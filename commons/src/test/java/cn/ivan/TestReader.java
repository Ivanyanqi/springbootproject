package cn.ivan;


import cn.ivan.parser.impl.DefaultModelParser;
import cn.ivan.reader.impl.DefaultModelReader;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author : yanqi
 * @version : 1.0
 * 2018/12/3
 */
public class TestReader {

    private static String filePath;

    static {
        try {
            filePath = URLDecoder.decode(TestReader.class.getClassLoader().getResource("决策树结果.txt").getPath(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReader(){
        new ModelAnalyzer.Builder(filePath)
                .setModelReader(new DefaultModelReader())
                .setModelParser(new DefaultModelParser())
                .build()
                .parser()
                .stream()
                .map(e -> String.join(" && ",e.getConditions()))
                .forEach(System.out::println);
    }


}
