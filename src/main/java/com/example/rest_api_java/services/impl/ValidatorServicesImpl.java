package com.example.rest_api_java.services.impl;

import com.example.rest_api_java.model.UserRegisterRequest;
import com.example.rest_api_java.services.ValidatorServices;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ValidatorServicesImpl implements ValidatorServices {

    @Autowired
    private Validator validator;

    @Override
    public void Validate(Object request) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(request);

        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }

    }
}
