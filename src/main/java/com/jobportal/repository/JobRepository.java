package com.jobportal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jobportal.entities.JobEntity;
import com.jobportal.ilistdto.IJobListDto;
import com.jobportal.ilistdto.IUserJobsListDto;

public interface JobRepository extends JpaRepository<JobEntity, Long> {

	Page<IJobListDto> findByCreatedBy(Long createdBy, Pageable pageable, Class<IJobListDto> class1);

	Page<IJobListDto> findByOrderByIdDesc(Pageable pageable, Class<IJobListDto> class1);

	Page<IJobListDto> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByIdDesc(String title,
			String description, Pageable pageable, Class<IJobListDto> class1);

	boolean existsByIdAndCreatedBy(Long id, Long createdBy);

	@Query(value = "SELECT u.id as Id, u.name as Name,u.email as Email, j.id as JobId, j.title as JobTitle from users u join jobs j on u.id=j.created_by\r\n"
			+ "and u.is_active=true and j.is_active=true", nativeQuery = true)
	Page<IUserJobsListDto> getAllJobs(Pageable pageable, Class<IUserJobsListDto> class1);

}
