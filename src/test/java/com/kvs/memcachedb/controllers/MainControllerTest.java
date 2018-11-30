package com.kvs.memcachedb.controllers;

import au.com.bytecode.opencsv.CSVReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
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
import java.util.Arrays;

import static org.junit.Assert.*;

public class MainControllerTest {

    @Test
    public void loadDataset() throws Exception {
        CSVReader reader = new CSVReader(new FileReader("src/main/resources/Data.csv"), ',' , '"' , 1);
        String[] nextLine;
//        while ((nextLine = reader.readNext()) != null) {
//            System.out.println(Arrays.toString(nextLine));
//        }


    }

}