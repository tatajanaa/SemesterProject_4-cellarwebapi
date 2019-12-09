package webservice;

import com.google.gson.Gson;
import controllers.HumidityController;
import controllers.TempController;
import model.Co2;
import model.Humidity;
import model.Pir;
import model.Temperature;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

@Path("/api")
@Produces("application/json")
public class SmartCellarService implements ISmartCellarService {

    private final SimpleDateFormat SDF;


    public SmartCellarService() {
        this.SDF = new SimpleDateFormat("dd-MM-yyyy");
    }



    @Override
    @GET
    @Path("/last/{sensortype}")
     public Response getLastReading(@PathParam("sensortype") String sensortype) {
        TempController controller = new TempController();

        switch (sensortype.toLowerCase()) {
            case "temperature":
                return Response.status(Response.Status.OK).entity(controller.getLastTemperatureReading()).build();

            case "co2":
                // code block
                break;
            default:
                // code block
        }
        return null;


    }

    @Override
    @GET
    @Path("/sensorData/{sensortype}/{startDate}/{endDate}")
    public Response getSensorData(@PathParam("sensortype") String sensortype,
                                  @PathParam("startDate") String startDate,
                                  @PathParam("endDate")  String endDate) throws ParseException {

        java.sql.Date startDate1 = new java.sql.Date(SDF.parse(startDate).getTime());
        java.sql.Date endDate1 = new java.sql.Date(SDF.parse(endDate).getTime());

        TempController controller = new TempController();
        HumidityController humidityController = new HumidityController();

        switch(sensortype.toLowerCase()) {
            case "temperature":
                return Response.status(Response.Status.OK).entity(controller.getTempertaures(startDate1, endDate1)).build();

            case "co2":
                return Response.status(Response.Status.OK).entity(humidityController.getHumidity(startDate1, endDate1)).build();
            case "humidity":
                // code block
                break;
            default:
                // code block
        }


        return null;
    }


}
