package controllers;

import database.DataWarehouseConnection;
import database.HumidityRepo;
import database.IdatabaseAdapter;
import database.TemperatureRepo;
import model.Humidity;
import model.Temperature;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class HumidityController {
    IdatabaseAdapter adapter;

    public HumidityController() {

    }

    public List<Humidity> getHumidity(Date startDate, Date endDate) {
        adapter = new HumidityRepo(DataWarehouseConnection.getConnection());

        try {
            return adapter.getReadings(startDate, endDate);
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
