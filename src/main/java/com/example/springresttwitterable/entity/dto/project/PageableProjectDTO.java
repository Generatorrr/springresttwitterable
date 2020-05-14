package com.example.springresttwitterable.entity.dto.project;

import com.example.springresttwitterable.entity.dto.PageDTO;
import com.example.springresttwitterable.entity.dto.project.ListProjectDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 2020-05-12
 *
 * @author generatorr
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageableProjectDTO implements Serializable {

    List<ListProjectDTO> projects;
    PageDTO page;
}
