/*
 * Developed for Epson Europe BV by Softeq Development Corporation.
 * http://www.softeq.com
 */

package com.example.springresttwitterable.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Initial data needed to fill FE application;
 * Created on 10/26/18.
 * <p>
 * @author Vlad Martinkov
 */

@Data
@JsonView(Views.InitialFEData.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InitialFrontendDataDTO implements Serializable
{
    private UserDTO profile;
    private List<MessageDTO> messages;
}
