echo -e "\
define qlocal(SUBMISSION_NPP) REPLACE\n\
define qlocal(SUBMISSION_SCT) REPLACE\n\
define qlocal(SUBMISSION_LEGACY) REPLACE\n\
define qlocal(SUBMISSION_BATCH_LEGACY) REPLACE\n\
define qlocal(SUBMISSION_REQ) REPLACE\n\
define qlocal(SUBMISSION_NOTIFICATION) REPLACE\n\
define qlocal(PAIN_SUBMISSION_NOTIFICATION) REPLACE\n\
end\
" | /opt/mqm/bin/runmqsc MQ.QUEUE.MANAGER
