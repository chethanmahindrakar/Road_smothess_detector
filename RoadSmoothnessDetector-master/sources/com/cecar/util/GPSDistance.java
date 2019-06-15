package com.cecar.util;

public class GPSDistance {
    public static void main(String[] args) {
        double a1 = 12.931824d / 57.294002532958984d;
        double a2 = 77.541464d / 57.294002532958984d;
        double b1 = 12.922041d / 57.294002532958984d;
        double b2 = 77.559148d / 57.294002532958984d;
        double t3 = Math.sin(a1) * Math.sin(b1);
        System.out.println(" Distance =" + (6366000.0d * Math.acos(((((Math.cos(a1) * Math.cos(a2)) * Math.cos(b1)) * Math.cos(b2)) + (((Math.cos(a1) * Math.sin(a2)) * Math.cos(b1)) * Math.sin(b2))) + t3)));
    }
}
