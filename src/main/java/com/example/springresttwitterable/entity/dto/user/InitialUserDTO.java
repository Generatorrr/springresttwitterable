/*
 * Developed for Epson Europe BV by Softeq Development Corporation.
 * http://www.softeq.com
 */

package com.example.springresttwitterable.entity.dto.user;

import com.example.springresttwitterable.entity.Message;
import com.example.springresttwitterable.entity.Role;
import com.example.springresttwitterable.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * TODO: write a brief summary fragment.
 * <p>
 * TODO: write a detailed description.
 * <p>
 * Created on 10/29/18.
 * <p>
 * @author Vlad Martinkov
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InitialUserDTO implements Serializable
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
}
