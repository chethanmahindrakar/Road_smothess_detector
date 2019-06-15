package com.cecar.util;

public class RoadSensorData {
    float acceleration;
    double altitude;
    float ax;
    float ay;
    float az;
    long capturedDate;
    double d3Distance;
    float gpsAccuracy;
    double init_d3Distance;
    int key;
    double latitude;
    double longitude;

    public double getInit_d3Distance() {
        return this.init_d3Distance;
    }

    public void setInit_d3Distance(double init_d3Distance) {
        this.init_d3Distance = init_d3Distance;
    }

    public float getGpsAccuracy() {
        return this.gpsAccuracy;
    }

    public void setGpsAccuracy(float gpsAccuracy) {
        this.gpsAccuracy = gpsAccuracy;
    }

    public double getD3Distance() {
        return this.d3Distance;
    }

    public void setD3Distance(double d3Distance) {
        this.d3Distance = d3Distance;
    }

    public float getAz() {
        return this.az;
    }

    public void setAz(float az) {
        this.az = az;
    }

    public float getAy() {
        return this.ay;
    }

    public void setAy(float ay) {
        this.ay = ay;
    }

    public float getAx() {
        return this.ax;
    }

    public void setAx(float ax) {
        this.ax = ax;
    }

    public float getAcceleration() {
        return this.acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public double getAltitude() {
        return this.altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public long getCapturedDate() {
        return this.capturedDate;
    }

    public void setCapturedDate(long capturedDate) {
        this.capturedDate = capturedDate;
    }
}
