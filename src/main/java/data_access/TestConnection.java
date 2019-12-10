package data_access;

import controllers.CO2Controller;
import controllers.HumidityController;
import controllers.TempController;
import data_access.JDBC_connection.SourceDbConnection;

import java.sql.SQLException;
import java.text.ParseException;

public class TestConnection {

    public static void main(String[] args) throws ParseException, SQLException {



  SourceDbConnection.getConnection();
    TempController c = new TempController();
 c.getLastTemperatureReading();

        CO2Controller c1 = new CO2Controller();
        c1.getLastCo2Reading();

        HumidityController hc = new HumidityController();
        hc.getLastHumidityReading();






    }
}

