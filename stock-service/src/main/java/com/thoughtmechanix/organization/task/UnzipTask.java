package com.thoughtmechanix.organization.task;

import com.thoughtmechanix.organization.model.FileDetail;
import com.thoughtmechanix.organization.repository.FileRepository;
import com.thoughtmechanix.organization.utils.DateUtil;
import com.thoughtmechanix.organization.utils.UnzipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Map;
import java.util.Optional;

import static com.thoughtmechanix.organization.task.FileConstant.FILENAME;

@Component
public class UnzipTask implements Task {
    private static final Logger logger = LoggerFactory.getLogger(UnzipTask.class);
    @Autowired
    private UnzipFile unzipFile;
    @Autowired
    private DateUtil dateUtil;

    @Override
    public void initialize(Input input, Map contextMap,CrudRepository crudRepository) {

    }

    @Override
    public Result execute(Input input, Map contextMap, CrudRepository crudRepository) throws ParseException {
        //FILENAME
        String fileName = (String)contextMap.get(FILENAME);
      //  try {
             unzipFile.unzipFile(dateUtil.getDate(fileName));
      //  } catch (ParseException e) {
     //       logger.error("error in task "+e.getMessage());
    //        return new TaskOutput(false, contextMap, e, null);
     //   }
        return new TaskOutput(false, contextMap, null, null);
    }

    @Override
    public void apply(Result result, Map contextMap,CrudRepository crudRepository) {
        FileRepository fileRepository  = (FileRepository)crudRepository;
        String messageFileName = (String) contextMap.get(FileConstant.FILENAME);
        Optional<FileDetail> filedetail = fileRepository.findById(messageFileName);

        if(filedetail.isPresent())
        {
            FileDetail fileDetail = filedetail.get();
            fileDetail.setUnzip(true);
            fileRepository.save(fileDetail);
        }

    }


    @Override
    public String getName() {
        return "UnzipTask";
    }
}
