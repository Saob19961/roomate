package com.example.roommate.service.pages;

import com.example.roommate.domain.model.User;
import com.example.roommate.domain.model.exceptions.ExceptionResponse;
import com.example.roommate.domain.model.exceptions.LoginException;
import com.example.roommate.domain.model.helpers.GitHubUser;
import com.example.roommate.domain.model.request.LoginModel;
import com.example.roommate.domain.model.request.RegisterModel;
import com.example.roommate.service.db.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class AuthPagesService {
    @Autowired
    private UserService userService;

    @GetMapping
    public String index(
        Model model,
        HttpServletRequest request
    ) {
        Object user = request.getSession().getAttribute(GitHubUser.SESSION_TAG);
        return user == null ? login(model) : "redirect:/home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginModel", new LoginModel());
        return "index";
    }

    @PostMapping("/login")
    public String loginFormSubmit(
        @ModelAttribute("loginModel") LoginModel loginModel,
        Model model,
        HttpServletRequest request
    ) {
        try {
            User user = userService.login(loginModel.getEmail(), loginModel.getPassword());

            request.getSession().setAttribute(GitHubUser.SESSION_TAG, user);
            return "redirect:/home";
        } catch (LoginException e) {
            model.addAttribute("loginError", new ExceptionResponse(e.getMessage()));
            return "index";
        }
    }

    @RequestMapping("/pre-register")
    public RedirectView preRegister(
        @RequestParam String email,
        @RequestParam String name,
        RedirectAttributes redirectAttributes
    ) {
        RegisterModel registerModel = new RegisterModel();
        registerModel.setEmail(email);
        registerModel.setName(name);
        redirectAttributes.addFlashAttribute("registerModel", registerModel);
        return new RedirectView("/register", true);
    }

    @GetMapping("/register")
    public String register(
        HttpServletRequest request,
        Model model
    ) {
        RegisterModel registerModel = (RegisterModel) RequestContextUtils.getInputFlashMap(request).get("registerModel");
        model.addAttribute("registerModel", registerModel);
        return "register";
    }

    @PostMapping("/register")
    public String registerFormSubmit(
        @ModelAttribute("registerModel") RegisterModel registerModel,
        Model model,
        HttpServletRequest request
    ) {
        try {
            User user = new User();
            user.setGithubadresse(registerModel.getEmail());
            user.setName(registerModel.getName());
            user.setPassword(registerModel.getPassword());
            user.setIsAdmin(false);

            user = userService.regestrierung(user);

            request.getSession().setAttribute(GitHubUser.SESSION_TAG, user);
            return "redirect:/home";
        } catch (LoginException e) {
            model.addAttribute("registerError", new ExceptionResponse(e.getMessage()));
            return "register";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute(GitHubUser.SESSION_TAG);
        return "redirect:/";
    }
}
