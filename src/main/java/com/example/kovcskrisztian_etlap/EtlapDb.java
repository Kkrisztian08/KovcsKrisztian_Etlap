package com.example.kovcskrisztian_etlap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtlapDb {
    Connection conn;

    public EtlapDb() throws SQLException {
        this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/etlapdb", "root", "");
    }

    //étlappal kapcsolatos metodusok
    public List<Etlap> getEtlap() throws SQLException {
        List<Etlap> etlapLista = new ArrayList<>();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM etlap INNER JOIN kategoria ON kategoria.id = etlap.kategoria_id;";
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()){
            int id = result.getInt("id");
            String nev = result.getString("etlap.nev");
            String leiras = result.getString("leiras");
            String kategoria = result.getString("kategoria.nev");
            int ar = result.getInt("ar");
            Etlap etel = new Etlap(id, nev,leiras,ar ,kategoria );
            etlapLista.add(etel);
        }
        return etlapLista;
    }

    public int etlaphozzaAdasa(String nev, String leiras, int ar, int  kategoria) throws SQLException {
        String sql = "INSERT INTO etlap(nev, leiras, ar, kategoria_id) VALUES (?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1,nev);
        stmt.setString(2,leiras);
        stmt.setInt(3,ar);
        stmt.setInt(4,kategoria);
        return stmt.executeUpdate();
    }

    public boolean etlapTorlese(int id) throws SQLException {
        String sql="DELETE FROM etlap WHERE id=?";
        PreparedStatement stmt=conn.prepareStatement(sql);
        stmt.setInt(1,id);
        int erintettSorok= stmt.executeUpdate();
        return erintettSorok==1;
    }

    public boolean etelForintosEmelese(int id, int emeles) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar + ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, emeles);
        stmt.setInt(2, id);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }

    public boolean osszesEtelForintosEmelese(int emeles) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar + ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, emeles);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }

    public boolean etelSzazalekosEmelese(int id, int szazalek) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar * ((100 + ?) / 100) WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, szazalek);
        stmt.setInt(2, id);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }

    public boolean osszesEtelSzazalekosEmelese(int szazalek) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar * ((100 + ?) / 100)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, szazalek);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }

    public List<Etlap> getSzurtEtlap(String szures) throws SQLException {
        List<Etlap> etlapLista = new ArrayList<>();
        String sql = "SELECT * FROM etlap JOIN kategoria ON kategoria.id = etlap.kategoria_id WHERE kategoria.nev = ?;";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, szures);
        ResultSet result = stmt.executeQuery();
        while (result.next()){
            int id = result.getInt("id");
            String nev = result.getString("etlap.nev");
            String leiras = result.getString("leiras");
            String kategoria = result.getString("kategoria.nev");
            int ar = result.getInt("ar");
            Etlap etel = new Etlap(id, nev,leiras, ar, kategoria);
            etlapLista.add(etel);
        }
        return etlapLista;
    }

    //kategóriákkal kapcsolatos metodusok
    public List<Kategoria> getKategoria() throws SQLException {
        List<Kategoria> kategoriaLista = new ArrayList<>();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM kategoria;";
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()){
            int id = result.getInt("id");
            String nev = result.getString("nev");
            Kategoria kategoria = new Kategoria(id, nev);
            kategoriaLista.add(kategoria);
        }
        return kategoriaLista;
    }

    public boolean kategoriaTorlese(int id) throws SQLException {
        String sql = "DELETE FROM kategoria WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }

    public int kategoriaHozzaAdasa(String nev) throws SQLException {
        String sql = "INSERT INTO kategoria(nev) VALUES (?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1,nev);
        return stmt.executeUpdate();
    }

}