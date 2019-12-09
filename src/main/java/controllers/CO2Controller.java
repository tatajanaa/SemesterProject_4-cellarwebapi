package controllers;

import database.*;
import model.Co2;
import model.Humidity;
import model.Temperature;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class CO2Controller {

    public CO2Controller() {
    }

    public List<Co2> getCo2List(Date startDate, Date endDate)  {
        IdatabaseAdapter adapter = new CO2Repo(DataWarehouseConnection.getConnection());

        try {
            return adapter.getReadings(startDate, endDate);
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return null;
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
}
