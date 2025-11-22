package cefsa.project.controller;

import cefsa.project.service.BrapiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/details")
public class HistoricalController {

    private final BrapiService brapiService;

    public HistoricalController(BrapiService brapiService) {
        this.brapiService = brapiService;
    }

    @GetMapping("/historical/{nome}")
    public String dashboard(Model model, @PathVariable String nome) {
        // Busca dados da API
        Object acoes = brapiService.getAcoes(nome);
        Object tick = brapiService.getAcaoEspecifica(nome);

        // Passa os dados para a View (HTML)
        model.addAttribute("acoesData", acoes);
        model.addAttribute("stockData", tick);

        return "details/historical";
    }
}
