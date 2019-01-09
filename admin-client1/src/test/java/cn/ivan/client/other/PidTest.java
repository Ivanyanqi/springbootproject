package cn.ivan.client.other;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author : yanqi
 * @version : 1.0
 * 2018/11/28
 */
@Slf4j
public class PidTest {

    @Test
    public void getPid(){
        String name = ManagementFactory.getRuntimeMXBean().getName();
        log.info("managerName : {}",name);
        String pid = name.split("@")[0];
        log.info("===pid : {}",pid);


    }

    @Test
    public void test(){

        String url = "http://www.baiud.com?url=http://10.96.18.00";
        String replace = url.replaceFirst("http://", "");
        System.out.println(replace);

    }

    @Test
    public void testPython() throws IOException {
//        /Users/yanqi/WorkSpaces/python/maoyan/quickSort.py

        String[] cmdArr = {"/bin/sh","-c",
                "/usr/local/var/pyenv/versions/3.6.0/bin/python /Users/yanqi/WorkSpaces/python/maoyan/quickSort.py 2000"};

        // ide 中运行缺少环境变量LANG=UTF-8"，导致python stdout 输出编码不对，中文出错
        Process exec = Runtime.getRuntime()
                .exec(cmdArr,new String[]{"LANG=UTF-8"});
//
//        Process exec = Runtime.getRuntime()
//                .exec("/Users/yanqi/WorkSpaces/shell/callpy.sh");
        BufferedReader brerror = new BufferedReader(new InputStreamReader(exec.getErrorStream()));
        System.out.println("==========error===================");

        BufferedReader br = new BufferedReader(new InputStreamReader(exec.getInputStream(), "utf-8"));
        br.lines().forEach(System.out::println);

        brerror.lines().forEach(System.out::println);


    }

    @Test
    public void testProcessBuilder() {
//        ProcessBuilder builder = new ProcessBuilder();
//        Map<String, String> environment = builder.environment();
//        environment.forEach(( k, v) -> System.out.println( k + " : " +v));

        // 默认包含当前目录
        //System.out.println(System.getProperty("java.library.path"));

        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "abc");
        map.put(10, "12");
        map.put(11, "13");
        map.put(20, "20");
        //方法用于返回最大键严格小于给定键，则返回null，如果不存在这样的键关联的键- 值映射关系
        System.out.println(map.ceilingEntry(20));
    }


}
