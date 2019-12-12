package modules;


import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeParser
{
        private Date date;

        public String getStringFromDate(Date date)
        {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            return dateFormat.format(date);
        }
        public String getStringFromTime(Time time)
        {
            DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
            return dateFormat.format(time);
        }


        public Date getDateFromString(String date)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            try
            {
                return sdf.parse(date);
            } catch (ParseException e)
            {
                e.printStackTrace();
                return null;
            }
        }
        public Time getTimeFromString(String time)
        {
            return Time.valueOf(time);
        }
}
