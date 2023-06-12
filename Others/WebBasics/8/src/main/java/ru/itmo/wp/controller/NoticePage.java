package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.form.NoticeCredentials;
import ru.itmo.wp.form.validator.NoticeCredentialsValidator;
import ru.itmo.wp.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class NoticePage extends Page {
    private final NoticeService noticeService;
    private final NoticeCredentialsValidator noticeCredentialsValidator;

    public NoticePage(NoticeService noticeService, NoticeCredentialsValidator noticeCredentialsValidator) {
        this.noticeService = noticeService;
        this.noticeCredentialsValidator = noticeCredentialsValidator;
    }

    @InitBinder("NoticeCredentials")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(noticeCredentialsValidator);
    }

    @GetMapping("/notice/create")
    public String NoticeGet(Model model) {
        model.addAttribute("noticeForm", new NoticeCredentials());
        return "NoticePage";
    }

    @PostMapping("/notice/create")
    public String NoticePost(@Valid @ModelAttribute("noticeForm") NoticeCredentials noticeForm,
                                   BindingResult bindingResult,
                                   HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "NoticePage";
        }

        noticeService.createNotice(noticeForm);
        putMessage(httpSession, "Congrats, you have successfully added a notification!");

        return "redirect:/";
    }
}
