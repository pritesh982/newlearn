
package com.sharing.doc_sharing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

import com.sharing.doc_sharing.service.UserService;

import lombok.RequiredArgsConstructor;

import com.sharing.doc_sharing.dto.UserRegistrationDto;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/custom-login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
            BindingResult result) {
        if (userService.existsByEmail(userDto.getEmail())) {
            result.rejectValue("email", "", "Email already in use");
        }
        if (result.hasErrors()) {
            return "register";
        }
        userService.registerUser(userDto);
        return "redirect:/login?success";
    }
}
