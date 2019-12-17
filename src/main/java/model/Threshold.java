package model;

public class Threshold {

    private String sensorType;
    private double minValue;
    private double maxValue;

    public Threshold() {

    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public String getSensorType() {
        return sensorType;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    @Override
    public String toString() {
        return "Threshold{" +
                "sensorType='" + sensorType + '\'' +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue +
                '}';
    }
}
