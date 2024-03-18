package com.example.roommate.service.api;

import com.example.roommate.domain.model.User;
import com.example.roommate.domain.model.request.LoginModel;
import com.example.roommate.service.db.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class LoginService {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public User login(@RequestBody LoginModel loginModel) {
        return userService.login(loginModel.getEmail(), loginModel.getPassword());
    }
}
