FROM ibmcom/mq:9

ADD config/mq-config.sh /var/mqm/config/

USER root
RUN chmod u+x /var/mqm/config/mq-config.sh && \
    /var/mqm/config/mq-config.sh
USER mqm

CMD ["/mq.sh"]

CMD ["/var/mqm/scripts/run_mq.sh"]
