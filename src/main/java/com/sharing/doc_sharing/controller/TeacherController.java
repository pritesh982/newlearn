package com.sharing.doc_sharing.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sharing.doc_sharing.dto.ChangePasswordDto;
import com.sharing.doc_sharing.model.User;
import com.sharing.doc_sharing.service.DocumentService;
import com.sharing.doc_sharing.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TeacherController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final DocumentService documentService;


    @GetMapping("/teacher/dashboard")
    public String list(Model model) {
        model.addAttribute("documents", documentService.findAll());
        return "teacher/list";
    }

    @GetMapping("/teacher/change-password")
    public String showChangePasswordForm(Model model) {
        model.addAttribute("changePasswordForm", new ChangePasswordDto());
        return "teacher/change-password";
    }

    @PostMapping("/teacher/change-password")
    public String changePassword(@ModelAttribute("changePasswordForm") @Valid ChangePasswordDto form,
            BindingResult result,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "teacher/change-password";
        }

        User user = userService.findByEmail(currentUser.getUsername()); // or getUsername() if using email
        if (!passwordEncoder.matches(form.getOldPassword(), user.getPassword())) {
            result.rejectValue("oldPassword", "", "Old password is incorrect");
            return "teacher/change-password";
        }

        if (!form.getNewPassword().equals(form.getConfirmNewPassword())) {
            result.rejectValue("confirmNewPassword", "", "Passwords do not match");
            return "teacher/change-password";
        }

        user.setPassword(passwordEncoder.encode(form.getNewPassword()));
        userService.savePassword(user);

        redirectAttributes.addFlashAttribute("message", "Password changed successfully!");
        return "redirect:/teacher/dashboard";
    }

    // @GetMapping("/teacher/list")
    // public String list(Model model) {
    //     model.addAttribute("documents", documentService.findAll());
    //     return "teacher/list";
    // }

    // @GetMapping("/teacher/new")
    // public String createForm(Model model) {
    //     model.addAttribute("document", new Document());
    //     model.addAttribute("subjects", subjectService.findAll());
    //     model.addAttribute("categories", categoryService.findAll());
    //     return "teacher/form";
    // }

    // @PostMapping("/teacher/save")
    // public String save(@RequestParam("file") MultipartFile file,
    //         @ModelAttribute Document document,
    //         @AuthenticationPrincipal User userDetails) throws IOException {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     String email = authentication.getName(); // Assuming email is used as username

    //     User user = userService.findByEmail(email);
    //     String storedPath = fileStorageService.storeFile(file);
    //     document.setFilePath(storedPath);
    //     document.setUploadedBy(user);
    //     document.setStatus("PENDING");
    //     document.setUploadedAt(LocalDateTime.now());
    //     documentService.save(document);
    //     return "redirect:/teacher/documents";
    // }

    // @GetMapping("/teacher/delete/{id}")
    // public String delete(@PathVariable Long id) {
    //     documentService.delete(id);
    //     return "redirect:/teacher/documents";
    // }

    @PostMapping("/teacher/status/{id}")
    public String updateStatus(@PathVariable("id") Long id, @RequestParam("status") String status,
            @AuthenticationPrincipal User userDetails, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Assuming email is used as username

        User user = userService.findByEmail(email);
        documentService.updateStatus(id, status, user);
        redirectAttributes.addFlashAttribute("message", "Status changed successfully!");
        return "redirect:/teacher/dashboard";
    }

}
