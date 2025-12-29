package com.sprintboot.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sprintboot.admin.model.AdminProblemResult;
import com.sprintboot.admin.model.AdminProblemResultId;

@RepositoryRestResource
public interface AdminProblemResultRepo extends JpaRepository<AdminProblemResult, AdminProblemResultId> {
    List<AdminProblemResult> findByIdResultId(Long resultId);

    // find all by problem_id
    List<AdminProblemResult> findByIdProblemId(Long problemId);

    // find by both result_id & problem_id
    Optional<AdminProblemResult> findByIdResultIdAndIdProblemId(Long resultId, Long problemId);

}
