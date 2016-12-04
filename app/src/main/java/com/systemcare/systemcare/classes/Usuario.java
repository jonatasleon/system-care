package com.systemcare.systemcare.classes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jonatasleon on 04/12/16.
 */
public class Usuario {

    @SerializedName("PacientesApi")
    Integer id;

    @SerializedName("Email")
    String email;

    @SerializedName("Senha")
    String senha;

    public Usuario(String email, String senha) {
        this(0, email, senha);
    }

    public Usuario(Integer id, String email, String senha) {
        this.id = id;
        this.email = email;
        this.senha = senha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
}
