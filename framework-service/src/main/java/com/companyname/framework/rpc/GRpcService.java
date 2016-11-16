package com.companyname.framework.rpc;

import io.grpc.ServerInterceptor;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface GRpcService {

    Class<? extends ServerInterceptor>[] interceptors() default {};
    boolean applyGlobalInterceptors() default true;
}
