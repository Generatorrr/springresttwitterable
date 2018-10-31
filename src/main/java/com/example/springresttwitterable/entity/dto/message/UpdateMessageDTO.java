package com.example.springresttwitterable.entity.dto.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * New message DTO
 * Created on 10/29/18.
 * <p>
 * @author Vlad Martinkov
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateMessageDTO implements Serializable
{
    
    @NotNull
    private Long id;
    
    @NotBlank(message = "Please, fill the message")
    @Length(max = 2048, message = "Message too long (more than 2 kB)")
    private String text;

    @Length(max = 255, message = "Tag is too long")
    private String tag;
    
    private MultipartFile file;
}
