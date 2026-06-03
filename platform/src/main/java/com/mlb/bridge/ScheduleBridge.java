package com.mlb.bridge;

public class ScheduleBridge extends ItkBridge {

    public native ItkResult getScheduleForDate(String date);
    
}
