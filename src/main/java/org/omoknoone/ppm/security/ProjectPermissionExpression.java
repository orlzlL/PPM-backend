package org.omoknoone.ppm.security;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.omoknoone.ppm.domain.projectmember.service.ProjectMemberService;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

public class ProjectPermissionExpression implements PermissionEvaluator {

    private final ProjectMemberService projectMemberService;

    @Builder
    public ProjectPermissionExpression(ProjectMemberService projectMemberService) {
        this.projectMemberService = projectMemberService;
    }

    /* 인자가 없는 권한 확인 메소드 */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        // targetDomainObject = PM, permission = read

        return false;
    }

    /* 인자가 있는 권한 확인 메소드 */
    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        String projectMemberRoleName = projectMemberService.checkProjectPermission(targetId);

        if(projectMemberRoleName.equals(targetType)) {
            return true;
        }

        return false;
    }
}
