package cefsa.project.repository;

import cefsa.project.Model.AtivoFavorito;
import cefsa.project.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtivoFavoritoRepository extends JpaRepository<AtivoFavorito, Long> {

    /**
     * Busca todos os ativos favoritos de um usuário específico.
     * O Spring Data gera a query automaticamente a partir do nome do método.
     * * @param usuario O objeto User para o qual os favoritos devem ser buscados.
     * @return Uma lista de AtivoFavorito.
     */
    List<AtivoFavorito> findByUsuario(User usuario);

    /**
     * Busca um ativo favorito específico (pelo código) para um dado usuário.
     * Útil para verificar se o ativo já foi adicionado.
     * * @param codigoAtivo O código do ativo (ex: "PETR4").
     * @param usuario O objeto User dono do favorito.
     * @return Um Optional contendo o AtivoFavorito, se encontrado.
     */
    Optional<AtivoFavorito> findByCodigoAtivoAndUsuario(String codigoAtivo, User usuario);
}