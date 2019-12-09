package webservice;

import model.Co2;
import model.Humidity;
import model.Pir;
import model.Temperature;

import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface ISmartCellarService {



     public Response getSensorData(String sensorType, String startDate, String endDate) throws ParseException;
     public Response getLastReading(String sensorType);



}
