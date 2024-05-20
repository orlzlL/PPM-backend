package org.omoknoone.ppm.domain.projectmember.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.common.annotation.NoLogIfDraft;
import org.omoknoone.ppm.common.enums.RecordingTarget;
import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMember;
import org.omoknoone.ppm.domain.projectmember.dto.CreateProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ModifyProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ProjectMemberDTO;
import org.omoknoone.ppm.domain.projectmember.dto.viewProjectMembersByProjectResponseDTO;
import org.omoknoone.ppm.domain.projectmember.repository.ProjectMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProjectMemberServiceImpl(ProjectMemberRepository projectMemberRepository, ModelMapper modelMapper) {
        this.projectMemberRepository = projectMemberRepository;
        this.modelMapper = modelMapper;
    }

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
        ProjectMember newMember = modelMapper.map(dto, ProjectMember.class);

//        projectMemberRepository.save(newMember);

        return newMember.getProjectMemberId();
    }

    @Transactional
    @Override
    public void removeProjectMember(Integer projectMemberId) {
        ProjectMember excludedMember = projectMemberRepository.findById(projectMemberId)
                .orElseThrow(() -> new EntityNotFoundException("exception.data.entityNotFound"));
        excludedMember.remove();

        projectMemberRepository.save(excludedMember);
    }

    @Override
    public void reactivateProjectMember(Integer projectMemberId) {
        ProjectMember reactivatedMember = projectMemberRepository.findById(projectMemberId)
                .orElseThrow(() -> new EntityNotFoundException("exception.data.entityNotFound"));
        reactivatedMember.reactivate();

        projectMemberRepository.save(reactivatedMember);
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
    public Integer viewProjectMemberId(String employeeId, Integer projectId) {
        ProjectMember projectMember = projectMemberRepository.
                findByProjectMemberEmployeeIdAndProjectMemberProjectId(employeeId, projectId);
        return projectMember.getProjectMemberId();
    }

    @NoLogIfDraft(RecordingTarget.PROJECT_MEMBER)
    @Override
    public void healthCheck(ProjectMemberDTO projectMemberDTO) {
        log.info("[service] 작동! : {}", projectMemberDTO);
    }

}
