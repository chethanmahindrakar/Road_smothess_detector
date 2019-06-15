package com.cecar.imp;

import com.cecar.util.RoadSensorData;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;

public class ReadSensorData {
    public Vector<RoadSensorData> roadSensorData = new Vector();

    public void readData(String filePath) {
        System.out.println("LittleFlower Road Data " + filePath);
        try {
            File f = new File(filePath);
            System.out.println("File status : " + f.exists());
            BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(f))));
            int lineCount = 0;
            while (true) {
                String strLine = br.readLine();
                if (strLine != null) {
                    int lineCount2 = lineCount + 1;
                    if (lineCount == 0) {
                        lineCount = lineCount2;
                    } else {
                        RoadSensorData rsd = new RoadSensorData();
                        System.out.println(lineCount2 + "---- " + strLine);
                        StringTokenizer stringTokenizer = new StringTokenizer(strLine, "#");
                        int field = 0;
                        while (stringTokenizer.hasMoreTokens()) {
                            String str = stringTokenizer.nextToken();
                            System.out.println("Field : " + field);
                            switch (field) {
                                case 0:
                                    field++;
                                    break;
                                case 7:
                                    float ax = Float.parseFloat(str);
                                    System.out.println("accelorometer x = " + ax);
                                    rsd.setAx(ax);
                                    field++;
                                    break;
                                case 8:
                                    float ay = Float.parseFloat(str);
                                    System.out.println(" Accelerometer y = " + ay);
                                    rsd.setAy(ay);
                                    field++;
                                    break;
                                case 9:
                                    float az = Float.parseFloat(str);
                                    System.out.println(" Accelerometer z= " + az);
                                    rsd.setAz(az);
                                    rsd.setD3Distance(getSqroot(rsd.getAx(), rsd.getAy(), rsd.getAz()));
                                    field++;
                                    break;
                                case 11:
                                    field++;
                                    break;
                                case 31:
                                    float altitude = Float.parseFloat(str);
                                    System.out.println("altitude =" + altitude);
                                    rsd.setAltitude((double) altitude);
                                    field++;
                                    break;
                                case 41:
                                    float lat = Float.parseFloat(str);
                                    System.out.println(" lat : " + lat);
                                    rsd.setLatitude((double) lat);
                                    field++;
                                    break;
                                case 51:
                                    float longitude = Float.parseFloat(str);
                                    System.out.println(" Longitude = " + longitude);
                                    rsd.setLongitude((double) longitude);
                                    field++;
                                    break;
                                case 61:
                                    float acceleration = Float.parseFloat(str);
                                    System.out.println("Acceleration =" + acceleration);
                                    rsd.setAcceleration(acceleration);
                                    field++;
                                    break;
                                default:
                                    field++;
                                    System.out.println(" Default value..." + str);
                                    break;
                            }
                        }
                        this.roadSensorData.add(rsd);
                        lineCount = lineCount2;
                    }
                } else {
                    return;
                }
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.getMessage();
        } catch (Exception ee) {
            ee.getMessage();
        }
    }

    private double getSqroot(float x, float y, float z) {
        return Math.sqrt((double) (((x * x) + (y * y)) + (z * z)));
    }

    public void writeData() {
        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(new File("c:\\tmp\\map.txt")));
            int sz = this.roadSensorData.size();
            System.out.println("Size  : " + sz);
            for (int i = 0; i < sz; i++) {
                RoadSensorData rs = (RoadSensorData) this.roadSensorData.get(i);
                System.out.println(" lang " + rs.getLongitude());
                String langitude = new Float(rs.getLongitude()).toString();
                fw.write(langitude + "," + new Float(rs.getLatitude()).toString() + "," + "0");
                fw.newLine();
            }
            fw.close();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    public void writeAxisData(String st) {
        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(new File(st)));
            int sz = this.roadSensorData.size();
            System.out.println("Size  : " + sz);
            for (int i = 0; i < sz; i++) {
                RoadSensorData rs = (RoadSensorData) this.roadSensorData.get(i);
                String x = new Float(rs.getAx()).toString();
                String y = new Float(rs.getAy()).toString();
                String z = new Float(rs.getAz()).toString();
                String distance3d = new Double(rs.getD3Distance()).toString();
                System.out.println("x : " + x + "  y : " + y + "  z : " + z + " 3dDistance : " + distance3d);
                fw.write(distance3d + "," + x + "," + y + "," + z);
                fw.newLine();
            }
            fw.close();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ReadSensorData rsd = new ReadSensorData();
        rsd.readData("C:\\tmp\\test4\\01.09.26.44_msensors.txt");
        rsd.writeData();
        rsd.writeAxisData("c:\\tmp\\test4\\axis3.txt");
    }
}
