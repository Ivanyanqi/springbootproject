package cn.ivan.dubbo.client.controller;


import cn.ivan.dubbo.client.clientservice.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DubboController {

    @Autowired
    private ClientService clientService;


    @GetMapping("/saysomething/{msg}")
    public String saySomething(@PathVariable("msg") String msg){
        return clientService.testDubboRPC(msg);
    }


}
