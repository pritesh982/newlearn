package com.sharing.doc_sharing.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.sharing.doc_sharing.dto.ChangePasswordDto;
import com.sharing.doc_sharing.model.Category;
import com.sharing.doc_sharing.model.Document;
import com.sharing.doc_sharing.model.Subject;
import com.sharing.doc_sharing.model.User;
import com.sharing.doc_sharing.service.SubjectService;
import com.sharing.doc_sharing.service.UserService;

import jakarta.validation.Valid;

import com.sharing.doc_sharing.service.CategoryService;
import com.sharing.doc_sharing.service.DocumentService;
import com.sharing.doc_sharing.service.FileStorageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
@RequestMapping("/student")
public class StudentController {

    private final SubjectService subjectService;
    private final DocumentService documentService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final FileStorageService fileStorageService;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/dashboard")
    public String studentDashboard(Model model) {
        List<Subject> subjects = subjectService.findAll();
        model.addAttribute("subjects", subjects);
        model.addAttribute("categories", categoryService.findAll());
        return "student/dashboard"; // your HTML file path under templates/

    }

    @GetMapping("/show/{id}")
    public String getDocumentsBySubject(@PathVariable("id") Long id, Model model) {
        Subject subject = subjectService.findById(id);
        List<Document> documents = documentService.findBySubjectIdAndStatus(id, "APPROVED");

        // Group documents by category
        Map<String, List<Document>> categoryMap = documents.stream()
                .collect(Collectors.groupingBy(doc -> doc.getCategory().getName()));

        model.addAttribute("subject", subject);
        model.addAttribute("categoryMap", categoryMap);
        return "student/subject";
    }

    @GetMapping("/showcat/{id}")
    public String getDocumentsByCategory(@PathVariable("id") Long id, Model model) {
        Category category = categoryService.findById(id);
        List<Document> documents = documentService.findByCategoryIdAndStatus(id, "APPROVED");

        // Group documents by subject
        Map<String, List<Document>> subjectMap = documents.stream()
                .collect(Collectors.groupingBy(doc -> doc.getSubject().getName()));

        model.addAttribute("category", category);
        model.addAttribute("subjectMap", subjectMap);
        return "student/category";
    }

    @GetMapping("/upload")
    public String upload(Model model) {
        model.addAttribute("document", new Document());
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "student/upload";
    }

    @PostMapping("/save")
    public String save(@RequestParam("file") MultipartFile file,
            @ModelAttribute Document document,
            @AuthenticationPrincipal User userDetails) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Assuming email is used as username

        User user = userService.findByEmail(email);
        String storedPath = fileStorageService.storeFile(file);
        document.setFilePath(storedPath);
        document.setUploadedBy(user);
        document.setStatus("PENDING");
        document.setUploadedAt(LocalDateTime.now());
        documentService.save(document);
        return "redirect:/student/dashboard";
    }

    @GetMapping("/change-password")
    public String showChangePasswordForm(Model model) {
        model.addAttribute("changePasswordForm", new ChangePasswordDto());
        return "student/change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@ModelAttribute("changePasswordForm") @Valid ChangePasswordDto form,
            BindingResult result,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "student/change-password";
        }

        User user = userService.findByEmail(currentUser.getUsername()); // or getUsername() if using email
        if (!passwordEncoder.matches(form.getOldPassword(), user.getPassword())) {
            result.rejectValue("oldPassword", "", "Old password is incorrect");
            return "student/change-password";
        }

        if (!form.getNewPassword().equals(form.getConfirmNewPassword())) {
            result.rejectValue("confirmNewPassword", "", "Passwords do not match");
            return "student/change-password";
        }

        user.setPassword(passwordEncoder.encode(form.getNewPassword()));
        userService.savePassword(user);

        redirectAttributes.addFlashAttribute("message", "Password changed successfully!");
        return "redirect:/student/dashboard";
    }
}