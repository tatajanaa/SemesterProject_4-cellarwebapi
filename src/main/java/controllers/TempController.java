package controllers;

import data_access.JDBC_connection.DataWarehouseConnection;
import data_access.IdatabaseAdapter;
import data_access.JDBC_connection.SourceDbConnection;
import data_access.TemperatureRepo;
import model.Temperature;
import org.codehaus.jackson.annotate.JsonIgnoreType;

import javax.crypto.spec.PSource;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.List;

public class TempController {


    public TempController() {


    }

    public List<Temperature> getTempertaures(Date startDate, Date endDate) {
        IdatabaseAdapter adapter = new TemperatureRepo(DataWarehouseConnection.getConnection());
        List<Temperature> targetList = null;
        List<Temperature> sourceList =null;

        try {
            sourceList= new ArrayList<Temperature>(adapter.getReadings(startDate, endDate));
            targetList = new ArrayList<>();

            targetList.add(sourceList.get(0));

        for (int i = 1; i < sourceList.size(); i++) {
             if(sourceList.get(i).getReading()!=sourceList.get(i-1).getReading()){
                targetList.add(sourceList.get(i));
            }
        }


        }
        catch (ParseException | SQLException e) {
            e.printStackTrace();

        }
         return targetList ;
    }


    public Temperature getLastTemperatureReading(){
        IdatabaseAdapter adapter1 = new TemperatureRepo(SourceDbConnection.getConnection());
        try {
            return (Temperature) adapter1.getLastReading();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
