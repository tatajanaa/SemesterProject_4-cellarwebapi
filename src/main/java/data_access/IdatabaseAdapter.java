package data_access;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;

import java.util.List;


public interface IdatabaseAdapter<T> {

    public List<T> getReadings(Date startDate, Date endDate) throws ParseException, SQLException;
    public T getLastReading() throws SQLException;

}
