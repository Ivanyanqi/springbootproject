package cn.ivan.client.config;

/**
 * @author yanqi69
 * @date 2019/12/17
 */
public class Process {

    private static ServiceFactory serviceFactory;

    private String method;

    private ServiceContext serviceContext;

    public String handler(){
        return serviceFactory.get(method).setServiceContext(serviceContext).handler();
    }

    public Process setRequest(String data){
        if(serviceContext == null){
            serviceContext = new ServiceContext();
        }
        serviceContext.setRequest(data);
        return this;
    }

    private Process(String method){
        this.method = method;
    }

    public static Process build(String method){
        return new Process(method);
    }



    public static void setServiceFactory(ServiceFactory serviceFactory){
        Process.serviceFactory = serviceFactory;
    }


}
