package com.application.restaurantwebapp.config;

//import com.example.model.AuditLog;

import com.application.restaurantwebapp.entity.AuditLog;
import com.application.restaurantwebapp.entity.Order;
import com.application.restaurantwebapp.repository.AuditLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
@Aspect
@Slf4j
public class AspectConfigure {

    @Autowired
    AuditLogRepository auditLogRepository;

    @Before("execution(public * com.application.restaurantwebapp.service.*.*(..))")
    public void beforeMethodExecutes(JoinPoint joinPoint) {
        log.info(joinPoint.getSignature().getName() + " Method Started");
    }

    @After("execution(public * com.application.restaurantwebapp.service.*.*(..))")
    public void afterMethodExecutes(JoinPoint joinPoint) {
        log.info(joinPoint.getSignature().getName() + " Method Ended");
    }

    @AfterReturning(value = "execution(public * com.application.restaurantwebapp.service.OrderService.createOrder(..)) " + "|| execution(public * com.application.restaurantwebapp.service.OrderService.updateOrder(..))", returning = "returnValue")
    public void AfterReturning(Object returnValue) {
        if (returnValue instanceof Order) {
            Order order = (Order) returnValue;
            AuditLog auditLog = new AuditLog(((Order) returnValue).getO_id(), order.getItems(), order.getAmount(), order.getUserId(), new Date());
            auditLogRepository.save(auditLog);
            log.info("Bill audit SAVED " + auditLog);
        } else log.info("Bill audit NOT SAVED");
    }
}
