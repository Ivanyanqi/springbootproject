package cn.ivan.client.other;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @author : yanqi
 * @version : 1.0
 * 2018/11/21
 */
@Slf4j
public class InvokeDynamicTest {

    @Test
    public void testMethodHandle() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle pow = lookup.findStatic(Math.class, "pow",
                MethodType.methodType(double.class, double.class, double.class));
        Object o = pow.invokeWithArguments(1.0, 1.0);
        log.info("调用结果:{}",o);
    }


}
