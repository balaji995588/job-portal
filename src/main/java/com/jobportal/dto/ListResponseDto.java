package com.jobportal.dto;

import com.jobportal.page.PeginationResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListResponseDto {

	private String key;

	private String message;

	private Object data;

	private PeginationResponse peginationResponse;
}
