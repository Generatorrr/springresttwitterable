package com.example.springresttwitterable.entity.mapper;

import com.example.springresttwitterable.entity.Message;
import com.example.springresttwitterable.entity.dto.message.ListMessageDTO;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

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
    
    ListMessageDTO convert(Message message);
    
    List<ListMessageDTO> convert(List<Message> messages);
    List<ListMessageDTO> convert(Set<Message> messages);
}
