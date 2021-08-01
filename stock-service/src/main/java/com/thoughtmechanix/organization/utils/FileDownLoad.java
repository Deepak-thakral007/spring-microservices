package com.thoughtmechanix.organization.utils;

import com.thoughtmechanix.organization.utils.kafka.service.ConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.MapKeyColumn;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.ParseException;

@Component
public class FileDownLoad {
    private static final Logger logger = LoggerFactory.getLogger(FileDownLoad.class);
    public static final String DOWNLOAD_FOLDER = "/home/iid/src/MicroServices/FileDownload/";
    public final String BASE_URL_BACKUP = "https://www1.nseindia.com/content/historical/EQUITIES/";
    public final String BASE_URL = "https://www1.archives.nseindia.com/content/historical/EQUITIES/";
    public final String FILE_PREFIX = "cm";
    public final String FILE_POSTFIX = "bhav.csv.zip";
    private String fileName;
    private String inputFilePath;
    private String outputFilePath;

    //https://archives.nseindia.com/content/historical/EQUITIES/2021/MAY/cm14MAY2021bhav.csv.zip

    public static void main(String str[]) throws ParseException, IOException {
        FileDownLoad fileDownLoad = new FileDownLoad();
        DateUtil dateUtil = new DateUtil();
        //FileDownLoad.downloadUsingStream("https://www1.nseindia.com/content/historical/EQUITIES/2018/JUL/cm05JUL2018bhav.csv.zip","");
        String strDate = "05-Jul-2018";
        DateUtil dataUtil = new DateUtil();

        System.out.println(dataUtil.getDate(strDate));
        System.out.println(dataUtil.getFileMonth(strDate));
        System.out.println(dataUtil.getFileYear(strDate));
        System.out.println(fileDownLoad.getFileNameString(dataUtil.getDate(strDate), dataUtil.getFileYear(strDate), dataUtil.getFileMonth(strDate)));
        // "https://www.nseindia.com/content/historical/EQUITIES/2018/JUL/cm05JUL2018bhav.csv.zip";
        fileDownLoad.downloadUsingNIO(fileDownLoad.getFileNameString(dataUtil.getDate(strDate), dataUtil.getFileYear(strDate), dataUtil.getFileMonth(strDate)), FileDownLoad.DOWNLOAD_FOLDER + dataUtil.getDate(strDate));
//https://www1.nseindia.com/content/historical/EQUITIES/2018/JUL/cm05JUL2018bhav.csv.zip
        UnzipFile unzipFile = new UnzipFile();
        unzipFile.unzipFile(  dataUtil.getDate(strDate));

    }
   // https://www1.nseindia.com/content/historical/EQUITIES/2021/MAY/cm12MAY2021bhav.csv.zip
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getInputFilePath() {
        return inputFilePath;
    }

    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    public void downloadUsingStream(String urlStr, String file) throws IOException, FileNotFoundException {
        URL url = new URL(urlStr);
        logger.info("starting downloading "+file);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count = 0;
        while ((count = bis.read(buffer, 0, 1024)) != -1) {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
        logger.info("downloading Completed " +file);
    }

    public void downloadUsingNIO(String urlStr, String file) throws IOException {

        logger.info("starting downloading "+file);
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
        logger.info("downloading Completed " +file);
    }

    //    @Override
    public FileDownLoad execute() {
        try {
            downloadUsingNIO(getInputFilePath(), getOutputFilePath());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return this;
    }

    //  @Override
    public String getName() {

        return "FileDownLoad";
    }

    private String getFileNameString(String fileDate, int year, String month) {
        return BASE_URL + year + "/" + month + "/" + FILE_PREFIX + fileDate + FILE_POSTFIX;
    }

}
