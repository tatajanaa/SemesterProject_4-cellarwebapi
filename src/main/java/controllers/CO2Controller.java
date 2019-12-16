package controllers;

import data_access.*;
import data_access.JDBC_connection.DataWarehouseConnection;
import data_access.JDBC_connection.SourceDbConnection;
import model.Co2;
import model.Humidity;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CO2Controller {

    public CO2Controller() {
    }


    public Co2 getLastCo2Reading(){
        IdatabaseAdapter adapter1 = new CO2Repo(SourceDbConnection.getConnection());
        try {
            return (Co2) adapter1.getLastReading();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public List<Co2> getAverageCO2(Date startDate, Date endDate) throws ParseException {
        IdatabaseAdapter adapter = new CO2Repo(DataWarehouseConnection.getConnection());

        try {
            return adapter.getAverage(startDate, endDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public List<Co2> getAverageCo2PerHour(Date date) throws ParseException {
        IdatabaseAdapter adapter = new CO2Repo(DataWarehouseConnection.getConnection());

        try {
            return adapter.getAveragePerEachHour(date);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
