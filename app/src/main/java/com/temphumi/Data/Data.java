package com.temphumi.Data;


public class Data {
    public int id;
    public int soilTemperature;
    public int soilHumidity;
    public int airTemperature;
    public int airHumidity;
    public String currentTime;
    public Data(int soilTemperature, int soilHumidity, int airTemperature,
                int airHumidity, String currentTime) {
        super();
        this.soilTemperature = soilTemperature;
        this.soilHumidity = soilHumidity;
        this.airTemperature = airTemperature;
        this.airHumidity = airHumidity;
        this.currentTime = currentTime;
    }
    public Data(){}
}
