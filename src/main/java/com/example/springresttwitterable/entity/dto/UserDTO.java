package com.example.springresttwitterable.entity.dto;

import com.example.springresttwitterable.entity.Message;
import com.example.springresttwitterable.entity.Role;
import com.example.springresttwitterable.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO implements Serializable
{
    
    @JsonView(Views.MessageAuthorDTO.class)
    private String id;

    @NotNull
    @NotEmpty
    @JsonView(Views.UserDTO.class)
    private String name;

    @NotNull
    @NotEmpty
    @JsonView(Views.UserDTO.class)
    private String email;

    @JsonView(Views.UserInitialFEDTO.class)
    private String userpic;

    @JsonView(Views.UserInitialFEDTO.class)
    private String gender;

    @JsonView(Views.UserInitialFEDTO.class)
    private Set<Role> roles;

    private String locale;
    private LocalDateTime lastVisit;
    Set<Message> messages;
    private Set<User> subscribtions;
    private Set<User> subscribers;
}
