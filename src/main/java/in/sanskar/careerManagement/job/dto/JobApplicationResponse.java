package in.sanskar.careerManagement.job.dto;

import in.sanskar.careerManagement.job.entity.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplicationResponse {

    private Long id;

    private String companyName;

    private String jobTitle;

    private String jobUrl;

    private String location;

    private String employmentType;

    private ApplicationStatus status;

    private LocalDate appliedDate;

    private String salary;

    private String notes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
