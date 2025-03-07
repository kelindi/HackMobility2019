package com.example.jas10022.parkingapp;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.mapping.MapCircle;

public class ParkingSpot extends ParkingLocation {

    private Coordinate location;
    private boolean occupied;

    public ParkingSpot(Coordinate location, boolean occupied) {
        super(location);
        this.location = this.getLocation();
        this.occupied = occupied;
    }

    public ParkingSpot(Coordinate location, double rating, long num_ratings, boolean occupied) {
        super(location, rating, num_ratings);
        this.occupied = occupied;
    }

    public void parkInSpot() {
        this.occupied = true;
    }

    public void releaseSpot() {
        this.occupied = false;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public double heatmapRating(){
        if (occupied){
            return 1.0;
        }
        else{
            return 0;
        }
    }

    @Override
    public MapCircle heatmapLocation(){
        double lat = this.location.getLatitude();
        double lng = this.location.getLongitude();
        GeoCoordinate coord = new GeoCoordinate(lat, lng);
        MapCircle position = new MapCircle(50, coord);
        if (occupied) {
            position.setFillColor(0x55ff0000); //If occupied, make red
        }
        else{
            position.setFillColor(0x5500ff00); //ELSE MAKE GREEN
        }
        return position;
    }

}
