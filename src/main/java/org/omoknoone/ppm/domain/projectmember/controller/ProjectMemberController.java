package org.omoknoone.ppm.domain.projectmember.controller;

import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.common.HttpHeadersCreator;
import org.omoknoone.ppm.domain.projectmember.dto.CreateProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ModifyProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ViewProjectMembersByProjectResponseDTO;
import org.omoknoone.ppm.domain.projectmember.service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/projectMembers")
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    @GetMapping("/list/{projectId}")
    public ResponseEntity<ResponseMessage> viewProjectMembersByProject(@PathVariable("projectId") Integer projectId) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        List<ViewProjectMembersByProjectResponseDTO> responseDTOs
                = projectMemberService.viewProjectMembersByProject(projectId);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("viewProjectMembersByProject", responseDTOs);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "프로젝트 구성원 조회 성공", responseMap));
    }

    @GetMapping("/availableMembers/{projectId}")
    public ResponseEntity<ResponseMessage> viewAvailableMembers(@PathVariable("projectId") Integer projectId, @RequestParam String query) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        List<ViewProjectMembersByProjectResponseDTO> responseDTOs
                = projectMemberService.viewAvailableMembers(projectId, query);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("viewAvailableMembers", responseDTOs);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "구성원 목록 조회 성공", responseMap));
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> createProjectMember(@RequestBody CreateProjectMemberRequestDTO requestDTO) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        Integer projectMemberId = projectMemberService.createProjectMember(requestDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("createProjectMember", projectMemberId);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "성공적으로 구성원이 추가 완료.", responseMap));
    }

    @PutMapping("/modify/{projectMemberId}")
    public ResponseEntity<ResponseMessage> modifyProjectMember(@PathVariable("projectMemberId") Integer projectMemberId, @RequestBody ModifyProjectMemberRequestDTO requestDTO) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        projectMemberService.modifyProjectMember(projectMemberId, requestDTO);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "구성원 정보가 성공적으로 업데이트되었습니다.", null));
    }

    @PutMapping("/remove/{projectMemberId}")
    public ResponseEntity<ResponseMessage> removeProjectMember(@PathVariable("projectMemberId") Integer projectMemberId) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        projectMemberService.removeProjectMember(projectMemberId);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "구성원이 성공적으로 제외되었습니다.", null));
    }
}
