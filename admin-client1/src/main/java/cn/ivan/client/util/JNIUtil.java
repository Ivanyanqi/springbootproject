package cn.ivan.client.util;

/**
 * 测试jni调用
 */
public class JNIUtil {

    public native String printPyStr(String argv);

    static {
        System.loadLibrary("pystr");
    }

    public static void main(String[] argv) {
        String res = new JNIUtil().printPyStr("我是java，调用c语言输出");
        System.out.println("=====java输出==========:" + res);
    }
}
