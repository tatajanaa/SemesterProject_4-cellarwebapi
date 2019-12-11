package data_access;

import controllers.CO2Controller;
import controllers.HumidityController;
import controllers.TempController;
import data_access.JDBC_connection.SourceDbConnection;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TestConnection {

    public static void main(String[] args) throws ParseException, SQLException {




    TempController c = new TempController();
        SimpleDateFormat SDF= new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date startDate1 = new java.sql.Date(SDF.parse("01-12-2019").getTime());
        java.sql.Date endDate1 = new java.sql.Date(SDF.parse("01-12-2019").getTime());

        System.out.println(c.getTempertaures(startDate1, endDate1));

/*        CO2Controller c1 = new CO2Controller();
        c1.getLastCo2Reading();

        HumidityController hc = new HumidityController();
        hc.getLastHumidityReading();*/






    }
}

