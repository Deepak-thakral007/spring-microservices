package com.thoughtmechanix.organization.task;

import com.thoughtmechanix.organization.model.FileDetail;
import com.thoughtmechanix.organization.repository.FileRepository;
import com.thoughtmechanix.organization.utils.DateUtil;
import com.thoughtmechanix.organization.utils.FileDownLoad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.Optional;

@Component
public class FileDownloadTask implements Task {

    private static final Logger logger = LoggerFactory.getLogger(FileDownloadTask.class);

    @Autowired
    private FileDownLoad fileDownLoad;

    @Autowired
    private DateUtil dateUtil;

    private String getFileNameString(String fileDate, int year, String month) {
        return FileConstant.BASE_URL + year + "/" + month + "/" + FileConstant.FILE_PREFIX + fileDate + FileConstant.FILE_POSTFIX;
    }

    @Override
    public void initialize(Input input, Map contextMap,CrudRepository crudRepository) {

    }

    @Override
    public Result execute(Input input, Map contextMap,CrudRepository crudRepository) throws ParseException, IOException {
     //   try {

            String messageFileName = (String) contextMap.get(FileConstant.FILENAME);

            logger.info(getFileNameString(dateUtil.getDate(messageFileName), dateUtil.getFileYear(messageFileName), dateUtil.getFileMonth(messageFileName)));

            fileDownLoad.downloadUsingNIO(getFileNameString(dateUtil.getDate(messageFileName), dateUtil.getFileYear(messageFileName), dateUtil.getFileMonth(messageFileName)), FileDownLoad.DOWNLOAD_FOLDER + dateUtil.getDate(messageFileName));

      //  } catch (Exception e) {
     //       logger.error("Error Message " + e.getMessage());
    //        return new TaskOutput(false, contextMap, e, null);

        //}
        return new TaskOutput(true, contextMap, null, null);
    }

    @Override
    public void apply(Result result, Map contextMap,CrudRepository crudRepository) {

        FileRepository fileRepository  = (FileRepository)crudRepository;
        String messageFileName = (String) contextMap.get(FileConstant.FILENAME);
        Optional<FileDetail> filedetail = fileRepository.findById(messageFileName);

        if(filedetail.isPresent())
        {
            FileDetail fileDetail = filedetail.get();
            fileDetail.setTransfer(true);
            fileRepository.save(fileDetail);
        }

    }

    @Override
    public String getName() {
        return "FileDownLoadTask";
    }
}
