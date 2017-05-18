package com.wyre;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by yaakov on 5/16/17.
 */
public class NeomatinGetter {

    private String address;
    private String minLat;
    private String minLon;
    private String maxLat;
    private String maxLon;
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMinLat() {
        return minLat;
    }

    public void setMinLat(String minLat) {
        this.minLat = minLat;
    }

    public String getMinLon() {
        return minLon;
    }

    public void setMinLon(String minLon) {
        this.minLon = minLon;
    }

    public String getMaxLat() {
        return maxLat;
    }

    public void setMaxLat(String maxLat) {
        this.maxLat = maxLat;
    }

    public String getMaxLon() {
        return maxLon;
    }

    public void setMaxLon(String maxLon) {
        this.maxLon = maxLon;
    }

    public NeomatinGetter(String address){
        this.address= address;
        try {
            //use the nominatim api to get the bounding box
            System.out.println("Contacting nominatim to get bounding box for " + address);
            //when creating the url replace spaces with %20
            URL url1 = new URL(("http://nominatim.openstreetmap.org/search/" + address + "?format=json").replace(" ","%20"));
            java.net.URLConnection c = url1.openConnection();
            //wrap the stream in a stream reader and then in a buffered reader to be able to read the response line by line
            BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
            String buffer = "";
            String response = "";
            //use a while loop to convert the whole stream into a string
            while (null != (buffer = br.readLine())) {
                response = response + buffer;
            }
            String boundText = (response.substring(response.indexOf("\"boundingbox\":[")+16,response.indexOf("],\"lat\":")));
            //extract the coordinates using sub string methods
            minLat = (boundText.substring(0, boundText.indexOf("\",\"")));
             boundText = boundText.substring(minLat.length()+ 3);
              maxLat = (boundText.substring(0,boundText.indexOf("\",\"")));
                      boundText = boundText.substring(maxLat.length() + 3);
            minLon = (boundText.substring(0,boundText.indexOf("\",\"")));
            boundText = boundText.substring(minLon.length() + 3);
            maxLon = (boundText.substring(0,boundText.indexOf("\"")));
System.out.println("The following are the bounding box coordinates: MinLat:"+ minLat +" MaxLat:"+maxLat +" MinLon:"+minLon+" MaxLon:" +maxLon  );

        } catch (Exception e) {
            System.out.println("A Error occured while retreiving data from the neominatim api:Error: " + e.getMessage());
        }
    }

}
