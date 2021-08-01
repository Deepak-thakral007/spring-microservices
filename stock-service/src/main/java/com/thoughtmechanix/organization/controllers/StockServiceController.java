package com.thoughtmechanix.organization.controllers;

import com.thoughtmechanix.organization.model.FileDetail;
import com.thoughtmechanix.organization.services.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/stock")
public class StockServiceController {

    private static final Logger logger = LoggerFactory.getLogger(StockServiceController.class);
    @Autowired
    private StockService stockService;

    @RequestMapping(value = "/addFile", method = RequestMethod.POST)
    public ResponseEntity<Void> downLoadFile(@RequestBody String fileName) {
        System.out.println("fileName      " + fileName);
        if(stockService.downloadFile(fileName))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.badRequest().build();

    }

    @RequestMapping(value = "/fileInformation/{fileName}", method = RequestMethod.GET)
    public ResponseEntity<FileDetail> fileInformation(@PathVariable String fileName) {

        return stockService.getFileInformation(fileName).
                map(details -> ResponseEntity.ok(details)).
                orElse(ResponseEntity.notFound().build());


    }

 /*   @RequestMapping(value="/{organizationId}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization( @PathVariable("orgId") String orgId,  @RequestBody Organization org) {
        stockService.deleteOrg( org );
    }*/
}
