package data_access;

import model.Humidity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HumidityRepo implements IdatabaseAdapter<Humidity> {

    List<Humidity> humidityList = null;
    private Connection connection;
    Statement statement = null;

    public HumidityRepo(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<Humidity> getAverage(Date startDate, Date endDate) {
        humidityList = new ArrayList<>();
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("use SmartCellarWarehouse_SEP4A19G2 " +
                    "SELECT" +
                    " distinct Dim_Date.MeasuringDate," +
                    " avg(mesurement) as Average" +
                    " FROM Fact_Humidity" +
                    " JOIN Dim_Date ON Dim_Date.Date_ID = Fact_Humidity.Date_ID" +
                    " Where MeasuringDate<='" + endDate + "' and MeasuringDate>='"
                    + startDate + "'" +
                    " group by MeasuringDate order by MeasuringDate; ");


            while (resultSet.next()) {
                Humidity humidity = new Humidity();
                humidity.setDate(resultSet.getDate("MeasuringDate"));
                humidity.setReading(resultSet.getDouble("Average"));
                humidityList.add(humidity);

            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        System.out.println(humidityList);
        return humidityList;
    }

    @Override
    public Humidity getLastReading() {
        Humidity humidity = new Humidity();
        try {
            statement = connection.createStatement();


            ResultSet resultSet = statement.executeQuery("use sourceDB_SEP4A19G2 " +
                    " SELECT top (1) date_time, " +
                    " value  FROM sourceTable where sensorType ='Humidity' and sensorLocation='cell1'" +
                    " order by date_time desc;");

            while (resultSet.next()) {
                humidity.setDate(resultSet.getDate(1));
                humidity.setTime(resultSet.getTime(1));
                humidity.setReading(resultSet.getDouble(2));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return humidity;

    }

    @Override
    public List<Humidity> getAveragePerEachHour(Date date) {
        humidityList = new ArrayList<>();
        try {
            statement = connection.createStatement();


            ResultSet resultSet = statement.executeQuery("use SmartCellarWarehouse_SEP4A19G2 " +
                    "SELECT" +
                    " distinct Dim_Time.Hour as Hour," +
                    " avg(mesurement) as Average" +
                    " FROM Fact_Humidity" +
                    " JOIN Dim_Date ON Dim_Date.Date_ID = Fact_Humidity.Date_ID" +
                    " JOIN Dim_Time ON Dim_Time.Time_ID = Fact_Humidity.Time_ID" +
                    " Where MeasuringDate<='" + date + "' " +
                    " group by Hour; ");


            while (resultSet.next()) {
                Humidity humidity = new Humidity();
                humidity.setHour(resultSet.getInt("Hour"));
                humidity.setReading(resultSet.getDouble("Average"));
                humidityList.add(humidity);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return humidityList;
    }

    @Override
    public List<Humidity> getMinAndMaxPerDay(Date startDate, Date endDate) {
        humidityList = new ArrayList<>();
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("use SmartCellarWarehouse_SEP4A19G2 " +
                    "SELECT" +
                    " distinct Dim_Date.MeasuringDate," +
                    " min(mesurement)," +
                    " max(mesurement)" +
                    " FROM Fact_Humidity" +
                    " JOIN Dim_Date ON Dim_Date.Date_ID = Fact_Humidity.Date_ID" +
                    " Where MeasuringDate<='" + endDate + "' and MeasuringDate>='"
                    + startDate + "' and Location_ID=1" +
                    " group by MeasuringDate order by  MeasuringDate; ");

            while (resultSet.next()) {
                Humidity humidity = new Humidity();
                humidity.setDate(resultSet.getDate(1));
                humidity.setReading(resultSet.getDouble(2));
                humidityList.add(humidity);
                Humidity humidity1 = new Humidity();
                humidity1.setDate(resultSet.getDate(1));
                humidity1.setReading(resultSet.getDouble(3));
                humidityList.add(humidity1);

      }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return humidityList;
    }


}
