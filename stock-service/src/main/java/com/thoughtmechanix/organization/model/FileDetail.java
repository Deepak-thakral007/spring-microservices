package com.thoughtmechanix.organization.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "filedetails")
public class FileDetail {
    @Id
    @Column(name = "filename", nullable = false)
    String fileName;
    @Column(name = "unzip")
    boolean unzip;
    @Column(name = "transfer")
    boolean transfer;
    @Column(name = "filesize")
    String fileSize;
    @Column(name = "noofrecords")
    Integer noOfRecords;
    @Column(name = "persist")
    boolean persist;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean getUnzip() {
        return unzip;
    }

    public void setUnzip(boolean unzip) {
        this.unzip = unzip;
    }

    public boolean getTransfer() {
        return transfer;
    }

    public void setTransfer(boolean transfer) {
        this.transfer = transfer;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getNoOfRecords() {
        return noOfRecords;
    }

    public void setNoOfRecords(Integer noOfRecords) {
        this.noOfRecords = noOfRecords;
    }

    public boolean getPersist() {
        return persist;
    }

    public void setPersist(boolean persist) {
        this.persist = persist;
    }

}
