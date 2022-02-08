package com.example.kovcskrisztian_etlap.controlls;

import com.example.kovcskrisztian_etlap.Controller;
import com.example.kovcskrisztian_etlap.Etlap;
import com.example.kovcskrisztian_etlap.EtlapDb;
import com.example.kovcskrisztian_etlap.Kategoria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class EtlapController extends Controller {

    @FXML
    private TableView<Etlap> etlapTable;
    @FXML
    private TableColumn<Etlap, String> kategoriaCol;
    @FXML
    private TableColumn<Etlap, Integer> arCol;
    @FXML
    private TableColumn<Etlap, String> nevCol;
    @FXML
    private TextArea leirasKiTextArea;
    @FXML
    private Spinner<Integer> szazalekinput;
    @FXML
    private Spinner<Integer> forintInput;
    @FXML
    private TableView<Kategoria> kategoriaTable;
    @FXML
    private TableColumn<Kategoria,String> kategoriaNevCol;
    @FXML
    private ChoiceBox<String> etlapSzurese;

    private EtlapDb db;

    public void initialize() {
        nevCol.setCellValueFactory(new PropertyValueFactory<>("nev"));
        kategoriaCol.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        arCol.setCellValueFactory(new PropertyValueFactory<>("ar"));
        kategoriaNevCol.setCellValueFactory(new PropertyValueFactory<>("nev"));
        try {
            db = new EtlapDb();
            etlapListaFeltolt();
            kategoriakFeltoltese();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        etlapSzurese.getSelectionModel().selectFirst();
    }

    //étlappal kapcsolatos metodusok
    private void etlapListaFeltolt(){
        try {
            List<Etlap> etlapLista = db.getEtlap();
            etlapTable.getItems().clear();
            for(Etlap etlap: etlapLista){
                etlapTable.getItems().add(etlap);
            }
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }
    @FXML
    public void onTorles(ActionEvent actionEvent) {
        int selectedIndex= etlapTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert("A törléshez elöbb válasszon ki egy elemet");
            return;
        }else {
            Etlap torlendoEtlap= etlapTable.getSelectionModel().getSelectedItem();
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
    public void onUjFelvetele(ActionEvent actionEvent) {
        try {
            Controller hozzadas=ujAblak("create-view.fxml","EtlapDb",320,300);
            hozzadas.getStage().setOnCloseRequest(event->etlapListaFeltolt());
            hozzadas.getStage().show();
        } catch (Exception e) {
            hibaKiir(e);
        }
    }
    @FXML
    public void onSzazalekosEmeles(ActionEvent actionEvent) {
        int emeles = 0;
        try {
            emeles = szazalekinput.getValue();
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

        int selectedIndex = etlapTable.getSelectionModel().getSelectedIndex();
        Etlap emelesEtel = etlapTable.getSelectionModel().getSelectedItem();

        if (selectedIndex == -1) {
            if (!confirm("Biztos szeretné emelni az összes étel árát?")) {
                return;
            }
            try {
                db.osszesEtelSzazalekosEmelese(emeles);
                alert("Sikeres emelés");
                etlapListaFeltolt();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            if (!confirm("Biztos szeretné emelni a(z) " + emelesEtel.getNev() + " árát?")) {
                return;
            }
            try {
                db.etelSzazalekosEmelese(emelesEtel.getId(), emeles);
                alert("Sikeres emelés");
                etlapListaFeltolt();
                leirasKiTextArea.setText("");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void onForintosEmeles(ActionEvent actionEvent) {
        int emeles = 0;
        try {
            emeles = forintInput.getValue();
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

        int selectedIndex = etlapTable.getSelectionModel().getSelectedIndex();
        Etlap emelesEtel = etlapTable.getSelectionModel().getSelectedItem();

        if (selectedIndex == -1) {
            if (!confirm("Biztos szeretné emelni az összes étel árát?")) {
                return;
            }
            try {
                db.osszesEtelForintosEmelese(emeles);
                alert("Sikeres emelés");
                etlapListaFeltolt();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            if (!confirm("Biztos szeretné emelni a(z) " + emelesEtel.getNev() + " árát?")) {
                return;
            }
            try {
                db.etelForintosEmelese(emelesEtel.getId(), emeles);
                alert("Sikeres emelés");
                etlapListaFeltolt();
                leirasKiTextArea.setText("");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void leiraskiClick(javafx.scene.input.MouseEvent mouseEvent) {
        Etlap kiirandoKategoria= etlapTable.getSelectionModel().getSelectedItem();
        leirasKiTextArea.setText(kiirandoKategoria.getLeairas());
    }

    private void szurtEtlapBetoltese(String szures){
        try {
            List<Etlap> etlapList = db.getSzurtEtlap(szures);
            etlapTable.getItems().clear();
            for(Etlap etlap: etlapList){
                etlapTable.getItems().add(etlap);
            }
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }

    public void onSzures(ActionEvent actionEvent) {
        String szures = etlapSzurese.getSelectionModel().getSelectedItem();
        if (szures == ""){
            etlapListaFeltolt();
            return;
        } else {
            szurtEtlapBetoltese(szures);
        }
    }

    //kategóriákkal kapcsolatos metodusok
    private void kategoriakFeltoltese(){
        try {
            List<Kategoria> kategoriaList = db.getKategoria();
            kategoriaTable.getItems().clear();
            etlapSzurese.getItems().clear();
            etlapSzurese.getItems().add("");
            for(Kategoria kategoria: kategoriaList){
                kategoriaTable.getItems().add(kategoria);
                etlapSzurese.getItems().add(kategoria.getNev());
            }
            etlapSzurese.getSelectionModel().selectFirst();
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }

    public void onKategoriaTorlese(ActionEvent actionEvent) {
        int selectedIndex = kategoriaTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1){
            alert("A törléshez előbb válasszon ki egy elemet a táblázatból");
            return;
        }
        Kategoria torlendoKategoria = (Kategoria) kategoriaTable.getSelectionModel().getSelectedItem();
        if (!confirm("Biztos hogy törölni szeretné az alábbi ételt: "+torlendoKategoria.getNev())){
            return;
        }
        try {
            db.kategoriaTorlese(torlendoKategoria.getId());
            alert("Sikeres törlés");
            kategoriakFeltoltese();
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }

    public void onKategoriaHozzaadasa(ActionEvent actionEvent) {
        try {
            Controller hozzaadas = ujAblak("kategoria-view.fxml", "Kategoria hozzáadása", 200, 100);
            hozzaadas.getStage().setOnCloseRequest(event -> kategoriakFeltoltese());
            hozzaadas.getStage().show();
        } catch (Exception e) {
            hibaKiir(e);
        }
    }


}