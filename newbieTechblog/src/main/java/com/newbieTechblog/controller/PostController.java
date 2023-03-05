package com.newbieTechblog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.newbieTechblog.domain.Post;
import com.newbieTechblog.request.PostCreate;
import com.newbieTechblog.response.PostResponse;
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

	
	//글 작성
	@PostMapping("/posts")
	public void post(@RequestBody @Valid PostCreate request) {
		postService.write(request);
	
	}
	
	
	/**
	 *  /posts -> 글 전체 조회(검색 + 페이징)
	 *  /posts/{postId} -> 글 한개만 조회
	 */
	
	
	//글 조회
	@GetMapping("/posts/{postId}")
	public PostResponse get(@PathVariable(name = "postId") Long id) {
		// request 클래스
		// response 클래스
		
		PostResponse postResponse = postService.get(id);
		// 응답 클래스 분리 (서비스 정책에 맞는)
		return postResponse;
	}

}
