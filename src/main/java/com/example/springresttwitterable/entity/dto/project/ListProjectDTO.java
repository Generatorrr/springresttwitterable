package com.example.springresttwitterable.entity.dto.project;

import com.example.springresttwitterable.entity.dto.module.ListModuleDTO;
import com.example.springresttwitterable.entity.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

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
    private String description;
    private LocalDateTime initialDate;
    private LocalDateTime endDate;
    private Status status;
    private Set<ListModuleDTO> modules;
}
