/*
 * Developed for Epson Europe BV by Softeq Development Corporation.
 * http://www.softeq.com
 */

package com.example.springresttwitterable.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for {@link org.springframework.data.domain.Pageable}
 * <p>
 * Created on 11/9/18.
 * <p>
 * @author Vlad Martinkov
 */

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageDTO
{
    
    private int total;
    private int currentPage;
    private int pageSize;
}
