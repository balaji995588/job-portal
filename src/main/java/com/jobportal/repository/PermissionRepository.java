package com.jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.entities.PermissionEntity;
import com.jobportal.ilistdto.IPermissionListDto;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {

	List<IPermissionListDto> findByOrderByIdDesc(Class<IPermissionListDto> class1);

//	List<IPermissionListDto> findByOrderByIdDesc(Class<IPermissionListDto> class1);

}
