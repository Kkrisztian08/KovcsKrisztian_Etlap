package com.example.kovcskrisztian_etlap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class EtlapController {

    @FXML
    private TableView<Etlap> etlapTableView;
    @FXML
    private TableColumn<Etlap, String> kategoriaCol;
    @FXML
    private TableColumn<Etlap, Integer> arCol;
    @FXML
    private TableColumn<Etlap, String> nevCol;
    @FXML
    private TextArea elemLeirasaTextArea;

    private EtlapDb db;

    public void initialize() {
        nevCol.setCellValueFactory(new PropertyValueFactory<>("nev"));
        kategoriaCol.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        arCol.setCellValueFactory(new PropertyValueFactory<>("ar"));
        try {
            db = new EtlapDb();
            List<Etlap> etlapLista = db.getEtlap();
            for (Etlap etlap : etlapLista) {
                etlapTableView.getItems().add(etlap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onTorlesClick(ActionEvent actionEvent) {

    }

    @FXML
    public void onEmelesSzazalekClick(ActionEvent actionEvent) {
    }

    @FXML
    public void onEmelesForintClick(ActionEvent actionEvent) {
    }

    @FXML
    public void onUjFelveteleClick(ActionEvent actionEvent) {
    }
}