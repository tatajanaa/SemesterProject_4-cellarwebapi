package webservice;

import model.Threshold;

import javax.ws.rs.core.Response;
import java.text.ParseException;

public interface ISmartCellarService {


    public Response getAverage(String sensorType, String startDate, String endDate);

    public Response getAveragePerEachHour(String sensorType, String date);

    public Response getLastReading(String sensorType);

    public Response getThresholds();

    public Response getOutOfBoundsLastReading();

    public Response getMinAndMaxPerDay(String sensorType, String startDate, String endDate);

    public Response setThresholdsTemp(Threshold threshold);
    public Response setThresholdsHum(Threshold threshold);
    public Response setThresholdsCO2(Threshold threshold);


}
