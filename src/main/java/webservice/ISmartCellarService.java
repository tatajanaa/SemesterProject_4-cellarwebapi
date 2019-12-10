package webservice;

import javax.ws.rs.core.Response;
import java.text.ParseException;

public interface ISmartCellarService {



     public Response getSensorData(String sensorType, String startDate, String endDate) throws ParseException;
     public Response getLastReading(String sensorType);



}
