package cn.ivan.client.config;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author yanqi69
 * @date 2019/12/17
 */
@Accessors(chain = true)
@Data
public class ExecutorChain {

    private List<Inteceptor> inteceptors;

    private ServiceExecutor executor;


    public String handler(ServiceContext serviceContext){
        if(inteceptors != null){
            inteceptors.forEach(inteceptor -> {
                inteceptor.before(serviceContext);
            });
        }
        serviceContext.setResponse(executor.executor(serviceContext.getRequest()));
        return serviceContext.getResponse();
    }


    public void addInteceptor(Inteceptor inteceptor){
        if(inteceptor == null){
            inteceptors = new ArrayList<>();
        }
        inteceptors.add(inteceptor);
    }



}
