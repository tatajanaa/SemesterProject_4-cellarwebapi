package webservice;

import controllers.CO2Controller;
import controllers.HumidityController;
import controllers.TempController;
import controllers.ThresholdController;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Path("/api")
@Produces("application/json")
public class SmartCellarService implements ISmartCellarService {

    private final SimpleDateFormat SDF;
    TempController tempController;
    HumidityController humidityController;
    CO2Controller co2Controller;
    ThresholdController thresholdController;


    public SmartCellarService() {

        this.SDF = new SimpleDateFormat("dd-MM-yyyy");
        tempController = new TempController();
      humidityController = new HumidityController();
       co2Controller = new CO2Controller();
       thresholdController = new ThresholdController();
    }



    @Override
    @GET
    @Path("/last/{sensortype}")
     public Response getLastReading(@PathParam("sensortype") String sensortype) {
        TempController controller = new TempController();
        JSONObject json = new JSONObject();

        try {
            json.put("sensortype ", sensortype + " not found");

            switch (sensortype.toLowerCase()) {
                case "temperature":
                    return Response.status(Response.Status.OK).entity(tempController.getLastTemperatureReading()).build();

                case "co2":
                    return Response.status(Response.Status.OK).entity(co2Controller.getLastCo2Reading()).build();

                case "humidity":
                    return Response.status(Response.Status.OK).entity(humidityController.getLastHumidityReading()).build();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();
    }

    @Override
    @GET
    @Path("/threshold/co2/{minValue}/{maxValue}")
    public Response setCO2Thresholds(@PathParam("minValue") double minValue,
                                     @PathParam("maxValue") double maxValue) {

        thresholdController.setCO2Thresholds(minValue, maxValue);
        return Response.status(200).build();
    }

    @Override
    @GET
    @Path("/threshold/temp/{minValue}/{maxValue}")
    public Response setTemperatureThresholds (@PathParam("minValue") double minValue,
                                              @PathParam("maxValue") double maxValue) {
        thresholdController.setTemperatureThresholds(minValue, maxValue);
        return Response.status(200).build();
    }

    @Override
    @GET
    @Path("/threshold/humid/{minValue}/{maxValue}")
    public Response setHumidityThresholds (@PathParam("minValue") double minValue,
                                           @PathParam("maxValue") double maxValue) {
        thresholdController.setHumidityThresholds(minValue, maxValue);
        return Response.status(200).build();
    }


    @Override
    @GET
    @Path("/average/{sensortype}/{startDate}/{endDate}")
    public Response getAverage(@PathParam("sensortype") String sensortype,
                                  @PathParam("startDate") String startDate,
                                  @PathParam("endDate")  String endDate) {

        java.sql.Date startDate1 = null;
        try {
            startDate1 = new java.sql.Date(SDF.parse(startDate).getTime());
            java.sql.Date endDate1 = new java.sql.Date(SDF.parse(endDate).getTime());


            switch (sensortype.toLowerCase()) {
                case "temperature":
                    return Response.status(Response.Status.OK).entity(tempController.getAverageTemperature(startDate1, endDate1)).build();

                case "co2":
                    return Response.status(Response.Status.OK).entity(co2Controller.getAverageCO2(startDate1, endDate1)).build();
                case "humidity":
                    return Response.status(Response.Status.OK).entity(humidityController.getAverageHumidity(startDate1, endDate1)).build();
            }

        }
        catch (ParseException e) {
            e.printStackTrace();

            return Response.status(500).entity(e).build();
        }
        return null;
    }

    @Override
    @GET
    @Path("/avgHour/{sensortype}/{date}")
    public Response getAveragePerEachHour(@PathParam("sensortype") String sensortype,
                                          @PathParam("date") String date){

        java.sql.Date date1 = null;
        try {
            date1 = new java.sql.Date(SDF.parse(date).getTime());

            switch (sensortype.toLowerCase()) {
                case "temperature":
                    return Response.status(Response.Status.OK).entity(tempController.getAverageTemperaturePerHour(date1)).build();

                case "co2":
                    return Response.status(Response.Status.OK).entity(co2Controller.getAverageCo2PerHour(date1)).build();
                case "humidity":
                    return Response.status(Response.Status.OK).entity(humidityController.getAverageHumidityPerHour(date1)).build();
            }

        }
        catch (ParseException e) {
            e.printStackTrace();

            return Response.status(500).entity(e).build();
        }
        return null;


    }





}
