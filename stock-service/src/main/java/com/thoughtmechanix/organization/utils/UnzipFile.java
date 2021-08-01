package com.thoughtmechanix.organization.utils;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.thoughtmechanix.organization.utils.FileDownLoad.DOWNLOAD_FOLDER;

@Component
public class UnzipFile {

    public static final String outFilePath = "/home/iid/src/MicroServices/FileDownload/UnzipFile/";

    String zipfileName = "cm21JUN2018bhav.csv.zip";

    // String txtfileName = "cm21JUN2018bhav.csv";



    public String getOutFilePath() {
        return outFilePath;
    }



    public String getZipfileName() {
        return zipfileName;
    }

    public void setZipfileName(String zipfileName) {
        this.zipfileName = zipfileName;
    }

    public static void main(String[] args) throws IOException {
        /*
         * String sourceFile = "test1.txt"; FileOutputStream fos = new FileOutputStream("compressed.zip");
         * ZipOutputStream zipOut = new ZipOutputStream(fos); File fileToZip = new File(sourceFile); FileInputStream fis
         * = new FileInputStream(fileToZip); ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
         * zipOut.putNextEntry(zipEntry); final byte[] bytes = new byte[1024]; int length; while ((length =
         * fis.read(bytes)) >= 0) { zipOut.write(bytes, 0, length); } zipOut.close(); fis.close(); fos.close();
         */

        new UnzipFile().unzipFile();
    }

    private void unzipFile() {

        FileInputStream fis = null;
        ZipInputStream zipIs = null;
        ZipEntry zEntry = null;
        try {
            fis = new FileInputStream(DOWNLOAD_FOLDER + "/" + zipfileName);
            zipIs = new ZipInputStream(new BufferedInputStream(fis));
            while ((zEntry = zipIs.getNextEntry()) != null) {
                try {
                    byte[] tmp = new byte[4 * 1024];
                    FileOutputStream fos = null;
                    String opFilePath = outFilePath + zEntry.getName();
                    System.out.println("Extracting file to " + opFilePath);
                    fos = new FileOutputStream(opFilePath);
                    int size = 0;
                    while ((size = zipIs.read(tmp)) != -1) {
                        fos.write(tmp, 0, size);
                    }
                    fos.flush();
                    fos.close();
                } catch (Exception ex) {

                }
            }
            zipIs.close();
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void unzipFile(String fileName) {

        FileInputStream fis = null;
        ZipInputStream zipIs = null;
        ZipEntry zEntry = null;
        try {
            fis = new FileInputStream(DOWNLOAD_FOLDER + "/" + fileName);
            zipIs = new ZipInputStream(new BufferedInputStream(fis));
            while ((zEntry = zipIs.getNextEntry()) != null) {
                try {
                    byte[] tmp = new byte[4 * 1024];
                    FileOutputStream fos = null;
                    String opFilePath = outFilePath + zEntry.getName();
                    System.out.println("Extracting file to " + opFilePath);
                    fos = new FileOutputStream(opFilePath);
                    int size = 0;
                    while ((size = zipIs.read(tmp)) != -1) {
                        fos.write(tmp, 0, size);
                    }
                    fos.flush();
                    fos.close();
                } catch (Exception ex) {

                }
            }
            zipIs.close();
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
    public UnzipFile execute() {

        unzipFile();
        return this;
    }


    public String getName() {

        return "UnzipFile";
    }

}
