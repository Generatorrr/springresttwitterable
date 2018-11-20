package com.example.springresttwitterable.entity.mapper;

import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Qualifier from MapStruct package to have possibility convert from Json strings;
 * <p>
 * Created on 11/16/18.
 * <p>
 * @author Vlad Martinkov
 */

@Qualifier
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface FromJson
{
}
