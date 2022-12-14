package com.decagon.xpress.repository;

import com.decagon.xpress.entity.Like;
import com.decagon.xpress.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

        List<Post> findByTitleContainingIgnoreCase(String keyword);
}
