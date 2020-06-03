package cn.ivan.webflux.router;

import cn.ivan.webflux.handler.BasicHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 *
 *  路由配置  类型 mvc 里 的requestMappering 将 handler与 请求路径绑定起来
 *   返回为 RouterFunction<ServerResponse> 的 一个 bean 路由函数
 * @author yanqi69
 * @date 2020/6/1
 */
@Configuration
public class BasicRouter {

    @Bean
    public RouterFunction<ServerResponse> router(BasicHandler handler){
        return RouterFunctions
                // 与具体的handler 绑定
                .route(RequestPredicates.GET("/api/rector/say"),handler::say)
                .andRoute(RequestPredicates.GET("/api/rector/flux"),handler::sayFlux)
                .andRoute(RequestPredicates.GET("/api/rector/stream/flux"),handler::sayFluxStream);
    }


}
