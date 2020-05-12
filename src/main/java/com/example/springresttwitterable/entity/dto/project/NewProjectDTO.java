package com.example.springresttwitterable.entity.dto.project;

import com.example.springresttwitterable.entity.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created on 2020-05-12
 *
 * @author generatorr
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewProjectDTO implements Serializable {

    @NotBlank(message = "Please, fill name field")
    @Length(max = 255, message = "Name is too long, max 255 chars")
    private String name;

    @NotBlank(message = "Please, fill description field")
    @Length(max = 2000, message = "Name is too long, max 2000 chars")
    private String description;

    @NotNull
    private Status status;

    @NotNull
    private LocalDateTime initialDate;

    @NotNull
    private LocalDateTime endDate;
}
