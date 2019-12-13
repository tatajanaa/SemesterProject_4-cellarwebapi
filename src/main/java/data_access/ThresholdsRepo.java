package data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ThresholdsRepo {

    private Connection connection;
    PreparedStatement stmt =null;

    public ThresholdsRepo(Connection connection) {
        this.connection = connection;
    }

    public void setCO2Thresholds(double minValue, double maxValue) {

        try {
            stmt = connection.prepareStatement(
                    " update dbo.Thresholds" +
                            "  set minValue="+minValue+", maxValue="+maxValue +
                            "  where SensorType='CO2';");


            stmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setHumidityThresholds(double minValue, double maxValue) {

        try {
            stmt = connection.prepareStatement(
                    " update dbo.Thresholds" +
                            "  set minValue="+minValue+", maxValue="+maxValue +
                            "  where SensorType='Humidity';");

            stmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setTemperatureThresholds(double minValue, double maxValue) {

        try {
            stmt = connection.prepareStatement(
                    " update dbo.Thresholds" +
                            "  set minValue="+minValue+", maxValue="+maxValue +
                            "  where SensorType='Temperature';");

            stmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}