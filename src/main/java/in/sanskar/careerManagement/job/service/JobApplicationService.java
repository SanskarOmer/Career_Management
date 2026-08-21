package in.sanskar.careerManagement.job.service;

import in.sanskar.careerManagement.exception.ResourceNotFoundException;
import in.sanskar.careerManagement.job.dto.JobApplicationRequest;
import in.sanskar.careerManagement.job.dto.JobApplicationResponse;
import in.sanskar.careerManagement.job.entity.JobApplication;
import in.sanskar.careerManagement.job.repository.JobApplicationRepository;
import in.sanskar.careerManagement.user.entity.User;
import in.sanskar.careerManagement.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final UserRepository userRepository;

    public JobApplicationResponse createApplication(
            JobApplicationRequest request,
            String email
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User", email)
                );

        JobApplication application = JobApplication.builder()
                .companyName(request.getCompanyName())
                .jobTitle(request.getJobTitle())
                .jobUrl(request.getJobUrl())
                .location(request.getLocation())
                .employmentType(request.getEmploymentType())
                .appliedDate(request.getAppliedDate())
                .salary(request.getSalary())
                .notes(request.getNotes())
                .user(user)
                .build();

        JobApplication savedApplication =
                jobApplicationRepository.save(application);

        return mapToResponse(savedApplication);
    }

    private JobApplicationResponse mapToResponse(
            JobApplication application
    ) {

        return JobApplicationResponse.builder()
                .id(application.getId())
                .companyName(application.getCompanyName())
                .jobTitle(application.getJobTitle())
                .jobUrl(application.getJobUrl())
                .location(application.getLocation())
                .employmentType(application.getEmploymentType())
                .status(application.getStatus())
                .appliedDate(application.getAppliedDate())
                .salary(application.getSalary())
                .notes(application.getNotes())
                .createdAt(application.getCreatedAt())
                .updatedAt(application.getUpdatedAt())
                .build();
    }
    public Page<JobApplicationResponse> getMyApplications(
            String email,
            Pageable pageable
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User", email)
                );

        return jobApplicationRepository
                .findByUser(user, pageable)
                .map(this::mapToResponse);
    }

}
