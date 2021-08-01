package com.thoughtmechanix.organization.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface  FileConstant {


    public static final String DOWNLOAD_FOLDER = "/home/iid/src/MicroServices/FileDownload/";
    public final String BASE_URL_BACKUP = "https://www1.nseindia.com/content/historical/EQUITIES/";
    public final String BASE_URL = "https://archives.nseindia.com/content/historical/EQUITIES/";
    //https://www1.archives.nseindia.com/content/historical/EQUITIES/2021/MAY/cm12MAY2021bhav.csv.zip
    public static final String FILE_PREFIX = "cm";
    public static final String FILE_POSTFIX = "bhav.csv.zip";

    public static final String FILENAME = "fileName";
    public static final String PARSING_RESULT = "parsingResult";
}
