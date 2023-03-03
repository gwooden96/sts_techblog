package com.newbieTechblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newbieTechblog.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
