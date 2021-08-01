package com.thoughtmechanix.organization.services;

import com.thoughtmechanix.organization.events.source.SimpleSourceBean;
import com.thoughtmechanix.organization.model.FileDetail;
import com.thoughtmechanix.organization.repository.FileRepository;
import com.thoughtmechanix.organization.utils.kafka.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class StockService {

    @Autowired
    SimpleSourceBean simpleSourceBean;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    ProducerService producerService ;

    public boolean downloadFile(String fileName) {

        if(fileRepository.findById(fileName).isPresent())
        {
            return false;
        }
        FileDetail fileDetail = new FileDetail();
        fileDetail.setFileName(fileName);
        fileRepository.save(fileDetail);
        producerService.sendMessage(fileName);
        return true;

    }
    public Optional<FileDetail> getFileInformation(String fileName) {

       return fileRepository.findById(fileName) ;

    }

}
