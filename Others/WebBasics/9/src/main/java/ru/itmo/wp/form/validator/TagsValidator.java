package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TagsValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            String tags = (String) target;
            if (!tags.trim().matches("[a-zA-Z ]")) {
                errors.rejectValue("tags", "tags.invalid", "Invalid tags");
            }
        }
    }
}
