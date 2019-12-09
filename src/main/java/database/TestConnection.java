package database;

import controllers.CO2Controller;
import controllers.HumidityController;
import controllers.TempController;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

