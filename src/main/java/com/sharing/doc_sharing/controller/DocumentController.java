package com.sharing.doc_sharing.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sharing.doc_sharing.model.Document;
import com.sharing.doc_sharing.model.User;
import com.sharing.doc_sharing.service.CategoryService;
import com.sharing.doc_sharing.service.DocumentService;
import com.sharing.doc_sharing.service.FileStorageService;
import com.sharing.doc_sharing.service.SubjectService;
import com.sharing.doc_sharing.service.UserService;

import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/documents")
public class DocumentController {
    private final DocumentService documentService;
    private final SubjectService subjectService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final FileStorageService fileStorageService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("documents", documentService.findAll());
        return "document/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("document", new Document());
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "document/form";
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
        return "redirect:/admin/documents";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        documentService.delete(id);
        return "redirect:/admin/documents";
    }

    @PostMapping("/status/{id}")
    public String updateStatus(@PathVariable("id") Long id, @RequestParam("status") String status,
            @AuthenticationPrincipal User userDetails, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Assuming email is used as username

        User user = userService.findByEmail(email);
        documentService.updateStatus(id, status, user);
        redirectAttributes.addFlashAttribute("message", "Status changed successfully!");
        return "redirect:/admin/documents";
    }
}
