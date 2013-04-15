package com.mamirault.findthegreenline.utils;

import java.text.ParseException;

import org.junit.Test;

public class TimeUtilsTest {
  @Test
  public void getRelativeTest() throws ParseException {
    long time = 1365991380000L; 
    long midnight = TimeUtils.getMidnight();
    long relativeTime = TimeUtils.getRelative(time);
    
    System.out.println(relativeTime);
    System.out.println(midnight);
    System.out.println(time);
  }
}
