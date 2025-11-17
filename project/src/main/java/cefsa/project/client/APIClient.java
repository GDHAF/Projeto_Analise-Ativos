/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cefsa.project.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 *
 * @author henri
 */
public class APIClient {
     private final WebClient webClient;

    // URL base da API externa (Ex: Alpha Vantage, Yahoo Finance)
    // Coloque isso no application.properties
    private final String API_BASE_URL = "https://api.exemplo.finance/v1";

    public FinanceApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(API_BASE_URL).build();
    }

    /**
     * Busca os dados de um ativo (Cotação, Variação) na API externa.
     * Este é o método usado para as telas "Página Principal" e "Detalhes do Ativo".
     */
    public Mono<Object> buscarAtivo(String codigo) {
        // O DTO 'AtivoExternoDTO' deve mapear a resposta JSON da API
        
        // Exemplo de chamada (ajuste o endpoint e DTO conforme a API real)
        return this.webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/quote/{codigo}")
                // .queryParam("apikey", "SUA_API_KEY") // Adicione params de API key se necessário
                .build(codigo)
            )
            .retrieve() // Executa a requisição
            .bodyToMono(Object.class); // Converte a resposta para seu DTO
            // .bodyToMono(AtivoExternoDTO.class);
    }
}
