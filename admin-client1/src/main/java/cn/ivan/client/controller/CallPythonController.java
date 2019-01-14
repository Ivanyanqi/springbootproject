package cn.ivan.client.controller;

import cn.ivan.client.util.JNIUtil;
import cn.ivan.client.util.command.LocalCommandExecutor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : yanqi
 * @version : 1.0
 * 2018/12/25
 */
@RestController
public class CallPythonController {

    @Autowired
    private LocalCommandExecutor localCommandExecutor;

    @Autowired
    private JNIUtil jniUtil;

    private static final String PYTHON_COMMAND = "/usr/local/var/pyenv/versions/3.6.0/bin/python";
    private static final String[] PYTHON_ENV = {"LANG=UTF-8"};

    @GetMapping("/callPython")
    public String execPython(@RequestParam String path) {
        if (StringUtils.isBlank(path)) {
            return "请输入执行脚本";
        }
        return localCommandExecutor.exec(PYTHON_COMMAND, path.split(" "), PYTHON_ENV).orElse("执行失败");
    }

    @GetMapping("/getjni")
    public String getJNI(String num) {
        return jniUtil.printPyStr(num);
    }
}
