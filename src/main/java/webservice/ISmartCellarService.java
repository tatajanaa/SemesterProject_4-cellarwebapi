package webservice;

import javax.ws.rs.core.Response;
import java.text.ParseException;

public interface ISmartCellarService {


    public Response getAverage(String sensorType, String startDate, String endDate);

    public Response getAveragePerEachHour(String sensorType, String date);

    public Response getLastReading(String sensorType);

    public Response setCO2Thresholds(double minValue, double maxValue);

    public Response setTemperatureThresholds(double minValue, double maxValue);

    public Response setHumidityThresholds(double minValue, double maxValue);

    public Response getThresholds();


}
