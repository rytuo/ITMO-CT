package ru.itmo.wp.form.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.DisabledCredentials;

@Component
public class DisabledCredentialsValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            DisabledCredentials status = (DisabledCredentials) target;
            if (!(status.getDisabled().equals("Enable") || status.getDisabled().equals("Disable"))) {
                errors.rejectValue("status", "status.incorrect-value", "Incorrect status value");
            }
        }
    }
}
