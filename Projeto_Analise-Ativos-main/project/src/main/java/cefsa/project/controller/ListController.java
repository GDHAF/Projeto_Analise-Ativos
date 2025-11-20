package cefsa.project.controller;

import cefsa.project.service.BrapiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/commodities")
public class ListController {

    private final BrapiService brapiService;

    public ListController(BrapiService brapiService) {
        this.brapiService = brapiService;
    }

    @GetMapping()
    public String list(Model model) {
        // Busca dados da API
        Object com = brapiService.getCommodities();

        // Passa os dados para a View (HTML)
        model.addAttribute("marketData", com);
        return "commodities/list"; // Retorna o template trade.html
    }
}
