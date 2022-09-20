package com.decagon.xpress.response;

import com.decagon.xpress.entity.Comment;
import com.decagon.xpress.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class SearchCommentResponse {

    private String message;
    private LocalDateTime dateTime;
    private List<Comment> comment;

}
