package com.example.springresttwitterable.entity.dto.message;

import com.example.springresttwitterable.entity.dto.user.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.example.springresttwitterable.entity.Message;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * DTO class for {@link Message}
 * Created on 10/25/18.
 * <p>
 * @author Vlad Martinkov
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageDTO implements Serializable
{
    
    private Long id;

    @NotBlank(message = "Please, fill the message")
    @Length(max = 2048, message = "Message too long (more than 2 kB)")
    private String text;

    @Length(max = 255, message = "Tag is too long")
    private String tag;

    private UserDTO author;
    
    private boolean edited;
    
    private MultipartFile file;
    
    private String filename;
}
