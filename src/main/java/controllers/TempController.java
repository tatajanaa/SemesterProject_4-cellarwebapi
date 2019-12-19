package controllers;

import data_access.IdatabaseAdapter;
import data_access.JDBC_connection.DataWarehouseConnection;
import data_access.JDBC_connection.SourceDbConnection;
import data_access.TemperatureRepo;
import model.Temperature;

import java.sql.Date;
import java.util.List;

public class TempController {


    public TempController() {

    }


    public List<Temperature> getAverageTemperature(Date startDate, Date endDate) {
        IdatabaseAdapter adapter = new TemperatureRepo(DataWarehouseConnection.getConnection());

        return adapter.getAverage(startDate, endDate);

    }

    public Temperature getLastTemperatureReading() {
        IdatabaseAdapter adapter1 = new TemperatureRepo(SourceDbConnection.getConnection());

        return (Temperature) adapter1.getLastReading();

    }

    public List<Temperature> getAverageTemperaturePerHour(Date date) {
        IdatabaseAdapter adapter = new TemperatureRepo(DataWarehouseConnection.getConnection());

        return adapter.getAveragePerEachHour(date);

    }

    public List<Temperature> getMinAndMaxPerDay(Date startDate, Date endDate) {
        IdatabaseAdapter adapter = new TemperatureRepo(DataWarehouseConnection.getConnection());

        return adapter.getMinAndMaxPerDay(startDate, endDate);


    }


}
