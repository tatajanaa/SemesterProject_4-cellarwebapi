package data_access;

import model.Co2;
import model.Temperature;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;


import java.util.List;

public class TemperatureRepo implements IdatabaseAdapter<Temperature>{

    List<Temperature> temperatureList =null;
    private Connection connection;
    Statement statement = null;
    PreparedStatement stmt =null;

    public TemperatureRepo(Connection connection) {
        this.connection = connection;
    }



    @Override
    public List<Temperature> getAverage(Date startDate, Date endDate) throws SQLException {
        temperatureList = new ArrayList<>();
        statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("use SmartCellarWarehouse_SEP4A19G2 " +
                "SELECT" +
                " distinct Dim_Date.MeasuringDate," +
                " avg(mesurement) as Average" +
                " FROM Fact_Temperature" +
                " JOIN Dim_Date ON Dim_Date.Date_ID = Fact_Temperature.Date_ID" +
                 " Where MeasuringDate<='" + endDate + "' and MeasuringDate>='"
                + startDate + "'" +
                " group by MeasuringDate order by MeasuringDate; ");


        while (resultSet.next()) {
            Temperature temp = new Temperature();
            temp.setDate(resultSet.getDate("MeasuringDate"));
            temp.setReading(resultSet.getDouble("Average"));
            temperatureList.add(temp);


        }

        return temperatureList;
    }

    @Override
    public Temperature getLastReading() throws SQLException {
        statement = connection.createStatement();
        Temperature temp = new Temperature();
        ResultSet resultSet = statement.executeQuery("use sourceDB_SEP4A19G2 " +
                " SELECT top (1) date_time, " +
                " value  FROM sourceTable where sensorType ='Temperature' order by date_time desc;");

        while (resultSet.next()) {
            temp.setDate(resultSet.getDate(1));
            temp.setTime(resultSet.getTime(1));
            temp.setReading(resultSet.getDouble(2));
        }

        return temp;
    }

    @Override
    public List<Temperature> getAveragePerEachHour(Date date) throws SQLException {
        temperatureList = new ArrayList<>();
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

        return temperatureList;
    }






    }












