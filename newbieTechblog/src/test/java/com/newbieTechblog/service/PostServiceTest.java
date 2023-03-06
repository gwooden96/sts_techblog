package com.newbieTechblog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.newbieTechblog.domain.Post;
import com.newbieTechblog.repository.PostRepository;
import com.newbieTechblog.request.PostCreate;
import com.newbieTechblog.response.PostResponse;

@SpringBootTest
public class PostServiceTest {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private PostRepository postRepository;
	
	@BeforeEach
	void clean() {
		postRepository.deleteAll();
	}
	
	@Test
	@DisplayName("글 작성")
	void test1() {
		// given
		
		PostCreate postCreate = PostCreate.builder()
				.title("제목입니다.")
				.content("내용입니다.")
				.build();
		
		// when
		postService.write(postCreate);
		
		// then
		assertEquals(1L, postRepository.count());
		Post post = postRepository.findAll().get(0);
		assertEquals("제목입니다.", post.getTitle());
		assertEquals("내용입니다.", post.getContent());
	}
	
	@Test
	@DisplayName("글 1개 조회")
	void test2() {
		//given
		Post requestPost = Post.builder()
				.title("foo")
				.content("bar")
				.build();
		
		postRepository.save(requestPost);

		
		// when
		PostResponse postResponse = postService.get(requestPost.getId());
		
		// then
		assertNotNull(postResponse);
		assertEquals(1L, postRepository.count());
		assertEquals("foo", postResponse.getTitle());
		assertEquals("bar", postResponse.getContent());

	}
	
	@Test
	@DisplayName("글 1페이지 조회")
	void test3() {
		//given
		List<Post> requestpostPosts = IntStream.range(1, 31)
							.mapToObj(i -> Post.builder()
									.title("지우든 제목 " + i)
									.content("조립pc " + i)
									.build())
							.collect(Collectors.toList());
		
		postRepository.saveAll(requestpostPosts);
		

		Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");
		
		// when
		List<PostResponse> posts = postService.getList(pageable);
		
		// then
		assertEquals(5L, posts.size());
		assertEquals("지우든 제목 30", posts.get(0).getTitle());
		assertEquals("지우든 제목 26", posts.get(4).getTitle());


	}


}
