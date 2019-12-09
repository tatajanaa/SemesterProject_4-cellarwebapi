package database;

import model.Humidity;
import model.Temperature;

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
    public Temperature getLastReading() throws SQLException {
        return null;
    }
}
