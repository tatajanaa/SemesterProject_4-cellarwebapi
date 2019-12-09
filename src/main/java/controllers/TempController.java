package controllers;

import database.DataWarehouseConnection;
import database.IdatabaseAdapter;
import database.SourceDbConnection;
import database.TemperatureRepo;
import model.Temperature;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.List;

public class TempController {
    IdatabaseAdapter adapter;

    public TempController() {


    }

    public List<Temperature> getTempertaures(Date startDate, Date endDate)  {
        adapter = new TemperatureRepo(DataWarehouseConnection.getConnection());

       try {
            return adapter.getReadings(startDate, endDate);
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Temperature getLastTemperatureReading(){
        adapter = new TemperatureRepo(SourceDbConnection.getConnection());
        try {
            return adapter.getLastReading();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
