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

    public List<Co2> getCo2List(Date startDate, Date endDate)  {
        IdatabaseAdapter adapter = new CO2Repo(DataWarehouseConnection.getConnection());
        List<Co2> targetList = null;
        List<Co2> sourceList = null;
        try {
            sourceList = new ArrayList<Co2>(adapter.getReadings(startDate, endDate));
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
}
