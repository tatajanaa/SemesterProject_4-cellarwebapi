package controllers;

import data_access.JDBC_connection.DataWarehouseConnection;
import data_access.IdatabaseAdapter;
import data_access.JDBC_connection.SourceDbConnection;
import data_access.TemperatureRepo;
import model.Temperature;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;

import java.util.List;

public class TempController {


    public TempController() {


    }

    public List<Temperature> getTempertaures(Date startDate, Date endDate)  {
        IdatabaseAdapter adapter = new TemperatureRepo(DataWarehouseConnection.getConnection());

       try {
            return adapter.getReadings(startDate, endDate);
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Temperature getLastTemperatureReading(){
        IdatabaseAdapter adapter1 = new TemperatureRepo(SourceDbConnection.getConnection());
        try {
            return (Temperature) adapter1.getLastReading();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
