package com.jobportal.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeginationResponse {

	private Integer pageNumber;
	private Integer pageSize;
	private Long total;

}
