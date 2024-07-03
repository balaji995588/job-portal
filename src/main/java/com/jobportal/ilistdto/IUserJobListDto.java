package com.jobportal.ilistdto;

import java.util.List;

public interface IUserJobListDto {

	List<IJobListDto> getJob();

	String getUserName();
}
