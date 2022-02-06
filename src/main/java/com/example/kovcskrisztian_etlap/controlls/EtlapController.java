package com.example.kovcskrisztian_etlap.controlls;

import com.example.kovcskrisztian_etlap.Controller;
import com.example.kovcskrisztian_etlap.Etlap;
import com.example.kovcskrisztian_etlap.EtlapDb;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
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
    @FXML
    private Spinner<Integer> inputNovelSzazalek;
    @FXML
    private Spinner<Integer> inputNovelForint;

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
        int emeles = 0;
        try {
            emeles = inputNovelSzazalek.getValue();
        } catch (NullPointerException e) {
            alert("Az emeléshez a százalék megadása kötelező");
            return;
        } catch (Exception e) {
            alert("Az ár 5 és 50 közötti szám lehet");
            return;
        }
        if (emeles < 5 || emeles > 50) {
            alert("Az ár 5 és 50 közötti szám lehet");
            return;
        }

        int selectedIndex = etlapTableView.getSelectionModel().getSelectedIndex();
        Etlap emelesEtel = etlapTableView.getSelectionModel().getSelectedItem();

        if (selectedIndex == -1) {
            if (!confirm("Biztos szeretné emelni az összes étel árát?")) {
                return;
            }
            try {
                db.etelEmelesSzazalekOsszes(emeles);
                alert("Sikeres emelés");
                etlapUjratoltese();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            if (!confirm("Biztos szeretné emelni a(z) " + emelesEtel.getNev() + " árát?")) {
                return;
            }
            try {
                db.etelEmelesSzazalek(emelesEtel.getId(), emeles);
                alert("Sikeres emelés");
                etlapUjratoltese();
                elemLeirasaTextArea.setText("");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onEmelesForintClick(ActionEvent actionEvent) {
        int emeles = 0;
        try {
            emeles = inputNovelForint.getValue();
        } catch (NullPointerException e) {
            alert("Az emeléshez az ár megadása kötelező");
            return;
        } catch (Exception e) {
            alert("Az ár 50 és 3000 közötti szám lehet");
            return;
        }
        if (emeles < 50 || emeles > 3000) {
            alert("Az ár 50 és 3000 közötti szám lehet");
            return;
        }

        int selectedIndex = etlapTableView.getSelectionModel().getSelectedIndex();
        Etlap emelesEtel = etlapTableView.getSelectionModel().getSelectedItem();

        if (selectedIndex == -1) {
            if (!confirm("Biztos szeretné emelni az összes étel árát?")) {
                return;
            }
            try {
                db.etelEmelesForintOsszes(emeles);
                alert("Sikeres emelés");
                etlapUjratoltese();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            if (!confirm("Biztos szeretné emelni a(z) " + emelesEtel.getNev() + " árát?")) {
                return;
            }
            try {
                db.etelEmelesForint(emelesEtel.getId(), emeles);
                alert("Sikeres emelés");
                etlapUjratoltese();
                elemLeirasaTextArea.setText("");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

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

    private void etlapUjratoltese() {
        try {
            List<Etlap> etlapLista = db.getEtlap();
            etlapTableView.getItems().clear();
            for (Etlap etlap : etlapLista) {
                etlapTableView.getItems().add(etlap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}