package cefsa.project.controller;

import cefsa.project.service.BrapiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final BrapiService brapiService;

    public DashboardController(BrapiService brapiService) {
        this.brapiService = brapiService;
    }

    @GetMapping("/dash")
    public String dashboard(Model model) {
        // Busca dados da API
        Object acoes = brapiService.getAcoes("PETR4");
        Object acoesVALE3 = brapiService.getAcoes("VALE3");
        Object acoesITUB4 = brapiService.getAcoes("ITUB4");
        Object cambio = brapiService.getCambio("USD-BRL");
        Object cambio1 = brapiService.getCambio("EUR-BRL");
        Object cambio2 = brapiService.getCambio("BTC-BRL");

        // Passa os dados para a View (HTML)
        model.addAttribute("acoesData", acoes);
        model.addAttribute("acoesDataVALE3", acoesVALE3);
        model.addAttribute("acoesDataITUB4", acoesITUB4);
        model.addAttribute("cambioData", cambio);
        model.addAttribute("cambioData1", cambio1);
        model.addAttribute("cambioData2", cambio2);
        return "dashboard/dashboard"; // Retorna o template trade.html
    }
}

