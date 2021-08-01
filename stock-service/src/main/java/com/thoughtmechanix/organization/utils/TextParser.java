package com.thoughtmechanix.organization.utils;

import com.thoughtmechanix.organization.model.DailyPrice;
import com.thoughtmechanix.organization.repository.DailyPriceRepository;
import com.thoughtmechanix.organization.utils.kafka.ParsingResult;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class TextParser {

    public static void main(String str[]) {

        String fileName = "/home/iid/src/MicroServices/FileDownload/UnzipFile/cm05JUL2018bhav.csv";
        TextParser textParser = new TextParser();
        //textParser.parse(fileName,fileName,false);
        System.out.println();
    }

    public ParsingResult parse(String fileName, boolean parseheader, DailyPriceRepository dailyPriceRepository) {
        AtomicInteger counter =  new AtomicInteger(0);
        try {


            List<DailyPrice> modelObject = Files.lines(Paths.get(fileName)).map(s -> s.trim()).skip(1)
                    .map(s -> parseLine(s, ","))
                    .map(arr -> arrToObject(arr)).collect(Collectors.toList());

            dailyPriceRepository.saveAll(modelObject);
               /*     .forEach(s -> {
                        counter.incrementAndGet();
                        //  Arrays.stream(s).forEach(e->System.out.println(e));
                        //   System.out.println(s);

                    });*/
            return new ParsingResult(true, modelObject.size(), null);


        } catch (Exception e) {

            return new ParsingResult(false, 0, e);

        }



    }

    private String[] parseLine(String line, String delimeter) {
        return line.split(delimeter);

    }

    private DailyPrice arrToObject(String[] recordFields) {

        DailyPrice DailyPrice = new DailyPrice(recordFields);
        return DailyPrice;

    }
}
