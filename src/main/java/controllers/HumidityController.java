package controllers;

import data_access.HumidityRepo;
import data_access.IdatabaseAdapter;
import data_access.JDBC_connection.DataWarehouseConnection;
import data_access.JDBC_connection.SourceDbConnection;
import data_access.TemperatureRepo;
import model.Humidity;
import model.Temperature;

import java.sql.Date;
import java.util.List;

public class HumidityController {
    IdatabaseAdapter adapter;

    public HumidityController() {

    }


    public Humidity getLastHumidityReading() {
        IdatabaseAdapter adapter1 = new HumidityRepo(SourceDbConnection.getConnection());

        return (Humidity) adapter1.getLastReading();

    }

    public List<Humidity> getAverageHumidity(Date startDate, Date endDate) {
        IdatabaseAdapter adapter = new HumidityRepo(DataWarehouseConnection.getConnection());

        return adapter.getAverage(startDate, endDate);

    }

    public List<Humidity> getAverageHumidityPerHour(Date date) {
        IdatabaseAdapter adapter = new HumidityRepo(DataWarehouseConnection.getConnection());

        return adapter.getAveragePerEachHour(date);

    }

    public List<Humidity> getMinAndMaxPerDay(Date startDate, Date endDate){
        IdatabaseAdapter adapter = new TemperatureRepo(DataWarehouseConnection.getConnection());

        return adapter.getMinAndMaxPerDay(startDate, endDate);


    }
}
