package com.jobportal.page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class Pegination {

	public Pegination() {
		super();
	}

	public static Pageable getPagination(String pageNumber, String pageSize) {
		return PageRequest.of(Integer.parseInt(pageNumber) - 1, Integer.parseInt(pageSize));
	}
}
