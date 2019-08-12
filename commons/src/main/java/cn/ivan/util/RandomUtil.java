package cn.ivan.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yanqi69
 * @date 2019/8/12
 */
public class RandomUtil {
        private final static String DATE_PATTERN = "yyyyMMddHHmmssSSS";
        private final static DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_PATTERN);

        public static String genernateNo1(){
            return new SimpleDateFormat(DATE_PATTERN).format(new Date()) + randomInt(4);
        }

        public static String genernateNo2(){
            return format.format(LocalDateTime.now()) + randomInt(4);
        }

        private static String randomInt(int length){
            Random random = new Random();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< length ; i++){
                sb.append(random.nextInt(9));
            }
            return sb.toString();
        }


    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<String> result = new CopyOnWriteArrayList<>();
        for( int i= 0; i< 1000 ;i++){
            executorService.submit(()->{
                result.add(genernateNo2());
            });
        }
        executorService.shutdown();
        Thread.sleep(10000);
        System.out.println(result.size());
        System.out.println(result.stream().distinct().count());
    }
}
