package com.example.kovcskrisztian_etlap.controlls;

import com.example.kovcskrisztian_etlap.Controller;
import com.example.kovcskrisztian_etlap.Etlap;
import com.example.kovcskrisztian_etlap.EtlapDb;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.events.MouseEvent;

import java.sql.SQLException;
import java.util.List;

public class EtlapController extends Controller {

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
        int selectedIndex=etlapTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert("A törléshez elöbb válasszon ki egy elemet");
            return;
        }else {
            Etlap torlendoEtlap=etlapTableView.getSelectionModel().getSelectedItem();
            if (!confirm("Biztos hogy szeretné törölni az alábbi filmet: "+torlendoEtlap.getNev())) {
                return;
            }
            try {
                db.etlapTorlese(torlendoEtlap.getId());
                alert("Sikeres törés!");
                etlapListaFeltolt();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onEmelesSzazalekClick(ActionEvent actionEvent) {

    }

    @FXML
    public void onEmelesForintClick(ActionEvent actionEvent) {

    }

    @FXML
    public void onUjFelveteleClick(ActionEvent actionEvent) {
        try {
            Controller hozzadas=ujAblak("create-view.fxml","EtlapDb",320,300);
            hozzadas.getStage().setOnCloseRequest(event->etlapListaFeltolt());
            hozzadas.getStage().show();
        } catch (Exception e) {
            hibaKiir(e);
        }
    }


    public void kategoriakiClick(javafx.scene.input.MouseEvent mouseEvent) {
        Etlap kiirandoKategoria=etlapTableView.getSelectionModel().getSelectedItem();
        elemLeirasaTextArea.setText(kiirandoKategoria.getLeairas());
    }

    private void etlapListaFeltolt(){
        try {
            List<Etlap> filmList = db.getEtlap();
            etlapTableView.getItems().clear();
            for(Etlap film: filmList){
                etlapTableView.getItems().add(film);
            }
        } catch (SQLException e) {
            hibaKiir(e);
        }

    }
}