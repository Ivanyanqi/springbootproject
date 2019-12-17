package cn.ivan.client.controller;

import cn.ivan.client.config.Process;
import cn.ivan.client.config.WJArgResolve;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yanqi69
 * @date 2019/12/12
 */
@Slf4j
@RestController
public class ChainController {

   /* @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handler(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }*/

    @Autowired
    private HttpServletRequest request;

    @WJArgResolve
    @PostMapping("/testParam")
    @ResponseBody
    public Object testParamPost(@RequestParam("method")String method) {
        String data = (String)request.getAttribute("data");
        log.info(" content is {}",data);
        return Process.build(method).setRequest(data).handler();
    }
}
