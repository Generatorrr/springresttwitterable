package com.example.springresttwitterable.entity.dto.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created on 2020-05-12
 *
 * @author generatorr
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListProjectDTO implements Serializable {

    private Long id;
    private String name;
    private LocalDateTime initialDate;
    private LocalDateTime endDate;
    private int moduleCount;
}
