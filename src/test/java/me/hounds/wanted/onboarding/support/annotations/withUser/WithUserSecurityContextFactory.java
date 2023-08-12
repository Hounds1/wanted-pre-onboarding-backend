package me.hounds.wanted.onboarding.support.annotations.withUser;

import me.hounds.wanted.onboarding.global.security.principal.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithUserSecurityContextFactory implements WithSecurityContextFactory<WithUser> {
    @Override
    public SecurityContext createSecurityContext(WithUser annotation) {
        CustomUserDetails principal
                = CustomUserDetails.from(annotation.id(), annotation.email(), annotation.password(), annotation.role());

        UsernamePasswordAuthenticationToken
                token = new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(token);

        return SecurityContextHolder.getContext();
    }
}
