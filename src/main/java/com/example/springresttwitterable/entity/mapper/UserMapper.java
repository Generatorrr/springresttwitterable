package com.example.springresttwitterable.entity.mapper;

import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.dto.UserDTO;
import com.example.springresttwitterable.entity.dto.Views;
import com.fasterxml.jackson.annotation.JsonView;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * User mapper for API communications
 * <p>
 * Created on 10/26/18.
 * <p>
 * @author Vlad Martinkov
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {MessageMapper.class})
public interface UserMapper
{
    
    UserDTO convert(User user);
}
