package com.blog.payloaddto;


import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private long id;
    @NotNull
    @Size(min = 2, message = "tittle should be at leat two character")//size eill work only String not inetger
    private String title;

    @NotNull
    @Size(min = 4, message = "Desciption should be at leat 4 charater")
    private String description;

    @NotNull
    @Size(min = 4, message = "Content should be at leat 4 charater")
    private String content;
//    private String message;
}
