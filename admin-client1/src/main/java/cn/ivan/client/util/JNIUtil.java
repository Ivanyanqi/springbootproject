package cn.ivan.client.util;

import org.springframework.stereotype.Component;

/**
 * 测试jni调用
 */
@Component
public class JNIUtil {

    public native String printPyStr(String argv);

    static {
        //System.loadLibrary("pystr");
    }

    public static void main(String[] argv) {
        String res = new JNIUtil().printPyStr("10000");
        System.out.println("=====java输出==========:" + res);
    }
}
