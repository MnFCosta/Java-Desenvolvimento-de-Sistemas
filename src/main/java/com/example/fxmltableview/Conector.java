package com.example.fxmltableview;

import javafx.scene.control.TextInputDialog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

/**
 * @author User
 */

public class Conector {
    private final String url;
    private final String user;
    private final String senha;
    private static Connection connection;

    Scanner scan = new Scanner(System.in);
    Scanner scanLine = new Scanner(System.in);

    public Conector() throws ClassNotFoundException {
        this.url = "jdbc:mysql://localhost:3306/livraria?useTimezone=true&serverTimezone=UTC";
        this.user = "root";
        this.senha = "info2k21";
        this.connection = null;
        conectar();
    }

    private void conectar() throws ClassNotFoundException {
        try {
            if (this.connection == null) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                this.connection = DriverManager.getConnection(this.url, this.user, this.senha);
                this.connection.setAutoCommit(false);
            }
        } catch (SQLException e) { //Erro de conexão com o banco de dados
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }


    public ResultSet select(String sql) throws Exception {
        PreparedStatement stm = connection.prepareStatement(sql);
        ResultSet resultado = stm.executeQuery();
        return resultado;
    }

    public void inserir(String sql) throws Exception {
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.execute();
        connection.commit();
    }

    //exemplo de PreparedStatement com substituição por ?
    public void exemploMontagemSql() throws SQLException {
        String sql = "insert into contato(nome, sobrenome, idade) values(?,?,?);";
        PreparedStatement stm = connection.prepareStatement(sql);
//            stm.setString(1, object.getValue1()); //substitui o primeiro ? pelo valor do objeto
//            stm.setString(2, object.getValue2()); //substitui o segundo ? pelo valor do objeto
//            stm.setInt(3, object.getValue3()); //substitui o terceiro ? pelo valor do objeto
        stm.execute();
        connection.commit();
    }


    //exemplo de insert com passagem de objeto por parametro
    public void inserirEditora(Livraria livraria) throws SQLException {
        String sql = "insert into editora(nome, endereco, telefone, gerente) values(?,?,?,?);";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setString(1, livraria.getNome()); //substitui o primeiro ? pelo valor do objeto
        stm.setString(2, livraria.getEndereco()); //substitui o segundo ? pelo valor do objeto
        stm.setString(3, livraria.getTelefone()); //substitui o segundo ? pelo valor do objeto
        stm.setString(4, livraria.getGerente()); //substitui o segundo ? pelo valor do objeto
        stm.execute();
        connection.commit();
    }


    public static void remover(Livraria l) throws SQLException {

        String sql = "DELETE FROM editora WHERE nome = ? and telefone = ?;";

        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setString(1, l.getNome());
        stm.setString(2, l.getTelefone());
        System.out.println(stm);
        stm.execute();
        connection.commit();
    }

    public static void alterarEditora(Livraria l) throws SQLException {

        TextInputDialog newNome = new TextInputDialog();
        newNome.setTitle("Text Input Dialog");
        newNome.setHeaderText("Qual é o novo nome da editora?");
        newNome.setContentText("Nome: ");


        Optional<String> resultnome = newNome.showAndWait();


        String sql = "UPDATE `livraria`.`editora` SET `nome` = ? WHERE telefone = ?;";

        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setString(1, resultnome.get());
        stm.setString(2, l.getTelefone());
        System.out.println(stm);
        stm.execute();
        connection.commit();

    }
}

