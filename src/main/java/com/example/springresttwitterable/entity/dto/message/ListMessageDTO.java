package com.example.springresttwitterable.entity.dto.message;

import com.example.springresttwitterable.entity.dto.user.InitialUserDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * TODO: write a brief summary fragment.
 * <p>
 * TODO: write a detailed description.
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

    private InitialUserDTO author;

    private boolean edited;

    private String filename;
}
