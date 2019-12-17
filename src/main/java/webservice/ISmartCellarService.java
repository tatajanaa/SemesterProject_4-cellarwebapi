package webservice;

import javax.ws.rs.core.Response;
import java.text.ParseException;

public interface ISmartCellarService {


    public Response getAverage(String sensorType, String startDate, String endDate);

    public Response getAveragePerEachHour(String sensorType, String date);

    public Response getLastReading(String sensorType);

    public Response setThresholds(String sensorType, double minValue, double maxValue);

    public Response getThresholds();


}
