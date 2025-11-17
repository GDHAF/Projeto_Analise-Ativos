package cefsa.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BrapiService {

    // URL base da API que vamos consumir
    private static final String BRAPI_V2_URL = "https://brapi.dev/api/v2";
    private static final String BRAPI_V1_URL = "https://brapi.dev/api";

    private final RestTemplate restTemplate;

    // O Spring injeta o RestTemplate que criamos no RestConfig
    public BrapiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Busca cotações de câmbio.
     * @param moedas Ex: "USD-BRL,EUR-BRL"
     */
    public Object getCambio(String moedas) {
        String url = BRAPI_V2_URL + "/currency?currency=" + moedas;

        // Usamos Object.class para que o Spring/Jackson
        // converta o JSON de resposta em um Mapa
        return restTemplate.getForObject(url, Object.class);
    }

    /**
     * Busca uma lista de ações (filtrando por tipo=stock)
     */
    public Object getAcoes() {
        String url = BRAPI_V1_URL + "/quote/list?type=stock";
        return restTemplate.getForObject(url, Object.class);
    }

    /**
     * Busca "commodities" usando o filtro de setor (ex: Petróleo, Minério)
     */
    public Object getCommodities() {
        // Exemplo: Setor "Energy Minerals" (Petrobras, Prio, etc)
        String url = BRAPI_V1_URL + "/quote/list?sector=Energy Minerals";
        return restTemplate.getForObject(url, Object.class);
    }

    /**
     * Busca dados de um Ticker específico
     * @param ticker Ex: "PETR4"
     */
    public Object getAcaoEspecifica(String ticker) {
        String url = BRAPI_V1_URL + "/quote/" + ticker;
        return restTemplate.getForObject(url, Object.class);
    }
}