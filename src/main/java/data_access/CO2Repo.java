package data_access;

import model.Co2;
import model.Humidity;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CO2Repo implements IdatabaseAdapter<Co2> {


    List<Co2> co2List = null;
    private Connection connection;
    Statement statement = null;

    public CO2Repo(Connection connection) {
        this.connection = connection;
    }

    /**
     * Returns a list  of average CO2 levels per day in period between two selected dates
     * @param startDate
     * @param endDate
     * @return List
     */
    @Override
    public List<Co2> getAverage(Date startDate, Date endDate) {
        co2List = new ArrayList<>();
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("use SmartCellarWarehouse_SEP4A19G2 " +
                    "SELECT" +
                    " distinct Dim_Date.MeasuringDate," +
                    " avg(mesurement) as Average" +
                    " FROM Fact_CO2" +
                    " JOIN Dim_Date ON Dim_Date.Date_ID = Fact_CO2.Date_ID" +
                    " Where MeasuringDate<='" + endDate + "' and MeasuringDate>='"
                    + startDate + "'" +
                    " group by MeasuringDate order by MeasuringDate; ");


            while (resultSet.next()) {
                Co2 co2 = new Co2();
                co2.setDate(resultSet.getDate("MeasuringDate"));
                co2.setReading(resultSet.getDouble("Average"));
                co2List.add(co2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return co2List;
    }

    @Override
    public Co2 getLastReading() {
        Co2 co2 = new Co2();
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("use sourceDB_SEP4A19G2 " +
                    " SELECT top (1) date_time, " +
                    " value  FROM sourceTable where sensorType ='CO2' " +
                    " and sensorLocation='cell1' order by date_time desc;");

            while (resultSet.next()) {
                co2.setDate(resultSet.getDate(1));
                co2.setTime(resultSet.getTime(1));
                co2.setReading(resultSet.getDouble(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return co2;
    }

    @Override
    public List<Co2> getAveragePerEachHour(Date date) {
        co2List = new ArrayList<>();
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("use SmartCellarWarehouse_SEP4A19G2 " +
                    "SELECT" +
                    " distinct Dim_Time.Hour as Hour," +
                    " avg(mesurement) as Average" +
                    " FROM Fact_CO2" +
                    " JOIN Dim_Date ON Dim_Date.Date_ID = Fact_CO2.Date_ID" +
                    " JOIN Dim_Time ON Dim_Time.Time_ID = Fact_CO2.Time_ID" +
                    " Where MeasuringDate<='" + date + "' " +
                    " group by Hour, mesurement order by  Hour, Average; ");

            while (resultSet.next()) {
                Co2 co2 = new Co2();
                co2.setHour(resultSet.getInt("Hour"));
                co2.setReading(resultSet.getDouble("Average"));
                co2List.add(co2);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return co2List;
    }

    @Override
    public List<Co2> getMinAndMaxPerDay(Date startDate, Date endDate) {
        co2List = new ArrayList<>();
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("use SmartCellarWarehouse_SEP4A19G2 " +
                    "SELECT" +
                    " distinct Dim_Date.MeasuringDate," +
                    " min(mesurement)," +
                    " max(mesurement)" +
                    " FROM Fact_CO2" +
                    " JOIN Dim_Date ON Dim_Date.Date_ID = Fact_CO2.Date_ID" +
                    " Where MeasuringDate<='" + endDate + "' and MeasuringDate>='"
                    + startDate + "' and Location_ID=1" +
                    " group by MeasuringDate order by  MeasuringDate; ");

            while (resultSet.next()) {
                Co2 co2 = new Co2();
                co2.setDate(resultSet.getDate(1));
                co2.setReading(resultSet.getDouble(2));
                co2List.add(co2);
                Co2 co21 = new Co2();
                co21.setDate(resultSet.getDate(1));
                co21.setReading(resultSet.getDouble(3));
                co2List.add(co21);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return co2List;
    }


}
