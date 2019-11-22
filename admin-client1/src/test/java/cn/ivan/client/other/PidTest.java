package cn.ivan.client.other;

import cn.ivan.client.util.command.LocalCommandExecutor;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import sun.nio.cs.StandardCharsets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.*;
import java.util.concurrent.*;

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
    public void testPython() throws UnsupportedEncodingException {

        Map<String, Object> head = new HashMap<>();
        head.put("entryId", "head = 1232\'1312'3");
        head.put("port", 1232132);
        head.put("country", "head = china public");
        head.put("公司", "head = 北京琪琪科技有限公司");
        head.put("date", new Date());

        Map<String, Object> data = new HashMap<>();
        data.put("entryHead", head);

        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 1620; i++) {
            Map<String, Object> body = new HashMap<>();
            body.put("entryId", "body" + i + " = 12321312" + i);
            body.put("port", "body" + i + " = 199900" + i);
            body.put("country", "body" + i + " = china public" + i);
            body.put("公司", "body" + i + " = 北京琪琪科技有限公司" + i);
            list.add(body);
        }
        data.put("entryList", list);

        String transData = JSON.toJSONStringWithDateFormat(data, "yyyy-MM-dd HH:mm:ss");
        log.info("数据大小,{},{}", transData.length(), transData.getBytes("UTF-8").length);

//        /Users/yanqi/WorkSpaces/python/maoyan/quickSort.py
        ExecutorService service =
                new ThreadPoolExecutor(2, 2, 0, TimeUnit.MICROSECONDS, new LinkedBlockingDeque<>());
        String command = "/usr/local/var/pyenv/versions/3.6.0/bin/python";
        // ide 中运行缺少环境变量LANG=UTF-8"，导致python stdout 输出编码不对，中文出错
        // System.setProperty("LANG","UTF-8");
        String[] env = {"LANG=UTF-8"};
        String[] args = {"/Users/yanqi/WorkSpaces/python/maoyan/baoguandan.py", transData};
        long start = System.currentTimeMillis();
        String exec = new LocalCommandExecutor
                .LocalCommandExecutorBuilder(service)
                .setTimeout(10)
                .getInstance()
                .exec(command, args, env)
                .orElse("执行失败");
        long end = System.currentTimeMillis();
        log.info("执行时间: {}", (end - start));
        log.info(exec);
        service.shutdown();
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

    @Test
    public void testReflect() throws Exception {

        Class<?> aClass = Class.forName("cn.ivan.client.other.Student");
        Student student = new Student();
        Method set = aClass.getMethod("setName", String.class);
        set.invoke(student,"123");
        System.out.println(student.getName());

        Field name = aClass.getDeclaredField("name");
//        name.set(student,123);  // 值类型不同
        name.set(student,"张三");
        System.out.println(student.getName());
    }



}
