package com.example.fxmltableview;


import javafx.scene.control.TextInputDialog;

public class Livraria {

    private int id;
    private String nome;
    private String endereco;
    private String telefone;
    private String gerente;

    public Livraria(String nome, String endereco, String telefone, String gerente) {
        this.nome = String.valueOf(nome);
        this.endereco = String.valueOf(endereco);
        this.telefone = String.valueOf(telefone);
        this.gerente = String.valueOf(gerente);
    }

    public Livraria(int id, String nome, String endereco, String telefone, String gerente) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.gerente = gerente;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getGerente() {
        return gerente;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setGerente(String gerente) {
        this.gerente = gerente;
    }


}
