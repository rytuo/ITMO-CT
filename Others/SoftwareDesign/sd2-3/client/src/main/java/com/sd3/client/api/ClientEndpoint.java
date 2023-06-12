package com.sd3.client.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sd3.client.model.UserStats;
import com.sd3.client.service.UserService;

@RestController
public class ClientEndpoint {

    private final UserService userService;

    @Autowired
    public ClientEndpoint(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public long createUser() {
        return userService.createUser();
    }

    @PostMapping("/addFunds")
    public void addFunds(
            @RequestParam long userId,
            @RequestParam long funds
    ) {
        userService.addFunds(userId, funds);
    }

    @GetMapping("/getStats")
    public UserStats getStats(
            @RequestParam long userId
    ) {
        return userService.getUserStats(userId);
    }

    @GetMapping("/getTotalFunds")
    public long getTotalFunds(
            @RequestParam long userId
    ) {
        return userService.getTotalFunds(userId);
    }

    @PostMapping("/buyStocks")
    public void buyStocks(
            @RequestParam long userId,
            @RequestParam long stockId,
            @RequestParam long amount,
            @RequestParam long price
    ) {
        userService.buyStocks(userId, stockId, amount, price);
    }

    @PostMapping("/sellStocks")
    public void sellStocks(
            @RequestParam long userId,
            @RequestParam long stockId,
            @RequestParam long amount,
            @RequestParam long price
    ) {
        userService.sellStocks(userId, stockId, amount, price);
    }
}
