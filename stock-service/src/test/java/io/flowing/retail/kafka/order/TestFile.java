package io.flowing.retail.kafka.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;

public class TestFile {

    public static void main(String str[])
    {
        System.out.println("Testing ");

        RestTemplate restTemplate = new RestTemplate();
        UUID uuid = UUID.randomUUID();

        String randomUUIDString = uuid.toString();

        String fileName ="10-May-2021";


        Map<String, String> parametersMap = new HashMap<String, String>();

        parametersMap.put("fileName", fileName);

        ResponseEntity<Void> postResponse = restTemplate.postForEntity("http://localhost:8084" + "/v1/stock/addFile",fileName, Void.class);
        // Thread.sleep(20000);
        assertNotNull(postResponse);

    }
}
