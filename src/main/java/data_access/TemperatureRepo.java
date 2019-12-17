package data_access;

import model.Co2;
import model.Temperature;
import model.Threshold;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;


import java.util.List;

public class TemperatureRepo implements IdatabaseAdapter<Temperature> {

    List<Temperature> temperatureList = null;
    Temperature temp = null;
    private Connection connection;
    Statement statement = null;
    PreparedStatement stmt = null;

    public TemperatureRepo(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<Temperature> getAverage(Date startDate, Date endDate) {
        temperatureList = new ArrayList<>();
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("use SmartCellarWarehouse_SEP4A19G2 " +
                    "SELECT" +
                    " distinct Dim_Date.MeasuringDate," +
                    " avg(mesurement) as Average" +
                    " FROM Fact_Temperature" +
                    " JOIN Dim_Date ON Dim_Date.Date_ID = Fact_Temperature.Date_ID" +
                    " Where MeasuringDate<='" + endDate + "' and MeasuringDate>='"
                    + startDate + "' and Location_ID=1" +
                    " group by MeasuringDate order by MeasuringDate; ");


            while (resultSet.next()) {
                Temperature temp = new Temperature();
                temp.setDate(resultSet.getDate("MeasuringDate"));
                temp.setReading(resultSet.getDouble("Average"));
                temperatureList.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return temperatureList;
    }

    @Override
    public Temperature getLastReading() {
        temp = new Temperature();
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("use sourceDB_SEP4A19G2 " +
                    " SELECT top (1) date_time, " +
                    " value  FROM sourceTable where sensorType ='Temperature' and sensorLocation='cell1' " +
                    " order by date_time desc;");

            while (resultSet.next()) {
                temp.setDate(resultSet.getDate(1));
                temp.setTime(resultSet.getTime(1));
                temp.setReading(resultSet.getDouble(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return temp;
    }

    @Override
    public List<Temperature> getAveragePerEachHour(Date date) {
        temperatureList = new ArrayList<>();
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("use SmartCellarWarehouse_SEP4A19G2 " +
                    "SELECT" +
                    " distinct Dim_Time.Hour as Hour," +
                    " avg(mesurement) as Average" +
                    " FROM Fact_Temperature" +
                    " JOIN Dim_Date ON Dim_Date.Date_ID = Fact_Temperature.Date_ID" +
                    " JOIN Dim_Time ON Dim_Time.Time_ID = Fact_Temperature.Time_ID" +
                    " Where MeasuringDate<='" + date + "' " +
                    " group by Hour, mesurement order by  Hour, Average; ");

            while (resultSet.next()) {
                Temperature temperature = new Temperature();
                temperature.setHour(resultSet.getInt("Hour"));
                temperature.setReading(resultSet.getDouble("Average"));
                temperatureList.add(temperature);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return temperatureList;
    }

    @Override
    public List<Temperature> getMinAndMaxPerDay(Date startDate, Date endDate) {
     temperatureList = new ArrayList<>();
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("use SmartCellarWarehouse_SEP4A19G2 " +
                    "SELECT" +
                    " distinct Dim_Date.MeasuringDate," +
                    " min(mesurement)," +
                    " max(mesurement)" +
                    " FROM Fact_Temperature" +
                    " JOIN Dim_Date ON Dim_Date.Date_ID = Fact_Temperature.Date_ID" +
                    " Where MeasuringDate<='" + endDate + "' and MeasuringDate>='"
                    + startDate + "' and Location_ID=1" +
                    " group by MeasuringDate order by  MeasuringDate; ");

            while (resultSet.next()) {
                Temperature temp = new Temperature();
                temp.setDate(resultSet.getDate(1));
                temp.setReading(resultSet.getDouble(2));
                temperatureList.add(temp);
                Temperature temp1 = new Temperature();
                temp1.setDate(resultSet.getDate(1));
                temp1.setReading(resultSet.getDouble(3));
                temperatureList.add(temp1);



            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(temperatureList);
        return temperatureList;

    }





}












