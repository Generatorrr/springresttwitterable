package com.example.springresttwitterable.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created on 11/14/18.
 * <p>
 * @author Vlad Martinkov
 */
public class Oauth2SsoCondition implements Condition
{

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata)
    {
        return !"test".equals(context.getEnvironment().getActiveProfiles()[0]);
    }
}
