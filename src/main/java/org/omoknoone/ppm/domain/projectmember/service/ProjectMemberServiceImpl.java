package org.omoknoone.ppm.domain.projectmember.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMember;
import org.omoknoone.ppm.domain.projectmember.dto.CreateProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ModifyProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.viewProjectMembersByProjectResponseDTO;
import org.omoknoone.ppm.domain.projectmember.repository.ProjectMemberRepository;
import org.omoknoone.ppm.domain.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public List<viewProjectMembersByProjectResponseDTO> viewProjectMembersByProject(Integer projectMemberProjectId) {

        List<ProjectMember> projectMembers = projectMemberRepository.findByProjectMemberProjectId(projectMemberProjectId);

        return projectMembers.stream()
                .map(member -> modelMapper.map(member, viewProjectMembersByProjectResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Integer createProjectMember(CreateProjectMemberRequestDTO dto) {
        ProjectMember member = modelMapper.map(dto, ProjectMember.class);

        projectMemberRepository.save(member);

        return member.getProjectMemberId();
    }

    @Transactional
    @Override
    public String removeProjectMember(Integer projectMemberId) {
        return null;
    }

    @Transactional
    @Override
    public Integer modifyProjectMember(ModifyProjectMemberRequestDTO dto) {

        ProjectMember existingMember = projectMemberRepository.findById(dto.getProjectMemberId())
                .orElseThrow(() -> new EntityNotFoundException("exception.data.entityNotFound"));
        existingMember.modify(dto);

        projectMemberRepository.save(existingMember);

        return existingMember.getProjectMemberId();
    }

    @Override
    public String checkProjectPermission(Serializable projectMemberId) {

        log.info("[service] 매개변수 : {}", projectMemberId);
        int projectMemberRoleId = projectMemberRepository.
                findProjectMemberByProjectMemberId((Integer) projectMemberId).getProjectMemberRoleId();
        log.info("[service] 역할 ID : {} ", projectMemberRoleId);

        String roleName = roleService.viewRoleNameByRoleId(projectMemberRoleId);
        log.info("[service] RoleName : {} ", roleName);
        return roleName;
    }

}
