package com.wyre;

import com.wyre.JDaven.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    //The array list to hold the minyan entrys
    private static List<MinyanEntry> me = new ArrayList<>();

    public static void main(String[] args) {
        //create 4 thread of work for the program to iterate throught all of the values
        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                getShuls(-90.00,-45.00,"Shuls1.txt");
            }
        });
        th1.start();
        Thread th2 = new Thread(new Runnable() {
            @Override
            public void run() {
                getShuls(-45.00,0.00,"Shuls2.txt");
            }
        });
        th2.start();
        Thread th3 = new Thread(new Runnable() {
            @Override
            public void run() {
                getShuls(0.00,45.00,"Shuls3.txt");
            }
        });
        th3.start();
        Thread th4 = new Thread(new Runnable() {
            @Override
            public void run() {
                getShuls(45.00,90.00,"Shuls4.txt");
            }
        });
        th4.start();
    }
    public static void getShuls(double StartLat,double EndLat,String fileName){
        // loop through all latitudes
        for (double lat = StartLat; lat < EndLat; lat = lat + .1) { // use a .1 stepping counter
            //loop through all longitudes
            for (double lon = -180.00; lon <= 180.00; lon = lon + .1) { //use a .1 stepping counter so that we dont miss any shuls
                System.out.println("Getting results from go daven for lat:" + lat + " Lon: " + lon);
                try {
                    //use my JDaven Api to get the minyanim
                    JDaven jd = new JDaven(lat, lon, 20);
                    if (jd.getMinyanim() != null) {
                        System.out.println("Number of results for these values is: " + jd.getMinyanim().length);
                        //loop through all of the minyanim
                        for (minyanim mi : jd.getMinyanim()) {
                            System.out.println("Address of shul is: " + mi.getAddress() +" " + mi.getCity() +" "+ mi.getState());
                            MinyanEntry temp = new MinyanEntry();
                            temp.setName(mi.getName());
                            temp.setAddress(mi.getAddress() + " " + mi.getCity() + " " + mi.getState());
                            //check whether we already have this shul in the database and if we do then skip
                            boolean contains = false;
                            for (MinyanEntry entry : me) {
                                //check if any entry is the same as temp
                                if (entry.getAddress().equals(temp.getAddress())) {
                                    contains = true;
                                    break;
                                }
                            }

                            if(contains==true){ //since the shul is allready on the list, skip to the next iteration of the for loop
                                continue;
                            }

                            //get the latitude and longitude bounding box from the neomatin api
                            NeomatinGetter ng = new NeomatinGetter(temp.getAddress());
                            temp.setMinLon(ng.getMinLon());
                            temp.setMaxLon(ng.getMaxLon());
                            temp.setMinLat(ng.getMinLat());
                            temp.setMaxLat(ng.getMaxLat());
                            //check to ensure that none of the values are null, if they are then skip to next iteration
                            if(temp.getMaxLon()==null||temp.getMinLon()==null||temp.getMaxLat()==null||temp.getMinLat()==null){
                               System.out.println("Some values are null, skipping this shul");
                                continue;
                            }
                            //add the minyan to the list
                            System.out.println("Adding " + temp.getName() +"  to the list of shuls");
                           me.add(temp);

                        }
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }

        }
        //now lets out put all of the shul data to a text file
        try {
            System.out.println("Starting to print the results...");
            PrintWriter pw = new PrintWriter(fileName);
            for(MinyanEntry em: me){
                System.out.println("Adding " + em.getName() +" to the shul list");
             pw.println(em.getName());
             pw.println(em.getAddress());
             pw.println(em.getMinLat());
             pw.println(em.getMaxLat());
             pw.println(em.getMinLon());
             pw.println(em.getMaxLon());
            }
            pw.close();
        } catch(FileNotFoundException ex){
            ex.printStackTrace();
        }

    }
}
