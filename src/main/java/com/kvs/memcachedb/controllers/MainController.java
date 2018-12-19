package com.kvs.memcachedb.controllers;

import au.com.bytecode.opencsv.CSVReader;
import com.kvs.memcachedb.entity.Sight;
import com.kvs.memcachedb.utils.OpenStreetMapUtils;
import com.whalin.MemCached.MemCachedClient;
import org.apache.commons.io.input.CharSequenceReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Controller
public class MainController {

    private final MemCachedClient mcc;

    @Autowired
    public MainController(MemCachedClient mcc) {
        this.mcc = mcc;
    }

    @GetMapping("/")
    String getMainPage() throws IOException {
//        ArrayList<Sight> sights = new ArrayList<>();
//        CSVReader reader = new CSVReader(new FileReader("src/main/resources/Data.csv"), ',' , '"' , 1);
//
//        String[] nextLine;
//        int i = 0;
//        while ((nextLine = reader.readNext()) != null && i < 10) {
//            Sight sight = new Sight();
//            sight.setEnsembleName(nextLine[1]);
//            sight.setObjectName(nextLine[2]);
//            sight.setDate(nextLine[3]);
//            sight.setAuthors(nextLine[4]);
//            sight.setAddress(nextLine[5]);
//            sight.setDistrict(nextLine[6]);
//            sight.setBase(nextLine[8]);
//            sight.setDescription(nextLine[9]);
//            sights.add(sight);
//            ++i;
//        }
//
//        sights.forEach(System.out::println);
//
//
//        mcc.flushAll();
//        mcc.set("The building of the Consistory administration of the Mogilev Roman Catholic Archdiocese with the Church of the assumption of the virgin Mary (Seminary of the Roman Catholic Archdiocese)", "59.916305:30.312108");
//        mcc.set("The building of the arena (esercitarsi) of the life guards Izmailovsky regiment", "59.9173965:30.3097739");
//        mcc.set("In early 1895, Lenin met with the St. Petersburg social-Democrats", "59.9158606:30.3106057");
//        mcc.set("Transformer substation", "59.9202004:30.3036704");
//        mcc.set("Manege of count G. I. Ribotpierre (the Building of the St. Petersburg athletic society)", "59.938732:30.316229");
//        mcc.set("Transformer substation", "59.924330:30.322837");
//        mcc.set("Manege of count G. I. Ribotpierre (the Building of the St. Petersburg athletic society)", "59.913206:30.310686");
//        mcc.set("The mansion and the apartment house of N. G. Kudryavtseva", "59.913818:30.301330");
//        mcc.set("House Securing", "59.937912:30.304788");
//        mcc.set("The house where chess player Chigorin M. I. lived in 1901-1907.", "59.93804164:30.30609721");
//
//        mcc.set("59.916305:30.312108", sights.get(0));
//        mcc.set("59.9173965:30.3097739", sights.get(1));
//        mcc.set("59.9158606:30.3106057", sights.get(2));
//        mcc.set("59.9202004:30.3036704", sights.get(3));
//        mcc.set("59.938732:30.316229", sights.get(4));
//        mcc.set("59.924330:30.322837", sights.get(5));
//        mcc.set("59.913206:30.310686", sights.get(6));
//        mcc.set("59.913818:30.301330", sights.get(7));
//        mcc.set("59.937912:30.304788", sights.get(8));
//        mcc.set("59.93804164:30.30609721", sights.get(9));

        return "main_page";
    }

    @PostMapping("/upload")
    String setDatabase(MultipartFile file) throws IOException {

        Reader inputStreamReader = new CharSequenceReader(new String(file.getBytes(), UTF_8));
        CSVReader reader = new CSVReader(inputStreamReader, ',' , '"' , 1);

        String[] nextLine;
        Map<String, Double> coords = null;
        while ((nextLine = reader.readNext()) != null) {
            Sight sight = new Sight();
            if (nextLine.length > 2)
            sight.setEnsembleName(nextLine[1]);
            if (nextLine.length > 3)
            sight.setObjectName(nextLine[2]);
            if (nextLine.length > 4)
            sight.setDate(nextLine[3]);
            if (nextLine.length > 5)
            sight.setAuthors(nextLine[4]);
            if (nextLine.length > 6)
            sight.setAddress(nextLine[5]);
            if (nextLine.length > 7)
            sight.setDistrict(nextLine[6]);
            if (nextLine.length > 8)
            sight.setBase(nextLine[8]);
            if (nextLine.length > 9)
            sight.setDescription(nextLine[9]);

            String address = "Санкт-Петербург " + sight.getAddress();

            try {
                coords = OpenStreetMapUtils.getInstance().getCoordinates(address);
            } catch (Exception ex){
                ex.printStackTrace();
            }

            if (coords != null && coords.get("lat") != null &&
                coords.get("lon") != null &&
                !coords.get("lat").toString().equals("null")) {
                mcc.set(coords.get("lat").toString() + ":" + coords.get("lon").toString(), sight);
            }

        }
        return "main_page";
    }

    @GetMapping("/set_dataset")
    String getUploadPage(){
        return "set_dataset";
    }

    @GetMapping("/about")
    String getAboutPage(){
        return "about";
    }

    @GetMapping("/contact")
    String getConnectPage(){
        return "contact";
    }

    @GetMapping("/my_position")
    String getMyPositionPage(Model model){
        model.addAttribute("close_objects", "");
        return "my_position";
    }
}
