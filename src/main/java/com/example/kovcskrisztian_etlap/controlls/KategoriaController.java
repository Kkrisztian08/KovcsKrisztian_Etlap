package com.example.kovcskrisztian_etlap.controlls;

import com.example.kovcskrisztian_etlap.Controller;
import com.example.kovcskrisztian_etlap.EtlapDb;
import com.example.kovcskrisztian_etlap.Kategoria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

public class KategoriaController extends Controller {
    @FXML
    private TextField inputKategoriaNev;
    private EtlapDb db;
    private List<Kategoria> kategoriaList;

    public void onKategoriaHozzaadasa(ActionEvent actionEvent) {
        try {
            db = new EtlapDb();
        } catch (SQLException e) {
            hibaKiir(e);
            return;
        }
        String kategoriaNev = inputKategoriaNev.getText();
        try {
            kategoriaList = db.getKategoria();
        } catch (SQLException e) {
            hibaKiir(e);
        }
        boolean tartalmazza = false;
        for (Kategoria kategoria : kategoriaList)
        {
            if(kategoria.getNev().toLowerCase().equals(kategoriaNev.toLowerCase())){
                tartalmazza = true;
                break;
            }
        }
        if(tartalmazza){
            alert("A kategória már szerepel!");
            return;
        }
        try {

            int siker = db.kategoriaHozzaAdasa(kategoriaNev);
            if (siker == 1){
                alert("A kategoria hozzáadása sikeres");
            } else {
                alert("Az kategoria hozzáadása sikeretelen");
            }
        } catch (SQLException e) {
            hibaKiir(e);
        }

    }


}
