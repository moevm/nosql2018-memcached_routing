package com.kvs.memcachedb.services;

import com.kvs.memcachedb.entity.Coords;
import com.kvs.memcachedb.entity.Sight;
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

    public String getCoordsByName(String name) {
        for (String coords : allSights) {
            Sight sight = (Sight)mcc.get(coords);
            if (sight != null) {
                if (sight.getObjectName().equals(name)) {
                    return coords;
                }
            }
        }
        return null;
    }

    public List<Coords> getCloseObjects(String coord, double requiredDistance) {
        ArrayList<Coords> coords = new ArrayList<>();

        Pair<Double, Double> currentLatAndLng = getLatAndLng(coord);

        for (String objCoords : allSights) {
            if (!objCoords.contains("null")) {
                Pair<Double, Double> latAndLng = getLatAndLng(objCoords);

                double distance = getDistance(currentLatAndLng, latAndLng);
                if (distance <= requiredDistance) {
                    coords.add(new Coords(latAndLng.getKey(), latAndLng.getValue()));
                }
            }
        }

        return coords;
    }

    public Pair<Pair<String, Double>, Pair<String, Double>> getStatisticSight(String currentPoint) {
        Pair<Double, Double> currentLatAndLng = getLatAndLng(getCoordsByName(currentPoint));
        double min = Double.MAX_VALUE;
        String closestObjectCoords = "";
        double max = -Double.MIN_VALUE;
        String furthersObjectCoords = "";

        for (String coords : allSights) {
            if (!coords.equals(getCoordsByName(currentPoint)) &&
                !coords.contains("null") &&
                mcc.get(coords) != null) {
                Pair<Double, Double> latAndLng = getLatAndLng(coords);
                double distance = getDistance(currentLatAndLng, latAndLng);
                if (distance < min) {
                    min = distance;
                    closestObjectCoords = coords;
                }
                if (distance > max) {
                    max = distance;
                    furthersObjectCoords = coords;
                }
            }
        }

        return new Pair<>(new Pair<>(((Sight) mcc.get(closestObjectCoords)).getObjectName(), min),
                          new Pair<>(((Sight) mcc.get(furthersObjectCoords)).getObjectName(), max));
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
