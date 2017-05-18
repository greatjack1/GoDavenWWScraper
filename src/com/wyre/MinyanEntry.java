package com.wyre;

/**
 * This class encapsulates a minyan entry for comparison
 * Created by yaakov on 5/16/17.
 */
public class MinyanEntry {
    private String name = "";
    private String address = "";
    private String MinLat = "";
    private String MinLon = "";
    private String MaxLat = "";
    private String MaxLon = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMinLat() {
        return MinLat;
    }

    public void setMinLat(String minLat) {
        MinLat = minLat;
    }

    public String getMinLon() {
        return MinLon;
    }

    public void setMinLon(String minLon) {
        MinLon = minLon;
    }

    public String getMaxLat() {
        return MaxLat;
    }

    public void setMaxLat(String maxLat) {
        MaxLat = maxLat;
    }

    public String getMaxLon() {
        return MaxLon;
    }

    public void setMaxLon(String maxLon) {
        MaxLon = maxLon;
    }


    public String getAddress() {
        return address;

    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object obj) {
        MinyanEntry em = (MinyanEntry) obj;
        //check if all of the properties are equal and if not then return false
        if (!(getName().equals(em.getName()))) {
            return false;
        }
        if (!(getAddress().equals(em.getAddress()))) {
            return false;
        }
        if (!(getMinLat().equals(em.getMinLat()))) {
            return false;
        }
        if (!(getMinLon().equals(em.getMinLon()))) {
            return false;
        }
        if (!(getMaxLat().equals(em.getMaxLat()))) {
            return false;
        }
        if (!(getMaxLon()).equals(em.getMaxLon())) {
            return false;
        }
        return true;
    }
}
