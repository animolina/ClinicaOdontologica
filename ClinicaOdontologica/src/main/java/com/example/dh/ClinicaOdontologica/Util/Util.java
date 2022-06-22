package com.example.dh.ClinicaOdontologica.Util;
import java.sql.Date;
import java.sql.Timestamp;


public class Util {
    public static Timestamp dateToTimestamp(Date date){
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;

    }

    //Convierte un Date en un SQL Date
    public static java.sql.Date utilDateToSqlDate(Date utilDate){
        long timeInMilliSeconds = utilDate.getTime();
        java.sql.Date sqlDate = new java.sql.Date(timeInMilliSeconds);
        return sqlDate;
    }
}
