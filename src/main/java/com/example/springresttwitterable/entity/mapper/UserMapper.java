package com.example.springresttwitterable.entity.mapper;

import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.dto.user.InitialUserDTO;
import com.example.springresttwitterable.entity.dto.user.UserAuthorDTO;
import com.example.springresttwitterable.entity.dto.user.UserSubscribDTO;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

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
    
    InitialUserDTO convertToInitialUserDTO(User user);
    
    UserAuthorDTO convertToAuthorDTO(User user);
    
    UserSubscribDTO convertToUserSubscribDTO(User user);
    Set<UserSubscribDTO> convertToUserSubscribDTO(Set<User> user);
}
