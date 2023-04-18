package com.alkafol.cdrmicroservice.cdrsource;

import com.abslab.lib.pairwise.gen.PairwiseGenerator;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CdrGenerator implements CdrProvider{
    @Value("${generator.lines.amount}")
    private int linesAmount;

    @Value("${generator.max.unique.numbers}")
    private int maxUniqueNumbers;

    @Value("${generator.number.region.code.max.length}")
    private int numberRegionCodeMaxLength;

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

    private String randomString(String alphabet, int length){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    private String generateValidCallType(){

        String callType = callTypes[(int)(Math.random() * callTypes.length)];

        return callType;
    }


    private List<String> generateInvalidCallType(){
        List<String> callTypeInvalidVariables = new ArrayList<String>();
        //empty string
        callTypeInvalidVariables.add("");
        //zero
        callTypeInvalidVariables.add("0");
        //-1
        callTypeInvalidVariables.add("-1");
        //null
        callTypeInvalidVariables.add("null");
        //without prefix 0
        callTypeInvalidVariables.add("1");
        callTypeInvalidVariables.add("2");
//        //00 and 03 - limit values for Call types formant
//        callTypeInvalidVariables.add("00");
//        callTypeInvalidVariables.add("03");
        //random 1-3 sign integer
        callTypeInvalidVariables.add(String.valueOf(3+(int) (Math.random()*9)));
        callTypeInvalidVariables.add(String.valueOf(10+(int) (Math.random()*99)));
        callTypeInvalidVariables.add(String.valueOf(100+(int) (Math.random()*999)));
        //float
        callTypeInvalidVariables.add(String.valueOf(Math.random()));
        //random 1-3 sign strings
        callTypeInvalidVariables.add(randomString("ABCDEFGHIJKLMNOPQRSTUVWXYZ",1));
        callTypeInvalidVariables.add(randomString("ABCDEFGHIJKLMNOPQRSTUVWXYZ",2));
        callTypeInvalidVariables.add(randomString("ABCDEFGHIJKLMNOPQRSTUVWXYZ",3));
        //space front of char
        callTypeInvalidVariables.add(" "+ (1 + (int)(Math.random()*2)));
        //space between of char
        callTypeInvalidVariables.add(0+" "+ (1 + (int)(Math.random()*2)));
        //hex, oct, bin
        callTypeInvalidVariables.add("0x0"+ (1 + (int)(Math.random()*2)));
        callTypeInvalidVariables.add("0o0"+ (1 + (int)(Math.random()*2)));
        callTypeInvalidVariables.add("0b0"+ (1 + (int)(Math.random()*2)));

        return  callTypeInvalidVariables;
    }

    private List<String> generateValidPhoneNumber(){
        List<String> numbers = new ArrayList<String>();

        for (int i = 0; i < maxUniqueNumbers; ++i) {
            int regionCodeLength = 1 + (int) (Math.random()*numberRegionCodeMaxLength);
            String regionCode = String.valueOf(1+(int) (Math.random()* Math.pow(10,regionCodeLength)));

            if (Math.random() <0.3) {
                regionCode = "+"+regionCode; //adding "+" for random numbers region code
            }

            String number = String.valueOf(1000000000L + (long) (Math.random() * ((9999999999L - 1000000000L) + 1L)));
            numbers.add(regionCode+number);
        }
        return numbers;
    }

    private List<String> generateInvalidPhoneNumber(){
        List<String> invalidNumbers = new ArrayList<String>();

        //empty string
        invalidNumbers.add("");
        //zero
        invalidNumbers.add("0");
        //-1
        invalidNumbers.add("-1");
        //null
        invalidNumbers.add("null");
        //length 10 near limit
        invalidNumbers.add("9999999999");
        //length 16 near limit, because number with regin and "+" sign is 15
        invalidNumbers.add("1000000000000000");
        //11 zeros
        invalidNumbers.add("00000000000");
        //11 spaces
        invalidNumbers.add("           ");
        //11 chars
        invalidNumbers.add(randomString("ABCDEFGHIJKLMNOPQRSTUVWXYZ",11));
        //space front of char 11 chars
        invalidNumbers.add(" "+(7000000000L + (long)(Math.random() * ((7999999999L - 7000000000L) + 1L))));
        //space between of char
        invalidNumbers.add((70000L + (long)(Math.random() * ((79999L - 70000L) + 1L)))+" "
                            +(10000L + (long)(Math.random() * ((99999L - 10000L) + 1L))));
        //different format:
        //###-###-####
        invalidNumbers.add(String.format("7%s-%s-%s",
                randomString("1234567890",3),randomString("1234567890",3),randomString("1234567890",4)));
        //###.###.####
        invalidNumbers.add(String.format("7%s.%s.%s",
                randomString("1234567890",3),randomString("1234567890",3),randomString("1234567890",4)));
        //(###)###-####
        invalidNumbers.add(String.format("7(%s)%s-%s",
                randomString("1234567890",3),randomString("1234567890",3),randomString("1234567890",4)));

        return invalidNumbers;
    }

    private String generateValidDateStartEndPare(){

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyMMddHHmmss");
        LocalDateTime callStartingTime = periodStartingTime.plusSeconds(
                (long)(Math.random() * ChronoUnit.SECONDS.between(periodStartingTime, periodEndingTime))
        );
        LocalDateTime callEndingTime = callStartingTime.plusSeconds(
                (long)(Math.random() * Math.min(
                        maxCallLength, ChronoUnit.SECONDS.between(callStartingTime, periodEndingTime))
                )
        );

        return dateTimeFormatter.format(callStartingTime)+","+dateTimeFormatter.format(callEndingTime);
    }

    private List<String> generateInvalidDateStartEndPare() {
        List<String> invalidDateStartEndPare = new ArrayList<>();
        //empty
        invalidDateStartEndPare.add(String.format("%s,%s", "",""));
        //zero
        invalidDateStartEndPare.add(String.format("%s,%s", "0","0"));
        //-1
        invalidDateStartEndPare.add(String.format("%s,%s", "-1","-1"));
        //null
        invalidDateStartEndPare.add(String.format("%s,%s", "null","null"));
        //Start > End
        invalidDateStartEndPare.add(String.format("%s,%s", periodEndingTime,periodStartingTime));
        //zeros
        invalidDateStartEndPare.add(String.format("%s,%s", "00000000000000","00000000000000"));
        //32 day in month
        invalidDateStartEndPare.add(String.format("%s,%s", "20230432000000","20230432100000"));
        //13 month
        invalidDateStartEndPare.add(String.format("%s,%s", "20231315000000","20231315100000"));
        //dates without 1 symbol
        invalidDateStartEndPare.add(String.format("%s,%s", "2023041500000","2023041510000"));
        //dates with 1 more symbol
        invalidDateStartEndPare.add(String.format("%s,%s", "202304150000000","20230415100000"));

        return invalidDateStartEndPare;
    }

    @SneakyThrows
    private File generateCdr(){
        List<String> numbers = generateValidPhoneNumber();

        File cdr = new File("cdr-microservice/cdr.txt");
        cdr.createNewFile();
        FileWriter fw = new FileWriter(cdr);

        Map<String, List<String>> invalidParams = new HashMap<>();

        invalidParams.put("invalidPhoneNumber",generateInvalidPhoneNumber());
        invalidParams.put("invalidCallType",generateInvalidCallType());
        invalidParams.put("invalidPhoneDateStartEndPare",generateInvalidDateStartEndPare());

        PairwiseGenerator<String, String> gen = new PairwiseGenerator<>(invalidParams);
        gen.stream().forEach(test -> {
            try {
                fw.write(test.get(2)+","+test.get(0)+","+test.get(1)+"\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        for (int i = 0; i < linesAmount; ++i){

            int numberIndex = (int)(Math.random() * numbers.size());

            fw.write(generateValidCallType() + ","
                    + numbers.get(numberIndex) + ","
                    + generateValidDateStartEndPare() + "\n");
        }

        fw.close();
        return cdr;
    }
}
