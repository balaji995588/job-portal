package com.jobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.service.GeneralService;

@RestController
@RequestMapping("/gen")
public class GeneralController {

	@Autowired
	GeneralService generalService;

	@GetMapping("/start")
	public String start() {
		return "Success";
	}

	@GetMapping("/allschemes")
	public ResponseEntity<?> getAllSchemes() {

		try {
			String allSchemes = this.generalService.getAllSchemes();

			return new ResponseEntity<String>(allSchemes, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
