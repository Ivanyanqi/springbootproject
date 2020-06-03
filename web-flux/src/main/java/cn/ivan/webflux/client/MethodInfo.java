package cn.ivan.webflux.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.util.Map;
/**
 * @author yanqi69
 * @date 2020/6/2
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MethodInfo {

    private String name;

    private String uri;

    private HttpMethod method;

    private Class<?> returnType;

    private Class<?> retrunActualType;

    private Map<String,Object> parameters;

    private Mono body;

    private Class<?> bodyActualType;


}
