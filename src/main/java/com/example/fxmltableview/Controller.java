package com.example.fxmltableview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Controller implements Initializable {

    @FXML
    private Button buttonRemover;
    @FXML
    private Button buttonAdd;

    @FXML
    private Button buttonAlterEditora;

    @FXML
    private TableView<Livraria> tableLivraria;

    @FXML
    private TableColumn<Livraria, Integer> tableId;
    @FXML
    private TableColumn<Livraria, String> tableNomes;
    @FXML
    private TableColumn<Livraria, String> tableEndereco;
    @FXML
    private TableColumn<Livraria, String> tableTelefones;
    @FXML
    private TableColumn<Livraria, String> tableGerente;

    private List<Livraria> listLivraria = new ArrayList();
    private ObservableList<Livraria> observableListLivraria;

    ObservableList<Livraria> oblist = FXCollections.observableArrayList();


    Conector conector = new Conector();

    public Controller() throws ClassNotFoundException {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadTableView();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void loadTableView() throws SQLException {

        try {
            Connection con = Conector.getConnection();

            ResultSet rs = con.createStatement().executeQuery("select * from editora");
            while (rs.next()) {
                oblist.add(new Livraria(rs.getInt("id"), rs.getString("nome"), rs.getString("endereco")
                        , rs.getString("telefone"), rs.getString("gerente")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableNomes.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        tableEndereco.setCellValueFactory(new PropertyValueFactory<>("Endereco"));
        tableTelefones.setCellValueFactory(new PropertyValueFactory<>("Telefone"));
        tableGerente.setCellValueFactory(new PropertyValueFactory<>("Gerente"));

        tableLivraria.setItems(oblist);
    }


    @FXML
    public void removerDataEditora() throws SQLException {
        Livraria l = tableLivraria.getSelectionModel().getSelectedItem();
        if (l != null) {
            System.out.println("Cliente removido: " + l.getNome());
            tableLivraria.getItems().remove(l);
            listLivraria.remove(l);
        }
        Conector.remover(l);

    }

    public void addEditora(ActionEvent actionEvent) throws SQLException {

        TextInputDialog nome = new TextInputDialog();
        nome.setTitle("Text Input Dialog");
        nome.setHeaderText("Qual é o nome da editora?");
        nome.setContentText("Nome: ");

        TextInputDialog endereco = new TextInputDialog();
        endereco.setTitle("Text Input Dialog");
        endereco.setHeaderText("Qual é o endereço da editora?");
        endereco.setContentText("Endereço: ");

        TextInputDialog telefone = new TextInputDialog();
        telefone.setTitle("Text Input Dialog");
        telefone.setHeaderText("Qual é o telefone da editora?");
        telefone.setContentText("Telefone: ");

        TextInputDialog gerente = new TextInputDialog();
        gerente.setTitle("Text Input Dialog");
        gerente.setHeaderText("Qual é o gerente da editora?");
        gerente.setContentText("Gerente: ");


        Optional<String> resultnome = nome.showAndWait();
        Optional<String> resultendereco = endereco.showAndWait();
        Optional<String> resulttelefone = telefone.showAndWait();
        Optional<String> resultgerente = gerente.showAndWait();

        if (resultnome.isPresent() && resultendereco.isPresent() && resulttelefone.isPresent() && resultgerente.isPresent()) {
            Livraria editorateste = new Livraria(resultnome.get(), resultendereco.get(), resulttelefone.get(), resultgerente.get());
            listLivraria.add(editorateste);

            conector.inserirEditora(editorateste);

            try {
                Connection con = Conector.getConnection();
                System.out.println("sus");
                String str = String.format("select * from editora where nome = '%s' and telefone = '%s'", resultnome.get(), resulttelefone.get());
                System.out.println(str);
                ResultSet rs = con.createStatement().executeQuery(str);
//
                while (rs.next()) {
                    oblist.add(new Livraria(rs.getInt("id"), rs.getString("nome"), rs.getString("endereco")
                            , rs.getString("telefone"), rs.getString("gerente")));
                }

            } catch (SQLException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            tableId.setCellValueFactory(new PropertyValueFactory<>("id"));
            tableNomes.setCellValueFactory(new PropertyValueFactory<>("Nome"));
            tableEndereco.setCellValueFactory(new PropertyValueFactory<>("Endereco"));
            tableTelefones.setCellValueFactory(new PropertyValueFactory<>("Telefone"));
            tableGerente.setCellValueFactory(new PropertyValueFactory<>("Gerente"));

            tableLivraria.setItems(oblist);

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("[ERRO] Valor inválido!");
            alert.setHeaderText("Parece que um ou mais valores estão faltando...");
            alert.setContentText("Insira valores em todas as opções!");

            alert.showAndWait();
        }
    }

    public void alteraEditora() throws SQLException {
        Livraria l = tableLivraria.getSelectionModel().getSelectedItem();
        if (l != null) {
            Conector.alterarEditora(l);
            removerDataEditora();

            try {
                Connection con = Conector.getConnection();
                System.out.println("sus");
                String str = String.format("select * from editora where telefone = '%s'", l.getTelefone());
                System.out.println(str);
                ResultSet rs = con.createStatement().executeQuery(str);
//
                while (rs.next()) {
                    oblist.add(new Livraria(rs.getInt("id"), rs.getString("nome"), rs.getString("endereco")
                            , rs.getString("telefone"), rs.getString("gerente")));
                }

            } catch (SQLException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            tableId.setCellValueFactory(new PropertyValueFactory<>("id"));
            tableNomes.setCellValueFactory(new PropertyValueFactory<>("Nome"));
            tableEndereco.setCellValueFactory(new PropertyValueFactory<>("Endereco"));
            tableTelefones.setCellValueFactory(new PropertyValueFactory<>("Telefone"));
            tableGerente.setCellValueFactory(new PropertyValueFactory<>("Gerente"));

            tableLivraria.setItems(oblist);
            System.out.println(oblist);

            System.out.println("Cliente alterado: " + l.getNome());

        }
    }
}
