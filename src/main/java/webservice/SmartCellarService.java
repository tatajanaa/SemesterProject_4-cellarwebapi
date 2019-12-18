package webservice;

import controllers.CO2Controller;
import controllers.HumidityController;
import controllers.TempController;
import controllers.ThresholdController;
import model.Threshold;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Path("/api")
@Produces("application/json")
@Consumes("application/json")
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

    /**
     * Returns last reading from database
     *
     * @param sensortype retrieve from client
     * @return one Temperature or CO2 or Humidity object(with attributes date, time, reading)
     */
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

    /**
     * Method saves Temperature or Humidity or CO2 thresholds in database
     *
     * @param sensortype retrieve from client
     * @param minValue   retrieve from client
     * @param maxValue   retrieve from client
     * @return status code 200 indicates that the request has been processed successfully
     */


    @POST
    @Path("/threshold/temp")
    public Response setThresholdsTemp(Threshold threshold) {

                thresholdController.setTemperatureThresholds(threshold.getMinValue(), threshold.getMaxValue());
        return Response.status(200).build();
    }
    @POST
    @Path("/threshold/humid")
    public Response setThresholdsHum(Threshold threshold) {

        thresholdController.setHumidityThresholds(threshold.getMinValue(), threshold.getMaxValue());
        return Response.status(200).build();
    }
    @POST
    @Path("/threshold/co2")
    public Response setThresholdsCO2(Threshold threshold) {

        thresholdController.setCO2Thresholds(threshold.getMinValue(), threshold.getMaxValue());
        return Response.status(200).build();
    }

    /**
     * Methods returns all saved Threshold values for Temperature, CO2 and Humidity from database
     *
     * @return List of Threshold objects(attributes: sensortype, minValue, maxValue)
     */
    @Override
    @GET
    @Path("/getthresholds")
    public Response getThresholds() {
        return Response.status(Response.Status.OK).entity(thresholdController.getThresholds()).build();
    }

    /**
     * Method returns values from OutOfBounds table
     * values are stored in Threshold object in date, time and minValue, where minValue stores
     * measured value out of limits.
     * method calls updateOutOfBoundsTable() which updates table in database and sets notified to True
     *
     * @return List of Threshold objects
     */
    @Override
    @GET
    @Path("/outofbounds")
    public Response getOutOfBoundsLastReading() {
        List<Threshold> t = thresholdController.getOutOfBoundsLastReading();
        thresholdController.updateOutOfBoundsTable();
        return Response.status(Response.Status.OK).entity(t).build();


    }

    /**
     * Method returns average Temperature or CO2 or Humidity for each day in selected period
     * @param sensortype retrieve form client
     * @param startDate retrieve form client
     * @param endDate retrieve form client
     * @return List of Temperature or Humidity or CO2 object (attributes: date, time, average reading)
     */
    @Override
    @GET
    @Path("/average/{sensortype}/{startDate}/{endDate}")
    public Response getAverage(@PathParam("sensortype") String sensortype,
                               @PathParam("startDate") String startDate,
                               @PathParam("endDate") String endDate) {

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

        } catch (ParseException e) {
            e.printStackTrace();

            return Response.status(500).entity(e).build();
        }
        return null;
    }

    /**
     * Method returns hourly average readings of Temperature or co2 or Humidity fo ONE chosen day
     *
     * @param sensortype retrieve form client
     * @param date retrieve form client
     * @return list of Temperature or CO2 or Humidity objects with attributes hour:int and average reading per hour
     */
    @Override
    @GET
    @Path("/avghour/{sensortype}/{date}")
    public Response getAveragePerEachHour(@PathParam("sensortype") String sensortype,
                                          @PathParam("date") String date) {

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

        } catch (ParseException e) {
            e.printStackTrace();

            return Response.status(500).entity(e).build();
        }
        return null;


    }

    /**
     * Method returns minimums and maximums measured value per each day in selected period
     * @param sensortype retrieves form client
     * @param startDate  retrieves form client
     * @param endDate retrieves form client
     * @return List
     */
    @Override
    @GET
    @Path("/minmax/{sensortype}/{startDate}/{endDate}")
    public Response getMinAndMaxPerDay(@PathParam("sensortype") String sensortype,
                                       @PathParam("startDate") String startDate,
                                       @PathParam("endDate") String endDate) {

        java.sql.Date startDate1 = null;
        try {
            startDate1 = new java.sql.Date(SDF.parse(startDate).getTime());
            java.sql.Date endDate1 = new java.sql.Date(SDF.parse(endDate).getTime());

            switch (sensortype.toLowerCase()) {
                case "temperature":
                    return Response.status(Response.Status.OK).entity(tempController.getMinAndMaxPerDay(startDate1, endDate1)).build();

                case "co2":
                    return Response.status(Response.Status.OK).entity(co2Controller.getMinAndMaxPerDay(startDate1, endDate1)).build();
                case "humidity":
                    return Response.status(Response.Status.OK).entity(humidityController.getMinAndMaxPerDay(startDate1, endDate1)).build();
            }

        } catch (ParseException e) {
            e.printStackTrace();

            return Response.status(500).entity(e).build();
        }

        return null;
    }


}
