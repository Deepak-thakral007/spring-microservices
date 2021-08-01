package com.thoughtmechanix.organization;

import com.thoughtmechanix.organization.repository.DailyPriceRepository;
import com.thoughtmechanix.organization.repository.FileRepository;
import com.thoughtmechanix.organization.task.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;


public class TaskExecutor {


    private static final Logger logger = LoggerFactory.getLogger(TaskExecutor.class);
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private FileDownloadTask fileDownloadTask;
    @Autowired
    private UnzipTask unzipTask;
    @Autowired
    private ParsingTask parsingTask;
    @Autowired
    private DailyPriceRepository dailyPriceRepository;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    public TaskExecutor() {

    }

    public void execute(String message) throws IOException, ParseException {

    boolean error=false;

        Map<String, Object> contextMap = new HashMap<>();
        contextMap.put(FileConstant.FILENAME, message);
        logger.info(String.format("$$$$ => Consumed message: %s", message));

        //  fileDownloadTask.executeTask(message);
        Future future = executor.submit(()-> {
            try {
                executeTask(fileDownloadTask, contextMap, fileRepository);
            } catch (IOException e) {

                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        try {
            future.get(10, TimeUnit.SECONDS);
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            logger.error("Error in task Timeout" + message + " " + e.getLocalizedMessage());
            future.cancel(true);
            error=true;
        } finally {

        }
        try {
            //executeTask(fileDownloadTask, contextMap, fileRepository);
            if(error==false) {
                executeTask(unzipTask, contextMap, fileRepository);
                executeTask(parsingTask, contextMap, fileRepository);
            }
        } catch (Exception e) {

            logger.error("Error in task " + message + " " + e.getLocalizedMessage());
        }
    }

    private void executeTask(Task task, Map<String, Object> contextMap, CrudRepository crudRepository) throws IOException, ParseException {

        logger.info("Starting Task " + task.getName());

        task.initialize(null, contextMap, crudRepository);

        task.execute(null, contextMap, crudRepository);

        task.apply(null, contextMap, crudRepository);

        logger.info("Finished Task " + task.getName());


    }
}
