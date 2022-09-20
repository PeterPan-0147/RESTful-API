package com.decagon.xpress.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data

public class PostDTO {

    @NotBlank
    @Size(min = 10, max = 30)
    private String title;
    private String story;
    private String designImage;
    private  int user_id;
}
