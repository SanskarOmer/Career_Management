package in.sanskar.careerManagement.job.repository;

import in.sanskar.careerManagement.job.entity.JobApplication;
import in.sanskar.careerManagement.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository
        extends JpaRepository<JobApplication, Long> {

    Page<JobApplication> findByUser(
            User user,
            Pageable pageable
    );

    Optional<JobApplication> findByIdAndUser(
            Long id,
            User user
    );
}