package com.kvs.memcachedb.controllers;

import au.com.bytecode.opencsv.CSVReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.kvs.memcachedb.entity.Sight;
import com.kvs.memcachedb.utils.OpenStreetMapUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.*;

public class MainControllerTest {

    @Ignore
    @Test
    public void loadDataset() throws Exception {
        ArrayList<Sight> sights = new ArrayList<>();
        CSVReader reader = new CSVReader(new FileReader("src/main/resources/Data.csv"), ',', '"', 1);

        String[] nextLine;
        Map<String, Double> coords;
        int i = 0;
        StringBuilder result = new StringBuilder("");
        while ((nextLine = reader.readNext()) != null && i < 500) {
            if (i >= 100) {
                try (FileWriter writer = new FileWriter(new File("C:\\Users\\Aleksei_Kruglik\\Desktop\\study\\nosql\\nosql2018-memcached_routing\\src\\main\\resources\\coords"), true)) {
                    Sight sight = new Sight();
                    sight.setEnsembleName(nextLine[1]);
                    sight.setObjectName(nextLine[2]);
                    sight.setDate(nextLine[3]);
                    sight.setAuthors(nextLine[4]);
                    sight.setAddress(nextLine[5]);
                    sight.setDistrict(nextLine[6]);
                    sight.setBase(nextLine[8]);
                    sight.setDescription(nextLine[9]);
                    sights.add(sight);
                    String address = "Санкт-Петербург " + sight.getAddress();

                    coords = OpenStreetMapUtils.getInstance().getCoordinates(address);
                    result = new StringBuilder("");

                    result.append(i + 1)
                            .append(":")
                            .append(sight.getAddress())
                            .append(":")
                            .append(coords.get("lat"))
                            .append(":")
                            .append(coords.get("lon"))
                            .append("\n");

                    writer.write(result.toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            ++i;
        }
    }

}