package data_access;

import model.Humidity;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class HumidityRepo implements IdatabaseAdapter<Humidity> {

    List<Humidity> humidityList =null;
    private Connection connection;
    Statement statement = null;

    public HumidityRepo(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Humidity> getReadings(Date startDate, Date endDate) throws ParseException, SQLException {
        humidityList = new ArrayList<>();
        statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("use SmartCellarWarehouse_SEP4A19G2 " +
                "SELECT" +
                " Dim_Date.MeasuringDate," +
                " Dim_Time.MeasuringTime," +
                " mesurement" +
                " FROM Fact_Humidity" +
                " JOIN Dim_Date ON Dim_Date.Date_ID = Fact_Humidity.Date_ID" +
                " JOIN Dim_Time ON Dim_Time.Time_ID = Fact_Humidity.Time_ID" +
                " Where MeasuringDate<='" + endDate + "' and MeasuringDate>='"
                + startDate + "'" +
                " order by MeasuringDate, MeasuringTime; ");

        while (resultSet.next()) {
            Humidity humidity = new Humidity();
            humidity.setDate(resultSet.getDate("MeasuringDate"));
            humidity.setTime(resultSet.getTime("MeasuringTime"));
            humidity.setReading(resultSet.getDouble("mesurement"));
            humidityList.add(humidity);

        }
        return humidityList;
    }

    @Override
    public Humidity getLastReading() throws SQLException {
        statement = connection.createStatement();
        Humidity humidity = new Humidity();
        ResultSet resultSet = statement.executeQuery("use sourceDB_SEP4A19G2 " +
                "DECLARE @date date"+
                " DECLARE @time time(0)"+
                " DECLARE @value  decimal(18,2)"+
                " DECLARE MYTESTCURSOR CURSOR"+
                " DYNAMIC"      +
                " FOR"+
                " SELECT date_time, date_time, value  FROM sourceTable where sensorType ='Humidity'"+
                " OPEN MYTESTCURSOR"+
                " FETCH LAST FROM MYTESTCURSOR INTO @date, @time, @value "+
                " CLOSE MYTESTCURSOR"+
                " DEALLOCATE MYTESTCURSOR"+
                " SELECT @date, @time, @value ");

        while (resultSet.next()) {
            humidity.setDate(resultSet.getDate(1));
            humidity.setTime(resultSet.getTime(2));
            humidity.setReading(resultSet.getDouble(3));
        }
        System.out.println(humidity);
        return humidity;
    }
}
