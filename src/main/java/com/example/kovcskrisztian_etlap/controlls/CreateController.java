package com.example.kovcskrisztian_etlap.controlls;

import com.example.kovcskrisztian_etlap.Controller;
import com.example.kovcskrisztian_etlap.EtlapDb;
import com.example.kovcskrisztian_etlap.Kategoria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreateController extends Controller {
    @FXML
    private ComboBox<String> selectKategoria;
    @FXML
    private TextArea inputLeiras;
    @FXML
    private Spinner<Integer> inputAr;
    @FXML
    private TextField inputNev;
    private List<Kategoria> kategoriaList;

    private EtlapDb db;


    public void initialize(){
        kategoriaList = new ArrayList<>();
        try {
            db = new EtlapDb();
        } catch (SQLException e) {
            hibaKiir(e);
        }
        try {
            kategoriaList = db.getKategoria();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Kategoria kategoria : kategoriaList){
            selectKategoria.getItems().add(kategoria.getNev());
        }
        selectKategoria.getSelectionModel().selectFirst();
    }
    @FXML
    public void onUjFelvetele(ActionEvent actionEvent) {
        String nev = inputNev.getText().trim();
        String leiras = inputLeiras.getText().trim();
        int ar = 0;
        int kategoriaIndex = selectKategoria.getSelectionModel().getSelectedIndex();
        if (nev.isEmpty()){
            alert("Név megadása kötelező");
            return;
        }
        if (leiras.isEmpty()){
            alert("Leírás megadása kötelező");
            return;
        }
        try {
            ar = (int) inputAr.getValue();
        } catch (NullPointerException ex){
            alert("Az ár megadása kötelező");
            return;
        } catch (Exception ex){
            System.out.println(ex);
            alert("Az ár csak 1 és 1000000 közötti szám lehet");
            return;
        }
        if (ar < 1 || ar > 1000000) {
            alert("Az ár csak 1 és 1000000 közötti szám lehet");
            return;
        }
        if (kategoriaIndex == -1){
            alert("Kategória kiválasztása köztelező");
            return;
        }

        String kategoriaString = selectKategoria.getSelectionModel().getSelectedItem();
        int kategoriaInt = -1;
        try {
            EtlapDb db = new EtlapDb();
            for (Kategoria kategoria : kategoriaList){
                if(kategoria.getNev().equals(kategoriaString)){
                    kategoriaInt = kategoria.getId();
                    break;
                }
            }
            int siker = db.etlaphozzaAdasa(nev,leiras, ar, kategoriaInt);
            if (siker == 1){
                alert("Az étel hozzáadása sikeres");
            } else {
                alert("Az étel hozzáadása sikeretelen");
            }
        } catch (Exception e) {
            hibaKiir(e);
        }
    }


}