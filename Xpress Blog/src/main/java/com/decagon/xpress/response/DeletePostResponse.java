package com.decagon.xpress.response;

import com.decagon.xpress.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DeletePostResponse {

    private String message;
    private LocalDateTime dateTime;
    private Post post;
}
