package com.thoughtmechanix.organization.utils.kafka.constant;

public class IKafkaConstants {

    public static String KAFKA_BROKERS = "localhost:9092";
    public static Integer MESSAGE_COUNT = 2000;
    public static String CLIENT_ID = "client1";
    public static final String TOPIC_NAME = "dailyfile";
    public static final String GROUP_ID_CONFIG = "consumerGroup1";
    public static Integer MAX_NO_MESSAGE_FOUND_COUNT = 100;
    public static String OFFSET_RESET_LATEST = "latest";
    public static String OFFSET_RESET_EARLIER = "earliest";
    public static Integer MAX_POLL_RECORDS = 1;

}
