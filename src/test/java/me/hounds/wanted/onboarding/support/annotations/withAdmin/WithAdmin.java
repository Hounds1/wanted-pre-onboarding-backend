package me.hounds.wanted.onboarding.support.annotations.withAdmin;

import me.hounds.wanted.onboarding.domain.member.domain.vo.RoleType;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithAdminSecurityContextFactory.class)
public @interface WithAdmin {

    long id() default 1L;
    String email() default "admin@admin.admin";

    String password() default "admin1234";

    RoleType role() default RoleType.ADMIN;

    boolean activated() default true;
}
