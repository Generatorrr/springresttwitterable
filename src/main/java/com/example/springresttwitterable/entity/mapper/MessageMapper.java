package com.example.springresttwitterable.entity.mapper;

import com.example.springresttwitterable.entity.Message;
import com.example.springresttwitterable.entity.dto.MessageDTO;
import com.example.springresttwitterable.entity.dto.Views;
import com.fasterxml.jackson.annotation.JsonView;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Message mapper for API communications.
 * <p>
 * Created on 10/26/18.
 * <p>
 * @author Vlad Martinkov
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {UserMapper.class})
public interface MessageMapper
{
    @Mapping(target = "file", ignore = true)
    @JsonView({ Views.UserInitialFEDTO.class, Views.MessageForListDTO.class })
    MessageDTO convert(Message message);
    
    List<MessageDTO> convert(List<Message> messages);
}
