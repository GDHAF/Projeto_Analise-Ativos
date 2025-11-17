/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cefsa.project.Model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "usuarios")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email; // Usado como username

    @Column(nullable = false)
    private String senha; // Armazenada com hash (BCrypt)

    // Relacionamento com os ativos favoritados pelo usuário
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
<<<<<<< HEAD
    private Set<AtivoFavorito> favoritos;
=======
    private Set<Ativo_Favorito> favoritos;
>>>>>>> 15d46159760c33366589129991c5cbb3784cc903

    // Construtores, Getters e Setters
    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

<<<<<<< HEAD
    public Set<AtivoFavorito> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(Set<AtivoFavorito> favoritos) {
=======
    public Set<Ativo_Favorito> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(Set<Ativo_Favorito> favoritos) {
>>>>>>> 15d46159760c33366589129991c5cbb3784cc903
        this.favoritos = favoritos;
    }
}