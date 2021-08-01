#!/bin/sh

set -e

function usage () {

echo $(basename "$0") && cat << EOF 
    [--generate-bindings] [--configure] 

--generate-bindings    Generates the binding JMS file 
--configure            Configures the MQ QMGR based on a predefined config files
--help                 Prints this usage 

EOF
}

function generate_bindings () {
    
    source /opt/mqm/bin/setmqenv -s

    MQ_CHANNEL=$(cat /var/mqm//config/mq-config.sh | grep -o -ie "channel('.*') " | cut -d "'" -f2 | uniq)
    MQ_PORT=$(cat /var/mqm//config/mq-config.sh | grep -oP 'port\(\K[^\)]+')
    echo "def xaqcf(QCF) qmgr(${MQ_QMGR_NAME}) transport(CLIENT) host(mq) channel(${MQ_CHANNEL}) port(${MQ_PORT})" >/tmp/jmsAdmin.scp
    queues=$(echo 'dis qlocal(*)' | /opt/mqm/bin/runmqsc ${MQ_QMGR_NAME} | grep QUEUE | grep -v SYSTEM | sed "s/QUEUE(//g" | awk -F\) '{print $1}' | sed "s/ //g" | sort)
    for queue in ${queues}; do
       echo "define q(${queue}) qu(${queue})" >>/tmp/jmsAdmin.scp
    done
    echo "end"  >>/tmp/jmsAdmin.scp
    echo "INITIAL_CONTEXT_FACTORY=com.sun.jndi.fscontext.RefFSContextFactory" > /tmp/JMSAdmin.config
    echo "PROVIDER_URL=file:///tmp/JNDI-DIRECTORY" >> /tmp/JMSAdmin.config
    . /opt/mqm/java/bin/setjmsenv64
    export JAVA_HOME=/opt/mqm/java/jre64/jre/
    export PATH=$PATH:$JAVA_HOME/bin

    if [[ -e /tmp/JNDI-DIRECTORY/.bindings ]]; then
        rm -f /tmp/JNDI-DIRECTORY/.bindings
    fi

    /opt/mqm/java/bin/JMSAdmin -cfg /tmp/JMSAdmin.config < /tmp/jmsAdmin.scp
}

function stop_mq () {
    endmqm $MQ_QMGR_NAME
}
    
function start_mq () {
 
    source /opt/mqm/bin/setmqenv -s
    echo "----------------------------------------"
    dspmqver
    echo "----------------------------------------"

    QMGR_EXISTS=$(dspmq | grep ${MQ_QMGR_NAME} > /dev/null ; echo $?)
    strmqm ${MQ_QMGR_NAME}
    echo "----------------------------------------"
}

function state_mq () {
    source /opt/mqm/bin/setmqenv -s
    dspmq -n -m ${MQ_QMGR_NAME} | awk -F '[()]' '{ print $4 }'
}

function config () {

    if [[ ! -e "/var/mqm/config_created" ]]; then
        mkdir -p /var/mqm/config/
        cp /tmp/delivery/config/* /var/mqm/config/
        cd /var/mqm/config/
        appconfigtool --input-file ${CONFIG_FILES} --target-env ci --action generate_mq --ouput-mq-config ./mq-config.sh
        chmod u+x /var/mqm/config/mq-config.sh
        # sed -i '$ d' /var/mqm/config/mq-config.sh
        /var/mqm/config/mq-config.sh
        touch /var/mqm/config_created

        # Loop until "dspmq" says the queue manager is not running any more
        until [[ "`state_mq`" == "ENDED NORMALLY" ]]; do
          sleep 1
        done

        start_mq
        source /opt/mqm/bin/setmqenv -s
        # Disable extra security MQ 8+
        if (( $(dspmqver -f 2 | awk -F '[[:space:]][[:space:]]+' '{print $2}' |cut -d "." -f 1) >= 8 )); then
            echo "ALTER QMGR CONNAUTH('')" > /tmp/disable_security.mqsc
            echo "REFRESH SECURITY TYPE(CONNAUTH)" >> /tmp/disable_security.mqsc
            echo "QUIT" >> /tmp/disable_security.mqsc
            /opt/mqm/bin/runmqsc ${MQ_QMGR_NAME} </tmp/disable_security.mqsc
        fi
        stop_mq
        # Loop until "dspmq" says the queue manager is not running any more
        until [[ "`state_mq`" == "ENDED NORMALLY" ]]; do
          sleep 1
        done
    else
        echo "Config already ran, skipping"
    fi

}

function monitor_mq () {
    source /opt/mqm/bin/setmqenv -s
    # Loop until "dspmq" says the queue manager is running
    until [[ "`state_mq`" == "RUNNING" ]]; do
      sleep 1
    done
    
    if [[ -e "/opt/pomblk-mq/init-mq.sh" ]]; then

        echo "Start working on init-mq.sh"

        /opt/pomblk-mq/init-mq.sh

        if [[ $? -eq 0 ]]; then
            echo "Script finished successfully"
        else
            echo "Failure: init-mq.sh script failed"
            exit 1
        fi
    else
        echo "[mq] init-mq.sh script was not found - aborting"
        exit 1
    fi
    
    
    dspmq

    # Loop until "dspmq" says the queue manager is not running any more
    until [[ "`state_mq`" != "RUNNING" ]]; do
      sleep 5
    done

    # Wait until queue manager has ended before exiting
    while true; do
      STATE=`state_mq`
      case "$STATE" in
        ENDED*) break;;
        *) ;;
      esac
      sleep 1
    done
    dspmq
}

##########
## MAIN ##
##########
OPTS=$(getopt -o '' --long configure,generate-bindings,help -n 'parse-options' -- "$@")
eval set -- "$OPTS"

while true; do
  case "$1" in
      --configure ) configure=1; shift ;;
      --generate-bindings ) bindings=1; shift ;;
      --help ) usage; exit 0 ;;
      -- ) shift; break ;;
      * ) break ;;
  esac
done


# configure MQ QMGR
if [[ configure -eq 1 ]]; then
    config;
fi

if [[ -e /var/mqm/config/mq-config.sh ]]; then
    export MQ_QMGR_NAME=$(grep QMGR_NAME /var/mqm/config/mq-config.sh | cut -d ":" -f 2)
else
    echo "Scripts expect /var/mqm/config/mq-config.sh to be present(generated during config)"
    exit 1
fi
# Start the MQ QMGR
start_mq

# Generate the MQ bindings file if specified
if [[ bindings -eq 1 ]]; then
    generate_bindings;
fi

trap stop_mq SIGTERM SIGINT
monitor_mq