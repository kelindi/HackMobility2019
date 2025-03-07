package com.example.jas10022.parkingapp;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.mapping.MapRoute;
import com.here.android.mpa.routing.RouteManager;
import com.here.android.mpa.routing.RouteOptions;
import com.here.android.mpa.routing.RoutePlan;
import com.here.android.mpa.routing.RouteResult;

import java.util.List;

public class Directions {


    private double currentlat = MainActivity.currentLatitude;
    private double currentlon = MainActivity.currentLongitude;
    private RouteManager rm;
    private MapRoute mapRoute;

    public Directions(GeoCoordinate endpoint){
        this(new Coordinate(endpoint));
    }

    public Directions (Coordinate endpoint){
        this(endpoint.getLatitude(), endpoint.getLongitude());
    }

    public Directions (double lat, double lng){
        rm = new RouteManager();
        RoutePlan routePlan = new RoutePlan();
        routePlan.addWaypoint(new GeoCoordinate(currentlat, currentlon));
        routePlan.addWaypoint(new GeoCoordinate(lat, lng));

        RouteOptions routeOptions = new RouteOptions();
        routeOptions.setTransportMode(RouteOptions.TransportMode.CAR);
        routeOptions.setRouteType(RouteOptions.Type.FASTEST);

        routePlan.setRouteOptions(routeOptions);

        // Calculate the route
        rm.calculateRoute(routePlan, new RouteListener());


    }

    public void end(){

        rm.cancel();
        MainActivity.map.removeMapObject(mapRoute);
    }

    private class RouteListener implements RouteManager.Listener {

        // Method defined in Listener
        public void onProgress(int percentage) {
            // Display a message indicating calculation progress
        }

        // Method defined in Listener
        public void onCalculateRouteFinished(RouteManager.Error error, List<RouteResult> routeResult) {
            // If the route was calculated successfully
            if (error == RouteManager.Error.NONE) {
                // Render the route on the map
                mapRoute = new MapRoute(routeResult.get(0).getRoute());
                MainActivity.map.addMapObject(mapRoute);
            }
            else {
                System.out.println("Directions failed with error: " + error);
            }
        }
    }
}

