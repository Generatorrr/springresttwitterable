package com.example.springresttwitterable.entity.dto.message;

import com.example.springresttwitterable.entity.dto.user.UserAuthorDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * DTO for {@link com.example.springresttwitterable.entity.Message} for use in case we need to pass it to FE to main page
 * <p>
 * Created on 10/29/18.
 * <p>
 * @author Vlad Martinkov
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListMessageDTO implements Serializable
{

    private Long id;

    @NotBlank(message = "Please, fill the message")
    @Length(max = 2048, message = "Message too long (more than 2 kB)")
    private String text;

    @Length(max = 255, message = "Tag is too long")
    private String tag;

    private UserAuthorDTO author;

    private boolean edited;

    private String filename;
}
