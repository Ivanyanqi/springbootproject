#include <stdio.h>
#include <Python/Python.h>

#include "cn_ivan_client_util_JNIUtil.h"

JNIEXPORT jstring JNICALL Java_cn_ivan_client_util_JNIUtil_printPyStr
  (JNIEnv *env, jobject obj, jstring argv){
        // 转化 jstring 类型为char*.
        const char *str = (*env)->GetStringUTFChars(env, argv, 0);
        printf("java参数: %s\n",str);


       //定义变量
       PyObject *pmod = NULL;
       PyObject *pfun = NULL;
       PyObject *pres = NULL;
       //定义返回值
       char *result = NULL;
       PyObject *pargs = NULL;

       // 初始化python 环境
       Py_Initialize();
       PyRun_SimpleString("import sys");
       PyRun_SimpleString("print(sys.version)");
       PyRun_SimpleString("sys.path.append('/Users/yanqi/WorkSpaces/test/springbootproject')");

       //导入模块名称, 通常为调用的Python脚本程序名
       pmod = PyImport_ImportModule("quickSort");
       if(pmod == NULL){
           printf("import module failed!\n");
       }
       //获取倒入模块的函数属性
       pfun = PyObject_GetAttrString(pmod, "main");
       if(pfun == NULL){
           printf("no such attribute \n");
       }
       //调用导入模块的入口函数, 获取返回结果
       pargs = Py_BuildValue("(s)",str);
       pres = PyEval_CallObject(pfun, pargs);
       if(pres == NULL){
           printf("call failed");
       }
       //解析返回的参数
       PyArg_Parse(pres,"s",&result);
       printf("%s \n",result);
       //回收资源
       Py_Finalize();
       // 将char转为jstring
       jstring res = (*env)->NewStringUTF(env,result);
       //释放字符串对象,应放在最后，否则程序中引用不到str
       (*env)->ReleaseStringUTFChars(env, argv, str);
       return res;
	}
