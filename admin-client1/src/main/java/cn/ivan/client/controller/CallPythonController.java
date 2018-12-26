package cn.ivan.client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author : yanqi
 * @version : 1.0
 * 2018/12/25
 */
@RestController
public class CallPythonController {

    @GetMapping("/callPython")
    public String execPython(@RequestParam String path) throws Exception{
        Process exec = Runtime.getRuntime()
                .exec(path);
        StringBuilder sb = new StringBuilder("=========标准输出===========<br>");
        BufferedReader br = new BufferedReader(new InputStreamReader(exec.getInputStream(),"utf-8"));
        br.lines().forEach(line -> sb.append(line).append("<br>"));
        BufferedReader brerror = new BufferedReader(new InputStreamReader(exec.getErrorStream()));
        sb.append("=============错误============<br>");
        brerror.lines().forEach(line->sb.append(line).append("<br>"));
        return sb.toString();
    }
}
