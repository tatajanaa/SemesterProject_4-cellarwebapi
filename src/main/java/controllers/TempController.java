package controllers;

import data_access.HumidityRepo;
import data_access.JDBC_connection.DataWarehouseConnection;
import data_access.IdatabaseAdapter;
import data_access.JDBC_connection.SourceDbConnection;
import data_access.TemperatureRepo;
import model.Humidity;
import model.Temperature;
import org.codehaus.jackson.annotate.JsonIgnoreType;

import javax.crypto.spec.PSource;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TempController {


    public TempController() {

    }


    public List<Temperature> getAverageTemperature(Date startDate, Date endDate) throws ParseException {
        IdatabaseAdapter adapter = new TemperatureRepo(DataWarehouseConnection.getConnection());

        try {
            return adapter.getAverage(startDate, endDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
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

    public List<Temperature> getAverageTemperaturePerHour(Date date) throws ParseException {
        IdatabaseAdapter adapter = new TemperatureRepo(DataWarehouseConnection.getConnection());

        try {
            return adapter.getAveragePerEachHour(date);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
