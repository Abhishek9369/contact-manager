package com.contactmanager.controller;

import com.contactmanager.model.User;
import com.contactmanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // ─── Home redirect ────────────────────────────────────────────
    @GetMapping("/")
    public String home() {
        return "redirect:/contacts";
    }

    // ─── Show Login Page ──────────────────────────────────────────
    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String logout,
                            Model model) {
        if (error != null) model.addAttribute("errorMsg", "Invalid email or password!");
        if (logout != null) model.addAttribute("logoutMsg", "You have been logged out.");
        return "login";
    }

    // ─── Show Register Page ───────────────────────────────────────
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // ─── Handle Registration ──────────────────────────────────────
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult result,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        if (userService.emailExists(user.getEmail())) {
            model.addAttribute("emailError", "Email is already registered!");
            return "register";
        }

        try {
            userService.registerUser(user);
            redirectAttributes.addFlashAttribute("successMsg", "Registration successful! Please login.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorMsg", "Registration failed: " + e.getMessage());
            return "register";
        }
    }
}
