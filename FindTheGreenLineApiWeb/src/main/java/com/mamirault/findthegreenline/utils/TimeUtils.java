package com.mamirault.findthegreenline.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
  public static long getRelative(long time) throws ParseException {
    return time - getMidnight();
  }

  public static long relativeToCurrent(long time) throws ParseException {
    return time + getMidnight();
  }

  public static long getMidnight() throws ParseException {
    DateFormat dayFormat = new SimpleDateFormat("MM/dd/yyyy");
    DateFormat midnightFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SS");
    Date midnight = midnightFormat.parse(dayFormat.format(System.currentTimeMillis()) + " 00:00:00:00");
    return midnight.getTime();
  }

  /*public static long UTCToEDT(long time) {
    return time + TimeUnit.HOURS.toMillis(5);
  }

  public static long EDTToUTC(long time) {
    return time - TimeUnit.HOURS.toMillis(5);
  }*/

  public static long getCurrentEDT() {
    return System.currentTimeMillis();
  }

  /*public static long getCurrentUTC() {
    return EDTToUTC(System.currentTimeMillis());
  }*/
}
