#include <stdio.h>
#include <Python/Python.h>

#include "cn_ivan_client_util_JNIUtil.h"

JNIEXPORT jstring JNICALL Java_cn_ivan_client_util_JNIUtil_printPyStr
  (JNIEnv *env, jobject obj, jstring argv){
		// insert code here..
    // 转化 jstring 类型为char*.
    const char *str = (*env)->GetStringUTFChars(env, argv, 0);
    printf("java参数: %s\n",str);
    (*env)->ReleaseStringUTFChars(env, argv, str);

    // 初始化python 环境
    Py_Initialize();
    PyRun_SimpleString("import sys");
    PyRun_SimpleString("print(sys.version)");

    //回收资源
    Py_Finalize();
    return argv;
	

	}
