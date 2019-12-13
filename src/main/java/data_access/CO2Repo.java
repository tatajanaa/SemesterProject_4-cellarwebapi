package data_access;

import model.Co2;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CO2Repo implements IdatabaseAdapter<Co2>{


    List<Co2> co2List =null;
    private Connection connection;
    Statement statement = null;

    public CO2Repo(Connection connection) {
        this.connection=connection;
    }


    @Override
    public List<Co2> getReadings(Date startDate, Date endDate) throws ParseException, SQLException {
        co2List = new ArrayList<>();
        statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("use SmartCellarWarehouse_SEP4A19G2 " +
                "SELECT" +
                " Dim_Date.MeasuringDate," +
                " Dim_Time.MeasuringTime," +
                " mesurement" +
                " FROM Fact_CO2" +
                " JOIN Dim_Date ON Dim_Date.Date_ID = Fact_CO2.Date_ID" +
                " JOIN Dim_Time ON Dim_Time.Time_ID = Fact_CO2.Time_ID" +
                " Where MeasuringDate<='" + endDate + "' and MeasuringDate>='"
                + startDate + "'" +
                " order by MeasuringDate, MeasuringTime; ");

        while (resultSet.next()) {
            Co2 co2 = new Co2();
            co2.setDate(resultSet.getDate("MeasuringDate"));
            co2.setTime(resultSet.getTime("MeasuringTime"));
            co2.setReading(resultSet.getDouble("mesurement"));
            co2List.add(co2);

        }
        return co2List;

    }

    @Override
    public Co2 getLastReading() throws SQLException {
        statement = connection.createStatement();
        Co2 co2 = new Co2();
        ResultSet resultSet = statement.executeQuery("use sourceDB_SEP4A19G2 " +
                " SELECT top (1) date_time, " +
                " value  FROM sourceTable where sensorType ='CO2' order by date_time desc;");

        while (resultSet.next()) {
            co2.setDate(resultSet.getDate(1));
            co2.setTime(resultSet.getTime(1));
            co2.setReading(resultSet.getDouble(2));
        }
        System.out.println(co2);
        return co2;
    }


}
