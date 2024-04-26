package com.example.demomcq.controller;

import com.example.demomcq.model.Theme;
import com.example.demomcq.repository.ThemeRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/theme")
public class ThemeController {
    private final ThemeRepo themeRepo;

    public ThemeController(ThemeRepo themeRepo) {
        this.themeRepo = themeRepo;
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("themes", themeRepo.findAll());
        return "themes";
    }

    @GetMapping("/form-create-theme")
    public String getFormCreate(Model model){
        Theme theme = new Theme();
        model.addAttribute("theme",theme);
        return "insertTheme";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute(name = "theme") Theme theme){
        themeRepo.save(theme);
        return "redirect:/theme";
    }

    @GetMapping("/form-update-theme/{id}")
    public String getFormUpdate(@PathVariable("id") int id, Model model){
        Theme theme = themeRepo.findById(id).get();
        model.addAttribute("theme",theme);
        return "updateTheme";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute(name = "theme") Theme theme){
        themeRepo.save(theme);
        return "redirect:/theme";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id){
        Theme theme = themeRepo.findById(id).get();
        theme.setStatus(0);
        themeRepo.save(theme);
        return "redirect:/theme";
    }

}
