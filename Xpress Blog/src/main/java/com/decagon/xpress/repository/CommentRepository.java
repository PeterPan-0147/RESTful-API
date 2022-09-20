package com.decagon.xpress.repository;

import com.decagon.xpress.entity.Comment;
import com.decagon.xpress.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

        List<Comment> findByCommentContainingIgnoreCase(String keyword);
}
