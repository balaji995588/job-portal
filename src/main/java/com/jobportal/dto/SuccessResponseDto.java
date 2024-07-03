package com.jobportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponseDto {

	private String key;

	private String message;

	private Object data;

}
