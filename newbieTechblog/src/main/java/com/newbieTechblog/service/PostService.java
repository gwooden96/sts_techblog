package com.newbieTechblog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.newbieTechblog.domain.Post;
import com.newbieTechblog.repository.PostRepository;
import com.newbieTechblog.request.PostCreate;
import com.newbieTechblog.response.PostResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
	
	
	private final PostRepository postRepository;
	
	//글 작성 메서드
	public void write(PostCreate postCreate) {
		
		Post post = Post.builder()
				.title(postCreate.getTitle())
				.content(postCreate.getContent())
				.build();
		
		postRepository.save(post);

	}
	
	
	//글 조회 메서드
	public PostResponse get(Long id) {
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
		
		return PostResponse.builder()
				.id(post.getId())
				.title(post.getTitle())
				.content(post.getContent())
				.build();
		
	}
	
	
	//글 여러개 조회 메서드
	public List<PostResponse> getList(Pageable pageable) {
		return postRepository.findAll(pageable).stream()
				.map(post -> new PostResponse(post))
				.collect(Collectors.toList());
		
	}

}






