package com.jobportal.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMessage {

	private String nameMessage;

	private String emailMessage;

	private String phoneMessage;

	private String genderMessage;
}
