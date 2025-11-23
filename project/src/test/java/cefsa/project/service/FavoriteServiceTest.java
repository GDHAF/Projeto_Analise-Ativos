package cefsa.project.service;

import cefsa.project.Model.AtivoFavorito;
import cefsa.project.Model.User;
import cefsa.project.repository.AtivoFavoritoRepository;
import cefsa.project.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FavoriteServiceTest {

    // Simula (Mocka) o repositório de Favoritos
    @Mock
    private AtivoFavoritoRepository ativoFavoritoRepository;

    // Simula (Mocka) o repositório de Usuários
    @Mock
    private UserRepository userRepository;

    // Injeta os Mocks no serviço que queremos testar
    @InjectMocks
    private FavoriteService favoriteService;

    // Dados de teste
    private User testUser;
    private AtivoFavorito petr4Favorite;
    private final String TEST_EMAIL = "teste@email.com";
    private final String PETR4 = "PETR4";

    @BeforeEach
    void setUp() {
        // Inicializa o usuário e o ativo que usaremos nos testes
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail(TEST_EMAIL);

        petr4Favorite = new AtivoFavorito();
        petr4Favorite.setId(10L);
        petr4Favorite.setCodigoAtivo(PETR4);
        petr4Favorite.setUsuario(testUser);
    }

    // -----------------------------------------------------------------
    // TESTES DE LISTAGEM
    // -----------------------------------------------------------------

    @Test
    void listFavorites_ShouldReturnFavorites_WhenUserExists() {
        // 1. Simula a busca do usuário pelo email (sucesso)
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(testUser));

        // 2. Simula o retorno de uma lista de favoritos para aquele usuário
        List<AtivoFavorito> expectedList = List.of(petr4Favorite);
        when(ativoFavoritoRepository.findByUsuario(testUser)).thenReturn(expectedList);

        // 3. Executa o método
        List<AtivoFavorito> actualList = favoriteService.listFavorites(TEST_EMAIL);

        // 4. Verifica o resultado
        assertFalse(actualList.isEmpty());
        assertEquals(1, actualList.size());
        assertEquals(PETR4, actualList.get(0).getCodigoAtivo());
    }

    @Test
    void listFavorites_ShouldReturnEmptyList_WhenUserDoesNotExist() {
        // 1. Simula a busca do usuário (falha)
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.empty());

        // 2. Executa o método
        List<AtivoFavorito> actualList = favoriteService.listFavorites(TEST_EMAIL);

        // 3. Verifica o resultado
        assertTrue(actualList.isEmpty());
        // Garante que o método findByUsuario NUNCA foi chamado, pois o usuário não existe
        verify(ativoFavoritoRepository, never()).findByUsuario(any());
    }

    // -----------------------------------------------------------------
    // TESTES DE ADIÇÃO
    // -----------------------------------------------------------------

    @Test
    void addFavorite_ShouldReturnTrueAndSave_WhenFavoriteIsNew() {
        // 1. Setup: Usuário existe
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(testUser));
        // 2. Setup: Ativo NÃO é favorito (retorna vazio)
        when(ativoFavoritoRepository.findByCodigoAtivoAndUsuario(PETR4, testUser)).thenReturn(Optional.empty());

        // 3. Executa o método
        boolean result = favoriteService.addFavorite(TEST_EMAIL, PETR4);

        // 4. Verifica o resultado e o comportamento
        assertTrue(result);
        // Garante que o método save() FOI chamado uma vez com qualquer objeto AtivoFavorito
        verify(ativoFavoritoRepository, times(1)).save(any(AtivoFavorito.class));
    }

    @Test
    void addFavorite_ShouldReturnFalseAndNotSave_WhenFavoriteAlreadyExists() {
        // 1. Setup: Usuário existe
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(testUser));
        // 2. Setup: Ativo JÁ é favorito (retorna o objeto existente)
        when(ativoFavoritoRepository.findByCodigoAtivoAndUsuario(PETR4, testUser)).thenReturn(Optional.of(petr4Favorite));

        // 3. Executa o método
        boolean result = favoriteService.addFavorite(TEST_EMAIL, PETR4);

        // 4. Verifica o resultado e o comportamento
        assertFalse(result);
        // Garante que o método save() NUNCA foi chamado
        verify(ativoFavoritoRepository, never()).save(any(AtivoFavorito.class));
    }

    // -----------------------------------------------------------------
    // TESTES DE REMOÇÃO
    // -----------------------------------------------------------------

    @Test
    void removeFavorite_ShouldReturnTrueAndDelete_WhenFavoriteExists() {
        // 1. Setup: Usuário existe
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(testUser));
        // 2. Setup: Ativo EXISTE na lista de favoritos
        when(ativoFavoritoRepository.findByCodigoAtivoAndUsuario(PETR4, testUser)).thenReturn(Optional.of(petr4Favorite));

        // 3. Executa o método
        boolean result = favoriteService.removeFavorite(TEST_EMAIL, PETR4);

        // 4. Verifica o resultado e o comportamento
        assertTrue(result);
        // Garante que o método delete() FOI chamado uma vez com o objeto encontrado
        verify(ativoFavoritoRepository, times(1)).delete(petr4Favorite);
    }

    @Test
    void removeFavorite_ShouldReturnFalseAndNotDelete_WhenFavoriteDoesNotExist() {
        // 1. Setup: Usuário existe
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(testUser));
        // 2. Setup: Ativo NÃO existe na lista de favoritos
        when(ativoFavoritoRepository.findByCodigoAtivoAndUsuario(PETR4, testUser)).thenReturn(Optional.empty());

        // 3. Executa o método
        boolean result = favoriteService.removeFavorite(TEST_EMAIL, PETR4);

        // 4. Verifica o resultado e o comportamento
        assertFalse(result);
        // Garante que o método delete() NUNCA foi chamado
        verify(ativoFavoritoRepository, never()).delete(any());
    }
}