package com.companyname.framework.mybatis;


import com.companyname.framework.MicroService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MultipleDataSourceAspectAdvice {

    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Around("within(com.companyname.framework.MicroService+)")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        MicroService target = (MicroService) point.getTarget();
        if (target != null) {
            MultipleDataSource.setDataSourceKey(target.getDbKey());
            logger.debug("[Change DataSource][" + target.getDbKey() + "]" + target.getClass().toString());
        }
        return point.proceed();
    }

}
