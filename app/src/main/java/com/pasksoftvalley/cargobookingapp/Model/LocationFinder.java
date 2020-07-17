package com.pasksoftvalley.cargobookingapp.Model;

public class LocationFinder
{
    public double longitute,latitute;
    public LocationFinder()
    {

    }
    public LocationFinder(double longitute, double latitute) {
        this.longitute = longitute;
        this.latitute = latitute;

    }
    public double getLongitute() {
        return longitute;
    }
    public void setLongitute(double longitute) {
        this.longitute = longitute;
    }
    public double getLatitute() {
        return latitute;
    }
    public void setLatitute(double latitute) {
        this.latitute = latitute;
    }

}
