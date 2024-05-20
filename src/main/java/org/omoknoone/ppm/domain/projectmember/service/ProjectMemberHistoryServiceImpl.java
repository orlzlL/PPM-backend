package org.omoknoone.ppm.domain.projectmember.service;

import lombok.RequiredArgsConstructor;
import org.omoknoone.ppm.domain.projectmember.repository.ProjectMemberHistoryRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProjectMemberHistoryServiceImpl implements ProjectMemberHistoryService {

    private final ProjectMemberHistoryRepository projectMemberHistoryRepository;
}
