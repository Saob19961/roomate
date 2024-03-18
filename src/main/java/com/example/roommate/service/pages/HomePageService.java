package com.example.roommate.service.pages;

import com.example.roommate.domain.model.User;
import com.example.roommate.domain.model.helpers.GitHubUser;
import com.example.roommate.domain.model.repository.ArbeitplatzRepository;
import com.example.roommate.domain.model.repository.RoomRepository;
import com.example.roommate.service.db.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/")
public class HomePageService {
    public static final String PAGE = "page";
    public static final String PAGE_TITLE = "page_title";
    public static final String PAGE_SUBTITLE = "page_subtitle";

    @Autowired
    private UserService userService;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ArbeitplatzRepository arbeitplatzRepository;

    public String _home(Model model, HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = (User) request.getSession().getAttribute(GitHubUser.SESSION_TAG);

            response.addCookie(new Cookie(GitHubUser.SESSION_TAG, user.getId()));

            model.addAttribute("user", user);
            return "home";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "redirect:/reservations";
    }

    @GetMapping("/reservations")
    public String reservations(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute(PAGE, "reservations");
        model.addAttribute(PAGE_TITLE, "Reservations");
        model.addAttribute(PAGE_SUBTITLE, "This is the booked reservations");
        return _home(model, request, response);
    }

    @GetMapping("/blocking")
    public String blocking(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute(PAGE, "blocking");
        model.addAttribute(PAGE_TITLE, "Blocking");
        model.addAttribute(PAGE_SUBTITLE, "This is your spaces blocking");
        return _home(model, request, response);
    }

    @GetMapping("/rooms")
    public String rooms(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute(PAGE, "rooms");
        model.addAttribute(PAGE_TITLE, "Rooms");
        model.addAttribute(PAGE_SUBTITLE, "This is the available rooms");
        return _home(model, request, response);
    }

    @GetMapping("/equipments")
    public String equipments(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute(PAGE, "equipments");
        model.addAttribute(PAGE_TITLE, "Equipments");
        model.addAttribute(PAGE_SUBTITLE, "This is the current equipments");
        return _home(model, request, response);
    }

    @GetMapping("/spaces")
    public String spaces(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute(PAGE, "spaces");
        model.addAttribute(PAGE_TITLE, "Spaces");
        model.addAttribute(PAGE_SUBTITLE, "Find room's spaces");
        return _home(model, request, response);
    }
}
