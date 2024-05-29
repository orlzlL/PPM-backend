package org.omoknoone.ppm.domain.projectmember.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ViewProjectMembersByProjectResponseDTO {

    private String employeeName;
    private Integer projectMemberRoleId;
    private String employeeEmail;
    private String employeeContact;
    private LocalDateTime projectMemberCreatedDate;
}



