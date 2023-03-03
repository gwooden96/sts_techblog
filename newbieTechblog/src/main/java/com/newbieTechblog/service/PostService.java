package com.newbieTechblog.service;

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
	
	public void write(PostCreate postCreate) {
		
		Post post = Post.builder()
				.title(postCreate.getTitle())
				.content(postCreate.getContent())
				.build();
		
		postRepository.save(post);

	}

}
