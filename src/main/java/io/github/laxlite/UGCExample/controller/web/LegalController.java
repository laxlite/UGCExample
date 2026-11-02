package io.github.laxlite.UGCExample.controller.web;

import io.github.laxlite.UGCExample.service.LegalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/legal")
public class LegalController {

    private final LegalService legalService;

    // Обрабатывает запросы к странице в legal
    // Кладет в контекст:
    //    - Список страниц legal: sections
    //    - Текущую показываемую страницу: section
    //    -
    //

    @GetMapping("/{slug}")
    public String getLayout(
            @PathVariable String slug,
            Model model
    ){
        // данные для фрагмента legal
        model.addAttribute("section", legalService.getSectionBySlug(slug));
        model.addAttribute("sections", legalService.getAllSections());

        // данные для отображения фрагмента legal в main
        model.addAttribute("templateName", "user/fragment/legal/legal");
        model.addAttribute("fragmentName", "legalLayout");
        return "user/layout/main";
    }

    // Обрабатывает запросы к франгментам в legal
    @GetMapping("/section/{slug}")
    public String getFragment(
            @PathVariable String slug,
            Model model
    ){
        // добавить в контекст документ
        model.addAttribute("section", legalService.getSectionBySlug(slug));
        return "user/fragment/legal/legal::content";
    }

    @Autowired
    public LegalController(LegalService legalService){
        this.legalService = legalService;
    }
}
