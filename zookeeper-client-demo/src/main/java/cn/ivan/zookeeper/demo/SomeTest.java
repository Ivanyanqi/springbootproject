package cn.ivan.zookeeper.demo;

import org.junit.Test;

public class SomeTest {

    @Test
    public void test(){
        String str = "abc";
        test1(str);
        System.out.println(str);
    }

    public void test1(String str){
        str = "abd";
    }
}
