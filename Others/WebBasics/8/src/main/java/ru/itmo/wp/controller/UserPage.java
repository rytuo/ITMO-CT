package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.service.UserService;

import java.util.Scanner;

@Controller
public class UserPage extends Page {
    private final UserService userService;

    public UserPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public String users(@PathVariable String id, Model model) {
        Scanner scanner = new Scanner(id.trim());
        if (scanner.hasNextLong()) {
            Long userId = scanner.nextLong();
            if (!scanner.hasNext()) {
                User user = userService.findById(userId);

                if (user != null) {
                    model.addAttribute("userInfo", user);
                }
            }
        }

        return "UserPage";
    }
}
