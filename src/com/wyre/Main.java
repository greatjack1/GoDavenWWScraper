package com.wyre;

import com.wyre.JDaven.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<MinyanEntry> me = new ArrayList<>();

    public static void main(String[] args) {
        // loop through all latitudes
        for (double lat = 40.00; lat < 41.00; lat = lat + 1) { // use a .1 stepping counter
            //loop through all longitudes
            for (double lon = -70.00; lon <= -69.90; lon = lon + .1) { //use a .1 stepping counter so that we dont miss any shuls
                System.out.println("Getting results from go daven for lat:" + lat + " Lon: " + lon);
                try {
                    JDaven jd = new JDaven(lat, lon, 20);
                    if (jd.getMinyanim() != null) {
                        System.out.println("Number of results for these values is: " + jd.getMinyanim().length);
                        for (minyanim mi : jd.getMinyanim()) {
                            System.out.println("Address of shul is: " + mi.getAddress() + mi.getCity() + mi.getState());
                            MinyanEntry temp = new MinyanEntry();
                            temp.setName(mi.getName());
                            temp.setAddress(mi.getAddress() + " " + mi.getCity() + " " + mi.getState());
                            //get the latitude and longitude from the neomatin api
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
                            boolean contains = false;
                            for (MinyanEntry entry : me) {
                                //check if any entry is the same as temp
                                if (entry.equals(temp)) {
                                    contains = true;
                                    break;
                                }
                            }
                            if(contains==false){
                                me.add(temp);
                            }

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
            PrintWriter pw = new PrintWriter("data.txt");
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
