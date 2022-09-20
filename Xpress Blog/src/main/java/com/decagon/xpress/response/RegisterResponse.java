package com.decagon.xpress.response;

import com.decagon.xpress.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RegisterResponse {
    private String message;
    private LocalDateTime dateTime;
    private User user;
}
