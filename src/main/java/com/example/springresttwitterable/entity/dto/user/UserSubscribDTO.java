package com.example.springresttwitterable.entity.dto.user;

import com.example.springresttwitterable.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import lombok.Data;

/**
 * DTO for {@link User} entity
 * <p>
 * Created on 10/29/18.
 * <p>
 * @author Vlad Martinkov
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSubscribDTO implements Serializable
{

    private String id;
    private String name;
    private String userpic;
}
