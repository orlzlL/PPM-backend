package org.omoknoone.ppm.domain.role.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ViewRoleListResponseDTO {

    private Integer roleId;
    private String roleName;
}
