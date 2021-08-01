package com.thoughtmechanix.organization.task;

import com.thoughtmechanix.organization.model.FileDetail;
import com.thoughtmechanix.organization.repository.DailyPriceRepository;
import com.thoughtmechanix.organization.repository.FileRepository;
import com.thoughtmechanix.organization.utils.DateUtil;
import com.thoughtmechanix.organization.utils.TextParser;
import com.thoughtmechanix.organization.utils.UnzipFile;
import com.thoughtmechanix.organization.utils.kafka.ParsingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.Optional;

import static com.thoughtmechanix.organization.task.FileConstant.PARSING_RESULT;

@Component
public class ParsingTask implements Task {

    private static final Logger logger = LoggerFactory.getLogger(ParsingTask.class);
    private final String FILE_POSTFIX = "bhav.csv";
    @Autowired
    private DailyPriceRepository dailyPriceRepository;
    @Autowired
    private TextParser textParser;
    @Autowired
    private DateUtil dateUtil;

    public void executeTask(String messageFileName) throws ParseException, IOException {
        // "https://www.nseindia.com/content/historical/EQUITIES/2018/JUL/cm05JUL2018bhav.csv.zip";

        try {

            textParser.parse(UnzipFile.outFilePath + getFileNameString(dateUtil.getDate(messageFileName)), false, dailyPriceRepository);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private String getFileNameString(String fileDate) {
        return FileConstant.FILE_PREFIX + fileDate + FILE_POSTFIX;
    }

    @Override
    public void initialize(Input input, Map contextMap,CrudRepository crudRepository) {

    }

    @Override
    public Result execute(Input input, Map contextMap,CrudRepository crudRepository) throws ParseException {
        ParsingResult parsingResult=null;
   //     try {


            String messageFileName = (String) contextMap.get(FileConstant.FILENAME);
            parsingResult = textParser.parse(UnzipFile.outFilePath + getFileNameString(dateUtil.getDate(messageFileName)), false, dailyPriceRepository);

            contextMap.put(PARSING_RESULT,parsingResult.getNoOfRecords());

    //    } catch (Exception e) {


         //   logger.error(e.getMessage());
       //     return new TaskOutput(false, contextMap, e, parsingResult);
//        }

        return new TaskOutput(true, contextMap, null, parsingResult);
    }

    @Override
    public void apply(Result result, Map contextMap, CrudRepository crudRepository) {

        FileRepository fileRepository  = (FileRepository)crudRepository;
        String messageFileName = (String) contextMap.get(FileConstant.FILENAME);
        Optional<FileDetail> optionalFileDetail = fileRepository.findById(messageFileName);

        if(optionalFileDetail.isPresent())
        {
            FileDetail fileDetail = optionalFileDetail.get();
            fileDetail.setNoOfRecords((Integer)contextMap.get(PARSING_RESULT));
            fileDetail.setPersist(true);
            fileRepository.save(fileDetail);
        }
    }

    @Override
    public String getName() {
        return "ParsingTask";
    }
}
