package com.sd.sd2.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sd.sd2.model.command.EnterIfAvailableCommand;
import com.sd.sd2.model.command.ExitCommand;
import com.sd.sd2.service.command.TurnstileCommandHandler;

@RestController
@RequestMapping("/turnstile")
public class TurnstileEndpoint {

    private final TurnstileCommandHandler turnstileCommandHandler;

    @Autowired
    public TurnstileEndpoint(TurnstileCommandHandler turnstileCommandHandler) {
        this.turnstileCommandHandler = turnstileCommandHandler;
    }

    @PostMapping("/enter")
    public void enterIfAvailable(
            @RequestParam long id
    ) {
        // если нет ошибок == можно проходить
        turnstileCommandHandler.enterIfAvailable(
                new EnterIfAvailableCommand(id)
        );
    }

    @RequestMapping("/exit")
    public void exit(
            @RequestParam long id
    ) {
        turnstileCommandHandler.exit(new ExitCommand(id));
    }
}
