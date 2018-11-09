package com.example.springresttwitterable.entity.dto.message;

import com.example.springresttwitterable.entity.Message;
import com.example.springresttwitterable.entity.dto.PageDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class for {@link Message}
 * Created on 10/25/18.
 * <p>
 * @author Vlad Martinkov
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageDTO implements Serializable
{
    List<ListMessageDTO> messages;
    PageDTO page;
}
