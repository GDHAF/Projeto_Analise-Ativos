package cefsa.project.repository;

import cefsa.project.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Encontra um usuário pelo email.
     * Este é o método padrão usado para login e verificação de unicidade.
     * * @param email O email único do usuário.
     * @return Um Optional contendo o objeto User, se encontrado.
     */
    Optional<User> findByEmail(String email);

    /**
     * Encontra um usuário pelo email e pela senha.
     * NOTA: Em aplicações reais, NUNCA se deve buscar pela senha.
     * A validação da senha é feita no Service, comparando a senha fornecida
     * com o hash (BCrypt) armazenado. Este método é apenas ilustrativo
     * da capacidade do Spring Data.
     * * @param email O email do usuário.
     * @param senha A senha do usuário (hash).
     * @return Um Optional contendo o objeto User, se encontrado.
     */
    Optional<User> findByEmailAndSenha(String email, String senha);
}