package com.kvs.memcachedb.services;

import com.kvs.memcachedb.entity.Coords;
import com.whalin.MemCached.MemCachedClient;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SightService {
    private final MemCachedClient mcc;
    private final List<String> allSights;

    @Autowired
    public SightService(MemCachedClient mcc, List<String> allSight) {
        this.mcc = mcc;
        this.allSights = allSight;
    }

    public List<Coords> getCloseObjects(String coord, double requiredDistance) {
        ArrayList<Coords> coords = new ArrayList<>();

        Pair<Double, Double> currentLatAndLng = getLatAndLng(coord);

        for (String sight : allSights) {
            String objCoords = (String) mcc.get(sight);
            Pair<Double, Double> latAndLng = getLatAndLng(objCoords);

            double distance = getDistance(currentLatAndLng, latAndLng);
            if (distance <= requiredDistance){
                coords.add(new Coords(latAndLng.getKey(), latAndLng.getValue()));
            }
        }

        return  coords;
    }

    public Pair<Pair<String, Double>, Pair<String, Double>> getStatisticSight(String currentPoint){
        Pair<Double, Double> currentLatAndLng = getLatAndLng((String)mcc.get(currentPoint));
        double min = Double.MAX_VALUE;
        String closestObjectName = "";
        double max = -Double.MIN_VALUE;
        String furthersObjectName = "";

        for (String sight : allSights) {
            if (!sight.equals(currentPoint)) {
                String coords = (String) mcc.get(sight);
                if (coords != null) {
                    Pair<Double, Double> latAndLng = getLatAndLng(coords);
                    double distance = getDistance(currentLatAndLng, latAndLng);
                    if (distance < min) {
                        min = distance;
                        closestObjectName = sight;
                    }
                    if (distance > max) {
                        max = distance;
                        furthersObjectName = sight;
                    }
                }
            }
        }

        return new Pair<>(new Pair<>(closestObjectName, min), new Pair<>(furthersObjectName, max));
    }

    public double getDistance(Pair<Double, Double> currentLatAndLng, Pair<Double, Double> latAndLng) {
        return Math.acos(Math.sin(currentLatAndLng.getKey())
                        * Math.sin(latAndLng.getKey())
                        + Math.cos(currentLatAndLng.getKey())
                        * Math.cos(latAndLng.getKey())
                        * Math.cos(currentLatAndLng.getValue() - latAndLng.getValue()));
    }

    public Pair<Double, Double> getLatAndLng(String coords) {
        StringBuilder coordBuilder = new StringBuilder(coords);
        Double lat = new Double(coordBuilder.substring(0, coordBuilder.indexOf(":")));
        Double lng = new Double(coordBuilder.substring(coordBuilder.indexOf(":") + 1, coordBuilder.length()));
        return new Pair<>(lat, lng);
    }
}
