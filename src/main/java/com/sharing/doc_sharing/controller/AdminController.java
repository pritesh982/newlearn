package com.sharing.doc_sharing.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sharing.doc_sharing.dto.ChangePasswordDto;
import com.sharing.doc_sharing.model.User;
import com.sharing.doc_sharing.repository.CategoryRepository;
import com.sharing.doc_sharing.repository.DocumentRepository;
import com.sharing.doc_sharing.repository.SubjectRepository;
import com.sharing.doc_sharing.repository.UserRepository;
import com.sharing.doc_sharing.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SubjectRepository subjectRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("subjectCount", subjectRepository.count());
        model.addAttribute("categoryCount", categoryRepository.count());
        model.addAttribute("userCount", userRepository.count());
        model.addAttribute("documentCount", documentRepository.count());
        return "admin/dashboard";
    }

    @GetMapping("/admin/change-password")
    public String showChangePasswordForm(Model model) {
        model.addAttribute("changePasswordForm", new ChangePasswordDto());
        return "admin/change-password";
    }

    @PostMapping("/admin/change-password")
    public String changePassword(@ModelAttribute("changePasswordForm") @Valid ChangePasswordDto form,
            BindingResult result,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "admin/change-password";
        }

        User user = userService.findByEmail(currentUser.getUsername()); // or getUsername() if using email
        if (!passwordEncoder.matches(form.getOldPassword(), user.getPassword())) {
            result.rejectValue("oldPassword", "", "Old password is incorrect");
            return "admin/change-password";
        }

        if (!form.getNewPassword().equals(form.getConfirmNewPassword())) {
            result.rejectValue("confirmNewPassword", "", "Passwords do not match");
            return "admin/change-password";
        }

        user.setPassword(passwordEncoder.encode(form.getNewPassword()));
        userService.savePassword(user);

        redirectAttributes.addFlashAttribute("message", "Password changed successfully!");
        return "redirect:/admin/dashboard";
    }

}