package com.jobportal.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDetails {
	private Long id;

	private String name;

	private String email;
}
