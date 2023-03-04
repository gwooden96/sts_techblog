package com.newbieTechblog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newbieTechblog.domain.Post;
import com.newbieTechblog.repository.PostRepository;
import com.newbieTechblog.request.PostCreate;

@AutoConfigureMockMvc
@SpringBootTest
public class PostControllerTest {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private PostRepository postRepository;
	
	@BeforeEach
	void clean() throws Exception {
		postRepository.deleteAll();
	}
	
  @Test
  @DisplayName("/posts 요청시 Hello World를 출력한다.")
  void test() throws Exception {
      // given
      PostCreate request = PostCreate.builder()
              .title("제목입니다.")
              .content("내용입니다.")
              .build();

      String json = objectMapper.writeValueAsString(request);

      // expected
      mockMvc.perform(post("/posts")
                      .contentType(APPLICATION_JSON)
                      .content(json))
              .andExpect(status().isOk())
              .andExpect(content().string(""))
              .andDo(print());
  }
	
	
	@Test
	@DisplayName("/posts 요청시 title값은 필수다.")
	void test2() throws Exception{
		// given
		PostCreate request = PostCreate.builder()
				.content("내용입니다.")
				.build();
		
		String json = objectMapper.writeValueAsString(request);

		// when
		mockMvc.perform(post("/posts")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)
				) 
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value("400"))
				.andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
				.andExpect(jsonPath("$.validation.title").value("타이틀을 입력하세요."))
				.andDo(print());
	}
	
	
	
	@Test
	@DisplayName("/posts 요청시 db에 값이 저장된다.")
	void test3() throws Exception{
		// given
		PostCreate request = PostCreate.builder()
				.title("제목입니다.")
				.content("내용입니다.")
				.build();
		
		String json = objectMapper.writeValueAsString(request);

		// when
		mockMvc.perform(post("/posts")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)
				) 
				.andExpect(status().isOk())
				.andDo(print());
		
		assertEquals(1L, postRepository.count());
		
		Post post = postRepository.findAll().get(0);
		assertEquals("제목입니다.", post.getTitle());
		assertEquals("내용입니다.", post.getContent());
	}
	
	
	
	@Test
	@DisplayName("글 1개 조회")
	void test4() throws Exception{
		// given
		Post post = Post.builder()
				.title("foo")
				.content("bar")
				.build();
		postRepository.save(post);
		
		
		// expected
		mockMvc.perform(get("/posts/{postId}", post.getId())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(post.getId()))
			.andExpect(jsonPath("$.title").value("foo"))
			.andExpect(jsonPath("$.content").value("bar"))
			.andDo(print());

	}

}


