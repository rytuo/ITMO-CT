package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.DisabledCredentials;
import ru.itmo.wp.form.validator.DisabledCredentialsValidator;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UsersPage extends Page {
    private final UserService userService;
    private final DisabledCredentialsValidator disabledCredentialsValidator;

    public UsersPage(UserService userService, DisabledCredentialsValidator disabledCredentialsValidator) {
        this.userService = userService;
        this.disabledCredentialsValidator = disabledCredentialsValidator;
    }

    @InitBinder("DisabledCredentials")
    void initBinder(WebDataBinder binder) {
        binder.addValidators(disabledCredentialsValidator);
    }

    @GetMapping("/users/all")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "UsersPage";
    }

    @PostMapping("/users/all")
    public String setDisabledStatus(@Valid DisabledCredentials disabledForm,
                                    BindingResult bindingResult,
                                    HttpSession httpSession) {
        if (!bindingResult.hasErrors()) {
            Boolean disabled = disabledForm.getDisabled().equals("Disable");
            long userId = Long.parseLong(disabledForm.getUserId());
            User user = userService.findById(userId);
            if (user != null && !user.equals(getUser(httpSession))) {
                userService.setDisabled(userId, disabled);
            }
        }
        return "redirect:/users/all";
    }
}
