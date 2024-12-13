package com.msa.order.application.annotation;

import com.msa.order.exception.BusinessException.UnauthorizedException;
import com.msa.order.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
public class RoleCheckAspect {

  @Around("@annotation(roleCheck)")
  public Object checkRole(ProceedingJoinPoint joinPoint, RoleCheck roleCheck) throws Throwable {

    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    String userRole = request.getHeader("X-User-Role");

    log.info("[Role Check] request-user-role : {}, required-roles: {}", userRole, Arrays.toString(roleCheck.roles()));

    if (userRole == null) {
      throw new NullPointerException();
    }

    if (Arrays.stream(roleCheck.roles()).noneMatch(userRole::equals)) {
      throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
    }

    return joinPoint.proceed();
  }
}
