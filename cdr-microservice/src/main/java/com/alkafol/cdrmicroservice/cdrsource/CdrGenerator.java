package com.alkafol.cdrmicroservice.cdrsource;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class CdrGenerator implements CdrProvider{
    @Value("${generator.lines.amount}")
    private int linesAmount;

    @Value("${generator.max.unique.numbers}")
    private int maxUniqueNumbers;

    @Value("${generator.call.types}")
    private String[] callTypes;

    @Value("${generator.period.start.datetime}")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime periodStartingTime;

    @Value("${generator.period.end.datetime}")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime periodEndingTime;

    @Value("${generator.max.call.length}")
    private int maxCallLength;

    @Override
    public File getCdrFile() {
        return generateCdr();
    }

    @SneakyThrows
    private File generateCdr(){
        List<Long> numbers = new ArrayList<>();
        File cdr = new File("cdr-microservice/cdr.txt");
        cdr.createNewFile();
        FileWriter fw = new FileWriter(cdr);

        for (int i = 0; i < maxUniqueNumbers; ++i){
            long number = 70000000000L + (long)(Math.random() * ((79999999999L - 70000000000L) + 1L));
            numbers.add(number);
        }

        for (int i = 0; i < linesAmount; ++i){
            int numberIndex = (int)(Math.random() * numbers.size());

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyMMddHHmmss");
            LocalDateTime callStartingTime = periodStartingTime.plusSeconds(
                    (long)(Math.random() * ChronoUnit.SECONDS.between(periodStartingTime, periodEndingTime))
            );
            LocalDateTime callEndingTime = callStartingTime.plusSeconds(
                    (long)(Math.random() * Math.min(
                            maxCallLength, ChronoUnit.SECONDS.between(callStartingTime, periodEndingTime))
                    )
            );
            String callType = callTypes[(int)(Math.random() * callTypes.length)];

            fw.write(callType + ","
                    + numbers.get(numberIndex) + ","
                    + dateTimeFormatter.format(callStartingTime) + ","
                    + dateTimeFormatter.format(callEndingTime) + "\n");
        }

        fw.close();
        return cdr;
    }
}
