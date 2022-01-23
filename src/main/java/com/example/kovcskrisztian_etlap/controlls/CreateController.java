package com.example.kovcskrisztian_etlap.controlls;

import com.example.kovcskrisztian_etlap.Controller;
import com.example.kovcskrisztian_etlap.EtlapDb;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CreateController extends Controller {
    @FXML
    private ChoiceBox<String> inputKategoria;
    @FXML
    private TextArea inputLeiras;
    @FXML
    private Spinner<Integer> inputAr;
    @FXML
    private TextField inputNev;

    @FXML
    public void onUjFelveteleClick(ActionEvent actionEvent) {
        String nev = inputNev.getText().trim();
        String leiras = inputLeiras.getText().trim();
        int ar = 0;
        int kategoriaIndex = inputKategoria.getSelectionModel().getSelectedIndex();
        if (nev.isEmpty()){
            alert("Cím megadása kötelező");
            return;
        }
        if (leiras.isEmpty()){
            alert("Leírás megadása kötelező");
            return;
        }
        try {
            ar = inputAr.getValue();
        } catch (NullPointerException ex){
            alert("A hossz megadása kötelező");
            return;
        } catch (Exception ex){
            System.out.println(ex);
            alert("A hossz csak 1 és 100000 közötti szám lehet");
            return;
        }
        if (ar < 1 || ar > 100000) {
            alert("A hossz csak 1 és 100000 közötti szám lehet");
            return;
        }
        if (kategoriaIndex == -1){
            alert("Kategória kiválasztása köztelező");
            return;
        }
        System.out.println(ar);
        String kategoria = inputKategoria.getValue();

        try {
            EtlapDb db=new EtlapDb();
            int siker=db.etlaphozzaAdasa(nev,leiras,kategoria,ar);
            if (siker==1) {
                alert("Étlap hozzáadása sikeres!");
            }else {
                alert("Étlap hozzáadása sikertelen!");
            }
        }catch (Exception e){
            hibaKiir(e);
        }
    }
}
