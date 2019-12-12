package modules;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser
{
        private Date date;

        public String getStringFromData(Date date)
        {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            return dateFormat.format(date);
        }

        public Date getDateFromString(String date)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM HH:mm:ss z yyyy");
            try
            {
                return sdf.parse(date);
            } catch (ParseException e)
            {
                e.printStackTrace();
                return null;
            }
        }
}
