package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Scanner;

@Controller
public class PostPage extends Page {
    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }

    @Guest
    @GetMapping("/post/{id}")
    public String postGet(@PathVariable String id,
                           Model model,
                           HttpSession httpSession) {
        Post post = findPost(id);

        if (post == null) {
            putMessage(httpSession, "Not existing post");
            return "redirect:/";
        }

        model.addAttribute("post", post);
        model.addAttribute("comment", new Comment());
        return "PostPage";
    }

    @PostMapping("/post/{id}")
    public String writeCommentPostPost(@PathVariable String id,
                                @Valid @ModelAttribute("comment") Comment comment,
                              BindingResult bindingResult,
                              Model model,
                              HttpSession httpSession) {
        Post post = findPost(id);

        if (post == null) {
            putMessage(httpSession, "Not existing post");
            return "redirect:/";
        }
        model.addAttribute("post", post);

        if (bindingResult.hasErrors()) {
            return "PostPage";
        }

        comment.setUser(getUser(httpSession));
        postService.writeComment(post, comment);
        return "redirect:/post/" + post.getId();
    }

    private Post findPost(String id) throws NumberFormatException {
        Scanner scanner = new Scanner(id.trim());

        if (!scanner.hasNextLong()) { return null; }
        Long post_id = scanner.nextLong();
        if (scanner.hasNext()) { return null; }

        return postService.findById(post_id);
    }
}
