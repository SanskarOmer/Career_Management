package in.sanskar.careerManagement.job.controller;

import in.sanskar.careerManagement.job.dto.JobApplicationRequest;
import in.sanskar.careerManagement.job.dto.JobApplicationResponse;
import in.sanskar.careerManagement.job.entity.JobApplication;
import in.sanskar.careerManagement.job.service.JobApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    @PostMapping
    public ResponseEntity<JobApplicationResponse> createApplication(
            @Valid @RequestBody JobApplicationRequest request,
            Authentication authentication
    ) {

        String email = authentication.getName();

        JobApplicationResponse response =
                jobApplicationService.createApplication(
                        request,
                        email
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Page<JobApplicationResponse>> getMyApplications(
            Authentication authentication,
            Pageable pageable
    ) {

        String email = authentication.getName();

        Page<JobApplicationResponse> applications =
                jobApplicationService.getMyApplications(
                        email,
                        pageable
                );

        return ResponseEntity.ok(applications);
    }

}