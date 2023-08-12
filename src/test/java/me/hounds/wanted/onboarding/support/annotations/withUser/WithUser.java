package me.hounds.wanted.onboarding.support.annotations.withUser;

import me.hounds.wanted.onboarding.domain.member.domain.vo.RoleType;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithUserSecurityContextFactory.class)
public @interface WithUser {

    long id() default 1L;
    String email() default "user@user.user";

    String password() default "user1234";

    RoleType role() default RoleType.USER;

    boolean activated() default true;
}
