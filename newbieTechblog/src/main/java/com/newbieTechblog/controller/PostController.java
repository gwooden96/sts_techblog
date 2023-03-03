package com.newbieTechblog.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.newbieTechblog.request.PostCreate;
import com.newbieTechblog.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//SPA -> vue
// -> javascript + <-> API (JSON)

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {
	
	private final PostService postService;

	
	@PostMapping("/posts")
	public void post(@RequestBody @Valid PostCreate request) {
		
		postService.write(request);
	
	}

}