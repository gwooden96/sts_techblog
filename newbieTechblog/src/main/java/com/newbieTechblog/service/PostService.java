package com.newbieTechblog.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.newbieTechblog.domain.Post;
import com.newbieTechblog.repository.PostRepository;
import com.newbieTechblog.request.PostCreate;

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
	public Post get(Long id) {
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
		
		return post;
			
		
	}

}
