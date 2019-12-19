package data_access;

import controllers.TempController;
import data_access.JDBC_connection.DataWarehouseConnection;
import data_access.JDBC_connection.SourceDbConnection;
import webservice.SmartCellarService;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TestConnection {

    public static void main(String[] args) throws ParseException, SQLException {
        final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date startDate1 = new java.sql.Date(SDF.parse("01-12-2019").getTime());
        java.sql.Date endDate1 = new java.sql.Date(SDF.parse("03-12-2019").getTime());

        TempController c = new TempController();
        c.getAverageTemperaturePerHour(startDate1);


        //System.out.println(r.getReadings(startDate1, endDate1));


    }
}

