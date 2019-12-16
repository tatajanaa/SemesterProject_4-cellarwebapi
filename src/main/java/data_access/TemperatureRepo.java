package data_access;

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

    public void add(float temp, float humid, int co2) {

        try {
            
            stmt = connection.prepareStatement(
                    "INSERT INTO \"sourceDB_SEP4A19G2\".dbo.sourceTable(date_time, sensorType, sensorId, sensorLocation, value) "
                            + "VALUES (current_timestamp ,'Temperature', 'T_1', 'cell1'," + temp + ");");
            stmt.executeUpdate();
            System.out.println("OK");

            stmt = connection.prepareStatement(
                    "INSERT INTO \"sourceDB_SEP4A19G2\".dbo.sourceTable(date_time, sensorType, sensorId, sensorLocation, value) "
                            + "VALUES (current_timestamp ,'Humidity', 'H_1', 'cell1'," + humid + ");");
            stmt.executeUpdate();

            stmt = connection.prepareStatement(
                    "INSERT INTO \"sourceDB_SEP4A19G2\".dbo.sourceTable(date_time, sensorType, sensorId, sensorLocation, value) "
                            + "VALUES (current_timestamp ,'CO2', 'C_1', 'cell1'," + co2 + ");");


            stmt.executeUpdate();

            connection.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }

    }












