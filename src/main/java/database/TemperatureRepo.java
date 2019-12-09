package database;

import model.Temperature;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;


import java.util.List;

public class TemperatureRepo implements IdatabaseAdapter<Temperature> {

    List<Temperature> temperatureList =null;
    private Connection connection;
    Statement statement = null;

    public TemperatureRepo(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Temperature> getReadings(Date startDate, Date endDate) throws ParseException, SQLException {
        temperatureList = new ArrayList<>();
        statement = connection.createStatement();

           ResultSet resultSet = statement.executeQuery("use SmartCellarWarehouse_SEP4A19G2 " +
                    "SELECT" +
                    " Dim_Date.MeasuringDate," +
                    " Dim_Time.MeasuringTime," +
                    " mesurement" +
                    " FROM Fact_Temperature" +
                    " JOIN Dim_Date ON Dim_Date.Date_ID = Fact_Temperature.Date_ID" +
                    " JOIN Dim_Time ON Dim_Time.Time_ID = Fact_Temperature.Time_ID" +
                    " Where MeasuringDate<='" + endDate + "' and MeasuringDate>='"
                    + startDate + "'" +
                    " order by MeasuringDate, MeasuringTime; ");

            while (resultSet.next()) {
                Temperature temp = new Temperature();
                temp.setDate(resultSet.getDate("MeasuringDate"));
                temp.setTime(resultSet.getTime("MeasuringTime"));
                temp.setReading(resultSet.getDouble("mesurement"));
                temperatureList.add(temp);

            }
        return temperatureList;


    }



    @Override
    public Temperature getLastReading() throws SQLException {
        statement = connection.createStatement();
        Temperature temp = new Temperature();
        ResultSet resultSet = statement.executeQuery("use sourceDB_SEP4A19G2 " +
                "DECLARE @date date"+
                " DECLARE @time time(0)"+
                " DECLARE @value  decimal(18,2)"+
                " DECLARE MYTESTCURSOR CURSOR"+
                " DYNAMIC"      +
                " FOR"+
                " SELECT date_time, date_time, value  FROM sourceTable where sensorType ='Temperature'"+
                " OPEN MYTESTCURSOR"+
                " FETCH LAST FROM MYTESTCURSOR INTO @date, @time, @value "+
                " CLOSE MYTESTCURSOR"+
                " DEALLOCATE MYTESTCURSOR"+
                " SELECT @date, @time, @value ");

        while (resultSet.next()) {
            temp.setDate(resultSet.getDate(1));
            temp.setTime(resultSet.getTime(2));
           temp.setReading(resultSet.getDouble(3));
        }
        System.out.println(temp);
        return temp;
    }
}



