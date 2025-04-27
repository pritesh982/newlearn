package com.sharing.doc_sharing.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.sharing.doc_sharing.model.Subject;
import com.sharing.doc_sharing.service.SubjectService;

@Controller
@RequestMapping("/admin/subjects")
@PreAuthorize("hasRole('ADMIN')")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public String listSubjects(Model model) {
        model.addAttribute("subjects", subjectService.findAll());
        return "subject/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("subject", new Subject());
        return "subject/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Subject subject) {
        subjectService.save(subject);
        return "redirect:/admin/subjects";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("subject", subjectService.findById(id));
        return "subject/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        subjectService.delete(id);
        return "redirect:/admin/subjects";
    }
}
