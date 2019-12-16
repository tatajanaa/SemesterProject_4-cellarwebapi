package data_access;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;

import java.util.List;


public interface IdatabaseAdapter<T> {


    public List<T> getAverage(Date startDate, Date endDate) throws SQLException;
    public T getLastReading() throws SQLException;
    public List<T> getAveragePerEachHour(Date date) throws SQLException;

}
