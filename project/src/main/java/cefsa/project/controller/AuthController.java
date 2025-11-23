package cefsa.project.controller;

import cefsa.project.Model.User;
import cefsa.project.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession; // Use 'javax.servlet.http.HttpSession' para Spring Boot < 3

import java.util.Optional;

@Controller
public class AuthController {

    // Chave para identificar o usuário logado na sessão.
    // Será usada tanto aqui quanto no DashboardController.
    public static final String SESSION_USER_KEY = "loggedUser";

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder; // Injeção

    public AuthController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        // Retorna o template do formulário de login (e.g., /templates/login/login.html)
        return "login/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String email, // Renomeado para 'email' para clareza
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {

        // 1. Busca o usuário pelo email (username)
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // 2. Compara a senha fornecida (texto puro) com o hash (BCrypt) armazenado
            //
            if (passwordEncoder.matches(password, user.getSenha())) {

                // LOGIN BEM-SUCEDIDO: Armazena o email (ou ID) do usuário na sessão.
                session.setAttribute(SESSION_USER_KEY, user.getEmail());

                // Redireciona para o dashboard
                return "redirect:/dashboard/dash";
            }
        }

        // 3. SE a busca falhou (usuário não existe) OU a senha não correspondeu
        model.addAttribute("loginError", "Usuário ou senha inválidos.");
        return "login/login";
    }
    @GetMapping("/register")
    public String showRegisterForm() {
        // Retorna o template do formulário de cadastro
        return "login/register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               @RequestParam("confirmPassword") String confirmPassword,
                               RedirectAttributes redirectAttributes) {

        // 1. Validação Simples de Senha
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "As senhas não coincidem!");
            return "redirect:/register";
        }

        // 2. Verifica se o usuário (email) já existe
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Este email já está cadastrado.");
            return "redirect:/register";
        }

        // 3. Cria e Salva o Novo Usuário
        User newUser = new User();
        newUser.setEmail(email);

        // Criptografa a senha antes de salvar!
        String hashedPassword = passwordEncoder.encode(password);
        newUser.setSenha(hashedPassword);

        userRepository.save(newUser);

        // 4. Redireciona para o login com mensagem de sucesso
        redirectAttributes.addFlashAttribute("successMessage", "Cadastro realizado com sucesso! Faça login.");
        return "redirect:/login";
    }



    @PostMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {

        // Invalida a sessão, removendo todos os atributos e encerrando a sessão.
        session.invalidate();

        redirectAttributes.addFlashAttribute("logoutMessage", "Sessão encerrada com sucesso!");

        // Redireciona para a página de login
        return "redirect:/login";
    }
}