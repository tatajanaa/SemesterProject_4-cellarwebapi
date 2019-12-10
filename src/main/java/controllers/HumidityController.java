package controllers;

import data_access.*;
import data_access.JDBC_connection.DataWarehouseConnection;
import data_access.JDBC_connection.SourceDbConnection;
import model.Humidity;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
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

    public Humidity getLastHumidityReading(){
        IdatabaseAdapter adapter1 = new HumidityRepo(SourceDbConnection.getConnection());

        try {
            return (Humidity) adapter1.getLastReading();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
