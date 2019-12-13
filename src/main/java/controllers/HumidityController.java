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

    public List<Humidity> getHumidity(Date startDate, Date endDate) {
        adapter = new HumidityRepo(DataWarehouseConnection.getConnection());
        List<Humidity> targetList = null;
        List<Humidity> sourceList = null;
        try {
            sourceList = new ArrayList<Humidity>(adapter.getReadings(startDate, endDate));
            targetList = new ArrayList<>();

            targetList.add(sourceList.get(0));

            for (int i = 1; i < sourceList.size(); i++) {
                if (sourceList.get(i).getReading() != sourceList.get(i - 1).getReading()) {
                    targetList.add(sourceList.get(i));
                }
            }


        } catch (ParseException | SQLException e) {
            e.printStackTrace();

        }
        return targetList;
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
