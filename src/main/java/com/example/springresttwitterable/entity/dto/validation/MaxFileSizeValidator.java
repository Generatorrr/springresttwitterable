package com.example.springresttwitterable.entity.dto.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created on 11/20/18.
 * <p>
 * @author Vlad Martinkov
 */
public class MaxFileSizeValidator implements ConstraintValidator<MaxFileSizeConstraint, MultipartFile>
{

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context)
    {
        if (value != null) {
            return value.getSize() <= 512000;
        }
        return true;
    }
}
