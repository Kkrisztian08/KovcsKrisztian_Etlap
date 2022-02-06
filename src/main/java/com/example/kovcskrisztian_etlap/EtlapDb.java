package com.example.kovcskrisztian_etlap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtlapDb {
    Connection conn;

    public EtlapDb() throws SQLException {
        this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/etlapdb", "root", "");
    }

    public List<Etlap> getEtlap() throws SQLException {
        List<Etlap> etlapok = new ArrayList<>();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM etlap";
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()) {
            int id = result.getInt("id");
            String nev = result.getString("nev");
            String leiras = result.getString("leiras");
            int ar = result.getInt("ar");
            String kategoria = result.getString("kategoria");
            Etlap etlap = new Etlap(id, nev, leiras, ar, kategoria);
            etlapok.add(etlap);
        }
        return etlapok;
    }

    public int etlaphozzaAdasa(String nev, String leiras, String kategoria, int ar) throws SQLException {
        String sql="INSERT INTO etlap(nev,leiras,kategoria,ar) VALUES(?,?,?,?)";
        PreparedStatement stmt=conn.prepareStatement(sql);
        stmt.setString(1,nev);
        stmt.setString(2,leiras);
        stmt.setString(3,kategoria);
        stmt.setInt(4,ar);
        return stmt.executeUpdate();
    }

    public boolean etlapTorlese(int id) throws SQLException {
        String sql="DELETE FROM etlap WHERE id=?";
        PreparedStatement stmt=conn.prepareStatement(sql);
        stmt.setInt(1,id);
        int erintettSorok= stmt.executeUpdate();
        return erintettSorok==1;
    }
    public boolean etelEmelesForint(int id, int emeles) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar + ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, emeles);
        stmt.setInt(2, id);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }

    public boolean etelEmelesForintOsszes(int emeles) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar + ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, emeles);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }

    public boolean etelEmelesSzazalek(int id, int szazalek) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar * ((100 + ?) / 100) WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, szazalek);
        stmt.setInt(2, id);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }

    public boolean etelEmelesSzazalekOsszes(int szazalek) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar * ((100 + ?) / 100)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, szazalek);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }
}
