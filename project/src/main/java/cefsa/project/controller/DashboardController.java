package cefsa.project.controller;

import cefsa.project.service.BrapiService;
import cefsa.project.service.FavoriteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession; // Importação essencial

import java.util.ArrayList;
import java.util.List;

import static cefsa.project.controller.AuthController.SESSION_USER_KEY;

@Controller
@RequestMapping("/dashboard")
public class DashboardController { // <-- Classe Principal

    private final BrapiService brapiService;
    private final FavoriteService favoriteService;

    public DashboardController(BrapiService brapiService, FavoriteService favoriteService) {
        this.brapiService = brapiService;
        this.favoriteService = favoriteService;
    }

    @GetMapping("/dash") // <-- Método Mapeado
    public String dashboard(Model model, HttpSession session) {
        String loggedUser = (String) session.getAttribute(SESSION_USER_KEY);
        if (loggedUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", loggedUser);

        // 1. LÓGICA DE BUSCA DA API
        // Busca os dados (retornam Map<String, Object> ou similar)
        Object acoes = brapiService.getAcoes("PETR4");
        Object acoesVALE3 = brapiService.getAcoes("VALE3");
        Object acoesITUB4 = brapiService.getAcoes("ITUB4");
        Object cambio = brapiService.getCambio("USD-BRL");
        Object cambio1 = brapiService.getCambio("EUR-BRL");
        Object cambio2 = brapiService.getCambio("BTC-BRL");

        // 2. COMBINAÇÃO DOS DADOS (CÂMBIO)
        List<Object> combinedCambio = new ArrayList<>();
        if (cambio != null) combinedCambio.add(cambio);
        if (cambio1 != null) combinedCambio.add(cambio1);
        if (cambio2 != null) combinedCambio.add(cambio2);

        // COMBINAÇÃO DOS DADOS (AÇÕES)
        List<Object> combinedAcoes = new ArrayList<>();
        if (acoes != null) combinedAcoes.add(acoes);
        if (acoesVALE3 != null) combinedAcoes.add(acoesVALE3);
        if (acoesITUB4 != null) combinedAcoes.add(acoesITUB4);


        // 3. PASSA OS DADOS COMBINADOS PARA O MODEL
        model.addAttribute("combinedCambio", combinedCambio);
        model.addAttribute("combinedAcoes", combinedAcoes); // Adicionando ações combinadas

        // 4. LÓGICA DE FAVORITOS (Contagem)
        model.addAttribute("favoritosCount", favoriteService.listFavorites(loggedUser).size());

        return "dashboard/dashboard";
    }
}