package data_access;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;

import java.util.List;


public interface IdatabaseAdapter<T> {


    public List<T> getAverage(Date startDate, Date endDate);

    public T getLastReading();

    public List<T> getAveragePerEachHour(Date date);

    public List<T> getMinAndMaxPerDay(Date startDate, Date endDate);




}
