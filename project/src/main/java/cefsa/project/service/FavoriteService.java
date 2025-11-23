package cefsa.project.service;

import cefsa.project.Model.AtivoFavorito;
import cefsa.project.Model.User;
import cefsa.project.repository.AtivoFavoritoRepository;
import cefsa.project.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {

    private final AtivoFavoritoRepository ativoFavoritoRepository;
    private final UserRepository userRepository;

    public FavoriteService(AtivoFavoritoRepository ativoFavoritoRepository, UserRepository userRepository) {
        this.ativoFavoritoRepository = ativoFavoritoRepository;
        this.userRepository = userRepository;
    }

    /** Busca o usuário pelo email (String) armazenado na sessão. */
    private Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /** * Lista todos os favoritos de um usuário.
     */
    public List<AtivoFavorito> listFavorites(String userEmail) {
        Optional<User> userOptional = findUserByEmail(userEmail);
        return userOptional.map(ativoFavoritoRepository::findByUsuario).orElse(List.of());
    }

    /**
     * Adiciona um ativo à lista de favoritos do usuário.
     * Retorna true se adicionado, false se já existia.
     */
    public boolean addFavorite(String userEmail, String codigoAtivo) {
        Optional<User> userOptional = findUserByEmail(userEmail);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Verifica se o ativo já é favorito
            Optional<AtivoFavorito> existing = ativoFavoritoRepository.findByCodigoAtivoAndUsuario(codigoAtivo, user);

            if (existing.isEmpty()) {
                AtivoFavorito novoFavorito = new AtivoFavorito();
                novoFavorito.setCodigoAtivo(codigoAtivo);
                novoFavorito.setUsuario(user);
                ativoFavoritoRepository.save(novoFavorito);
                return true;
            }
        }
        return false;
    }

    /**
     * Remove um ativo dos favoritos.
     */
    public boolean removeFavorite(String userEmail, String codigoAtivo) {
        Optional<User> userOptional = findUserByEmail(userEmail);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<AtivoFavorito> existing = ativoFavoritoRepository.findByCodigoAtivoAndUsuario(codigoAtivo, user);

            if (existing.isPresent()) {
                ativoFavoritoRepository.delete(existing.get());
                return true;
            }
        }
        return false;
    }
}