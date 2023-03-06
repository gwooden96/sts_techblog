package com.newbieTechblog.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

	
	//글 작성 api
	@PostMapping("/posts")
	public void post(@RequestBody @Valid PostCreate request) {
		postService.write(request);
	
	}
	
	
	/**
	 *  /posts -> 글 전체 조회(검색 + 페이징)
	 *  /posts/{postId} -> 글 한개만 조회
	 */
	
	
	//글 조회(단건 조회) api
	@GetMapping("/posts/{postId}")
	public PostResponse get(@PathVariable Long postId) {
		return postService.get(postId);

	}
	
	
	//글 조회(여러개의 글 조회) api
	@GetMapping("/posts")
	public List<PostResponse> getList(Pageable pageable) {
		return postService.getList(pageable);
	}

}
