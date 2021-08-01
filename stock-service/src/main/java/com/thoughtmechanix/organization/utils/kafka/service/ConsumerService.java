package com.thoughtmechanix.organization.utils.kafka.service;

import com.thoughtmechanix.organization.TaskExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;

import static com.thoughtmechanix.organization.utils.kafka.constant.IKafkaConstants.GROUP_ID_CONFIG;
import static com.thoughtmechanix.organization.utils.kafka.constant.IKafkaConstants.TOPIC_NAME;

@Service
public final class ConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    @Autowired
    private TaskExecutor taskExecutor;

    @KafkaListener(topics = TOPIC_NAME, groupId = GROUP_ID_CONFIG)
    public void consume(String message) throws IOException, ParseException {
        logger.info(String.format("$$$$ => Consumed message: %s", message));
        taskExecutor.execute(message);

    }
}
