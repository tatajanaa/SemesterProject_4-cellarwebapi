package data_access;

import model.Threshold;

import java.util.List;

public interface IThresholdAdapter {

    public void setCO2Thresholds(double minValue, double maxValue);
    public void setHumidityThresholds(double minValue, double maxValue);
    public void setTemperatureThresholds(double minValue, double maxValue);
    public List<Threshold> getThresholds();
    public void updateOutOfBoundsTable();
    public List<Threshold> getOutOfBoundsLastReading();
}
