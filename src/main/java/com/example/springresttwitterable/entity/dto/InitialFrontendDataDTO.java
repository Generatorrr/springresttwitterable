package com.example.springresttwitterable.entity.dto;

import com.example.springresttwitterable.entity.dto.message.ListMessageDTO;
import com.example.springresttwitterable.entity.dto.user.InitialUserDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class InitialFrontendDataDTO implements Serializable
{
    private InitialUserDTO profile;
    private List<ListMessageDTO> messages;
}
