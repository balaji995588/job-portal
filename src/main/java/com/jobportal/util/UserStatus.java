package com.jobportal.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatus {

	private String nameStatus;

	private String emailStatus;

	private String phoneStatus;

	private String genderStatus;
}
