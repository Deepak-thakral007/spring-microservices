package io.flowing.retail.kafka.order;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.thoughtmechanix.organization.ApplicationStockService;
import com.thoughtmechanix.organization.model.FileDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationStockService.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void testCreateFile() throws InterruptedException {

        UUID uuid = UUID.randomUUID();

        String randomUUIDString = uuid.toString();

        String fileName ="10-May-2021";
         Map<String, String> parametersMap = new HashMap<String, String>();

         parametersMap.put("fileName", fileName);

        ResponseEntity<Void> postResponse = restTemplate.postForEntity(getRootUrl() + "/v1/stock/addFile",fileName, Void.class);
       // Thread.sleep(20000);
        assertNotNull(postResponse);

    //    assertNotNull(postResponse.getBody());
    }
  //  @Test
    public void testCreateFile2() throws InterruptedException {

        UUID uuid = UUID.randomUUID();

        String randomUUIDString = uuid.toString();

        String fileName ="11-May-2021";
        Map<String, String> parametersMap = new HashMap<String, String>();

        parametersMap.put("fileName", fileName);

        ResponseEntity<Void> postResponse = restTemplate.postForEntity(getRootUrl() + "/v1/stock/addFile",fileName, Void.class);
        Thread.sleep(20000);
        assertNotNull(postResponse);

        //    assertNotNull(postResponse.getBody());
    }

}
