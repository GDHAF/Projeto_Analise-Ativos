package cefsa.project.controller;

import cefsa.project.service.BrapiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;

// Imports essenciais para o Logger (boa prática)
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cefsa.project.controller.AuthController.SESSION_USER_KEY;

@Controller
@RequestMapping("/details")
public class HistoricalController {

    // Criação do Logger para registrar erros
    private static final Logger logger = LoggerFactory.getLogger(HistoricalController.class);

    private final BrapiService brapiService;

    public HistoricalController(BrapiService brapiService) {
        this.brapiService = brapiService;
    }

    @GetMapping("/historical/{nome}")
    public String dashboard(Model model, @PathVariable String nome, HttpSession session) {

        // 1. PROTEÇÃO DA ROTA: Verifica se há um usuário logado
        String loggedUser = (String) session.getAttribute(SESSION_USER_KEY);

        if (loggedUser == null) {
            // Se o usuário não estiver na sessão, redireciona para o login
            return "redirect:/login";
        }

        model.addAttribute("user", loggedUser);
        model.addAttribute("assetName", nome);

        // 2. TENTA BUSCAR DADOS DA API
        try {

            // Busca dados da API (Pode gerar uma exceção)
            Object acoes = brapiService.getAcoes(nome);
            Object tick = brapiService.getAcaoEspecifica(nome);

            // Passa os dados para a View (HTML)
            model.addAttribute("acoesData", acoes);
            model.addAttribute("stockData", tick);

            return "details/historical";

        } catch (Exception e) {

            // 3. CAPTURA O ERRO
            // Registra o erro completo no log da aplicação para investigação
            logger.error("Falha ao buscar dados da API para o ativo: " + nome, e);

            // Redireciona para a página de commodities/dashboard
            return "redirect:/commodities";
        }
    }
}