package com.sylviavitoria.blogpets.repository;

import com.sylviavitoria.blogpets.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}