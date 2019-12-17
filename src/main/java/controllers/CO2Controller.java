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

public class CO2Controller {

    public CO2Controller() {
    }


    public Co2 getLastCo2Reading() {
        IdatabaseAdapter adapter1 = new CO2Repo(SourceDbConnection.getConnection());

        return (Co2) adapter1.getLastReading();

    }

    public List<Co2> getAverageCO2(Date startDate, Date endDate) {
        IdatabaseAdapter adapter = new CO2Repo(DataWarehouseConnection.getConnection());

        return adapter.getAverage(startDate, endDate);

    }

    public List<Co2> getAverageCo2PerHour(Date date) {
        IdatabaseAdapter adapter = new CO2Repo(DataWarehouseConnection.getConnection());

        return adapter.getAveragePerEachHour(date);

    }

    public List<Co2> getMinAndMaxPerDay(Date startDate, Date endDate){
        IdatabaseAdapter adapter = new TemperatureRepo(DataWarehouseConnection.getConnection());

        return adapter.getMinAndMaxPerDay(startDate, endDate);


    }
}
