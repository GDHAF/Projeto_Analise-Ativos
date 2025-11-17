/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cefsa.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

// Importe seus DTOs e Services
// import com.exemplo.assettracker.dto.AtivoExternoDTO;
// import com.exemplo.assettracker.service.AtivoService;

@Controller
public class AtivoController {

    // @Autowired
    // private AtivoService ativoService;

    /**
     * Exibe a "Página Principal" com o formulário de busca.
     */
    @GetMapping("/")
    public String exibirPaginaPrincipal(Model model) {
        // Pode carregar "ativos em alta" aqui, se desejar
        // List<AtivoExternoDTO> emAlta = ativoService.buscarAtivosEmAlta();
        // model.addAttribute("ativosEmAlta", emAlta);
        return "index"; // -> resources/templates/index.html
    }

    /**
     * Processa a busca da "Página Principal" e redireciona para "Detalhes do Ativo".
     */
    @GetMapping("/buscar")
    public String buscarAtivo(@RequestParam("codigo") String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return "redirect:/";
        }
        // Redireciona para a URL de detalhes
        return "redirect:/ativo/" + codigo.toUpperCase();
    }

    /**
     * Exibe a "Detalhes do Ativo" (Cotação, Variação).
     */
    @GetMapping("/ativo/{codigo}")
    public String exibirDetalhesAtivo(@PathVariable("codigo") String codigo, Model model, Principal principal) {
        // AtivoExternoDTO ativo = ativoService.buscarDadosExternos(codigo);
        // model.addAttribute("ativo", ativo);

        // boolean isFavorito = false;
        // if (principal != null) {
        //    isFavorito = ativoService.isFavorito(principal.getName(), codigo);
        // }
        // model.addAttribute("isFavorito", isFavorito);

        return "detalhes-ativo"; // -> resources/templates/detalhes-ativo.html
    }

    /**
     * Exibe a "Tela de Favoritos" do usuário logado.
     * (Protegido pelo Spring Security - CT-002)
     */
    @GetMapping("/favoritos")
    public String exibirFavoritos(Model model, Principal principal) {
        // O 'principal' não será nulo, pois o Spring Security barra antes
        String emailUsuario = principal.getName();
        // List<AtivoExternoDTO> favoritos = ativoService.listarFavoritos(emailUsuario);
        // model.addAttribute("favoritos", favoritos);
        return "favoritos"; // -> resources/templates/favoritos.html
    }

    /**
     * Adiciona um ativo aos favoritos.
     * (Protegido pelo Spring Security - CT-002)
     */
    @PostMapping("/api/favoritos/adicionar")
    public String adicionarFavorito(@RequestParam("codigo") String codigo, Principal principal, RedirectAttributes redirectAttributes) {
        // O 'principal' não será nulo
        try {
            // String emailUsuario = principal.getName();
            // ativoService.adicionarFavorito(emailUsuario, codigo);
            // redirectAttributes.addFlashAttribute("successMessage", "Ativo adicionado!");
        } catch (Exception e) {
            // redirectAttributes.addFlashAttribute("errorMessage", "Erro ao adicionar ativo.");
        }
        
        // Redireciona de volta para a página de detalhes
        return "redirect:/ativo/" + codigo;
    }
}