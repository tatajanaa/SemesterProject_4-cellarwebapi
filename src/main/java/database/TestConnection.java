package database;

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






    }
}

