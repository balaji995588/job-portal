package com.jobportal.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobportal.entities.JobEntity;
import com.jobportal.entities.UserEntity;
import com.jobportal.entities.UserJobEntity;
import com.jobportal.ilistdto.ForNesting;
import com.jobportal.ilistdto.IUserJobsListDto;

import jakarta.servlet.http.HttpServletResponse;

public interface UserJobRepository extends JpaRepository<UserJobEntity, Long> {

	UserJobEntity findByJobAndUser(JobEntity job, UserEntity user);

	boolean existsByJobAndUser(JobEntity job, UserEntity user);

	@Query(value = "select u.id as Id, u.name as Name , u.email as Email, j.id as JobId, j.title as JobTitle from user_jobs uj\r\n"
			+ "join users u on uj.user_id=u.id \r\n"
			+ "join jobs j on uj.job_id=j.id where uj.user_id=:userId", nativeQuery = true)
	Page<IUserJobsListDto> getAllJobsByUser(@Param("userId") Long userId, Pageable pageable,
			Class<IUserJobsListDto> class1);

	@Query(value = "select u.id as Id, u.name as Name , u.email as Email, j.id as JobId, j.title as JobTitle from user_jobs uj\r\n"
			+ "join users u on uj.user_id=u.id \r\n"
			+ "join jobs j on uj.job_id=j.id where uj.job_id=:jobId", nativeQuery = true)
	Page<IUserJobsListDto> getAllUsersByJob(@Param("jobId") Long jobId, Pageable pageable,
			Class<IUserJobsListDto> class1);

	@Query(value = "select u.id as Id, u.name as Name , u.email as Email, j.id as JobId, j.title as JobTitle from user_jobs uj\r\n"
			+ "			join users u on uj.user_id=u.id \r\n"
			+ "			join jobs j on uj.job_id=j.id where j.created_by=:userId order by uj.id desc", nativeQuery = true)
	Page<IUserJobsListDto> getAllCreatedJobs(Long userId, Pageable pageable, Class<IUserJobsListDto> class1);

	@Query(value = "select u.id as Id, u.name as Name , u.email as Email, j.id as JobId, j.title as JobTitle from user_jobs uj\r\n"
			+ "join users u on uj.user_id=u.id \r\n"
			+ "join jobs j on uj.job_id=j.id order by uj.id desc", nativeQuery = true)
	Page<IUserJobsListDto> getAllUserJobs(Pageable pageable, Class<IUserJobsListDto> class1);

	@Query(value = "select u.id as Id, u.name as Name , u.email as Email, j.id as JobId, j.title as JobTitle from user_jobs uj\r\n"
			+ "join users u on uj.user_id=u.id \r\n"
			+ "join jobs j on uj.job_id=j.id order by uj.id desc", nativeQuery = true)
	List<IUserJobsListDto> getAllUserJobsList(HttpServletResponse response, Class<IUserJobsListDto> class1);

	List<ForNesting> findAllJobByUser(UserEntity user, Class<ForNesting> class1);

}
