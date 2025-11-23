package cefsa.project.controller;

import cefsa.project.service.BrapiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ativos") // URL base para esta controller
public class AtivosController {

    private final BrapiService brapiService;

    // Injeção de dependência do nosso serviço
    public AtivosController(BrapiService brapiService) {
        this.brapiService = brapiService;
    }

    /**
     * Rota para buscar Câmbio.
     * Exemplo de chamada: GET /api/ativos/cambio?moedas=USD-BRL,EUR-BRL
     */
    @GetMapping("/cambio")
    public ResponseEntity<Object> getCambio(
            @RequestParam("moedas") String moedas) {

        Object dados = brapiService.getCambio(moedas);
        return ResponseEntity.ok(dados);
    }

    /**
     * Rota para listar Ações.
     * Exemplo de chamada: GET /api/ativos/acoes
     */
    @GetMapping("/acoes")
    public ResponseEntity<Object> getAcoes() {
        Object dados = brapiService.getAcoes("PETR4");
        return ResponseEntity.ok(dados);
    }

    /**
     * Rota para listar "Commodities" (baseado no setor).
     * Exemplo de chamada: GET /api/ativos/commodities
     */
    @GetMapping("/commodities")
    public ResponseEntity<Object> getCommodities() {
        Object dados = brapiService.getCommodities();
        return ResponseEntity.ok(dados);
    }

    /**
     * Rota para buscar uma ação específica.
     * Exemplo de chamada: GET /api/ativos/acao/PETR4
     */
    @GetMapping("/acao/{ticker}")
    public ResponseEntity<Object> getAcao(
            @PathVariable("ticker") String ticker) {

        Object dados = brapiService.getAcaoEspecifica(ticker);
        return ResponseEntity.ok(dados);
    }
}