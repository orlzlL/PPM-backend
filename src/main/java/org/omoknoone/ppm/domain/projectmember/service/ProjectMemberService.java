package org.omoknoone.ppm.domain.projectmember.service;

import org.omoknoone.ppm.domain.projectmember.dto.CreateProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ModifyProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.viewProjectMembersByProjectResponseDTO;

import java.io.Serializable;
import java.util.List;

public interface ProjectMemberService {
    List<viewProjectMembersByProjectResponseDTO> viewProjectMembersByProject(Integer projectMemberProjectId);

    Integer createProjectMember(CreateProjectMemberRequestDTO createProjectMemberRequestDTO);

    String removeProjectMember(Integer projectMemberId);

    Integer modifyProjectMember(ModifyProjectMemberRequestDTO modifyProjectMemberRequestDTO);

    String checkProjectPermission(Serializable projectMemberId);
}
