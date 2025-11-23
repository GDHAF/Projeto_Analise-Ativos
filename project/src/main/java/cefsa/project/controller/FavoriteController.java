package cefsa.project.controller;

import cefsa.project.Model.AtivoFavorito;
import cefsa.project.service.FavoriteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static cefsa.project.controller.AuthController.SESSION_USER_KEY;

@Controller
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    /**
     * Exibe a lista de ativos favoritos do usuário.
     */
    @GetMapping
    public String listFavorites(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        String userEmail = (String) session.getAttribute(SESSION_USER_KEY);

        if (userEmail == null) {
            return "redirect:/login"; // Proteção de rota
        }

        // Busca a lista de ativos favoritos
        List<AtivoFavorito> favoritos = favoriteService.listFavorites(userEmail);

        model.addAttribute("favoritos", favoritos);
        model.addAttribute("userEmail", userEmail);

        return "favorites/list"; // Retorna o template de listagem
    }

    /**
     * Adiciona um ativo favorito. Chamado via POST de um formulário ou botão.
     */
    @PostMapping("/add")
    public String addFavorite(@RequestParam("codigoAtivo") String codigoAtivo,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {

        String userEmail = (String) session.getAttribute(SESSION_USER_KEY);
        if (userEmail == null) return "redirect:/login";

        boolean added = favoriteService.addFavorite(userEmail, codigoAtivo);

        if (added) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Ativo " + codigoAtivo + " adicionado aos favoritos!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Ativo " + codigoAtivo + " já está na sua lista ou o usuário não existe.");
        }

        // Redireciona para a página de onde a ação foi chamada ou para a lista de favoritos
        return "redirect:/favorites";
    }

    /**
     * Remove um ativo favorito. Chamado via POST.
     */
    @PostMapping("/remove")
    public String removeFavorite(@RequestParam("codigoAtivo") String codigoAtivo,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {

        String userEmail = (String) session.getAttribute(SESSION_USER_KEY);
        if (userEmail == null) return "redirect:/login";

        boolean removed = favoriteService.removeFavorite(userEmail, codigoAtivo);

        if (removed) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Ativo " + codigoAtivo + " removido dos favoritos.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Erro: Ativo não encontrado na sua lista.");
        }

        return "redirect:/favorites";
    }
}