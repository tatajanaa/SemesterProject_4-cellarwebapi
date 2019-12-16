package controllers;

import data_access.*;
import data_access.JDBC_connection.DataWarehouseConnection;
import data_access.JDBC_connection.SourceDbConnection;
import model.Co2;
import model.Humidity;
import model.Temperature;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class HumidityController {
    IdatabaseAdapter adapter;

    public HumidityController() {

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

    public List<Humidity> getAverageHumidity(Date startDate, Date endDate) throws ParseException {
        IdatabaseAdapter adapter = new HumidityRepo(DataWarehouseConnection.getConnection());

        try {
            return adapter.getAverage(startDate, endDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public List<Humidity> getAverageHumidityPerHour(Date date) throws ParseException {
        IdatabaseAdapter adapter = new HumidityRepo(DataWarehouseConnection.getConnection());

        try {
            return adapter.getAveragePerEachHour(date);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
