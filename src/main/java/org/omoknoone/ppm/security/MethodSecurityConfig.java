package org.omoknoone.ppm.security;

import lombok.RequiredArgsConstructor;
import org.omoknoone.ppm.domain.projectmember.service.ProjectMemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@RequiredArgsConstructor
@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class MethodSecurityConfig {

    private final ProjectMemberService projectMemberService;

    @Bean
    protected MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler =
                new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(ProjectPermissionExpression.builder()
                .projectMemberService(projectMemberService)
                .build());

        return expressionHandler;
    }
}
