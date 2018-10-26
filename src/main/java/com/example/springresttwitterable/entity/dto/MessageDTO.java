/*
 * Developed for Epson Europe BV by Softeq Development Corporation.
 * http://www.softeq.com
 */

package com.example.springresttwitterable.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
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
    @JsonView({Views.MessageDTO.class, Views.InitialFEData.class})
    private Long id;

    @NotBlank(message = "Please, fill the message")
    @Length(max = 2048, message = "Message too long (more than 2 kB)")
    @JsonView({Views.MessageDTO.class, Views.InitialFEData.class})
    private String text;

    @Length(max = 255, message = "Tag is too long")
    @JsonView({Views.MessageDTO.class, Views.InitialFEData.class})
    private String tag;

    @JsonView({ Views.MessageDTO.class, Views.InitialFEData.class })
    private UserDTO author;
    
    @JsonView({Views.MessageDTO.class, Views.InitialFEData.class})
    private boolean edited;
    
    @JsonView(Views.MessageToSaveDTO.class)
    private MultipartFile file;
    
    @JsonView({Views.MessageForListDTO.class, Views.InitialFEData.class})
    private String filename;

}
