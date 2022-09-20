package com.decagon.xpress.response;

import com.decagon.xpress.entity.Like;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class LikeResponse {

    private String message;
    private LocalDateTime dateTime;
    private Like like;
    private int totalLikes;

}
