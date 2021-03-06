package com.example.springresttwitterable.entity.dto.user;

import com.example.springresttwitterable.entity.Message;
import com.example.springresttwitterable.entity.Role;
import com.example.springresttwitterable.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO implements Serializable
{
    
    private String id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String email;

    private String userpic;

    private String gender;

    private Set<Role> roles;

    private String locale;
    
    private LocalDateTime lastVisit;
    
    Set<Message> messages;
    
    private Set<User> subscribtions;
    
    private Set<User> subscribers;
}
