package cefsa.project.controller;

import cefsa.project.service.BrapiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession; // Importação essencial para Sessão

// Importa a chave da sessão do AuthController para consistência
import static cefsa.project.controller.AuthController.SESSION_USER_KEY;

@Controller
@RequestMapping("/commodities")
public class ListController {

    private final BrapiService brapiService;

    public ListController(BrapiService brapiService) {
        this.brapiService = brapiService;
    }

    @GetMapping()
    public String list(Model model, HttpSession session) {

        // **********************************************
        // 1. PROTEÇÃO DA ROTA: Verifica se há um usuário logado
        // **********************************************
        String loggedUser = (String) session.getAttribute(SESSION_USER_KEY);

        if (loggedUser == null) {
            // Se o usuário não estiver na sessão, redireciona para o login
            return "redirect:/login";
        }

        // Adiciona o nome do usuário logado ao modelo (opcional)
        model.addAttribute("user", loggedUser);

        // Busca dados da API (SÓ EXECUTA SE O USUÁRIO ESTIVER LOGADO)
        Object com = brapiService.getCommodities();

        // Passa os dados para a View (HTML)
        model.addAttribute("marketData", com);
        return "commodities/list"; // Retorna o template commodities/list
    }
}