package controllers;

import data_access.IThresholdAdapter;
import data_access.JDBC_connection.SourceDbConnection;
import data_access.ThresholdsRepo;
import model.Threshold;

import java.util.List;

public class ThresholdController {

    IThresholdAdapter adapter;

    public ThresholdController() {

        adapter = new ThresholdsRepo(SourceDbConnection.getConnection());
    }

    public void setCO2Thresholds(double minValue, double maxValue) {

        adapter.setCO2Thresholds(minValue, maxValue);

    }

    public void setHumidityThresholds(double minValue, double maxValue) {

        adapter.setHumidityThresholds(minValue, maxValue);

    }

    public void setTemperatureThresholds(double minValue, double maxValue) {

        adapter.setTemperatureThresholds(minValue, maxValue);

    }

    public List<Threshold> getThresholds() {

        return adapter.getThresholds();

    }

    public List<Threshold> getOutOfBoundsLastReading() {
        return adapter.getOutOfBoundsLastReading();

    }

    public void updateOutOfBoundsTable() {
        adapter.updateOutOfBoundsTable();
    }


}
