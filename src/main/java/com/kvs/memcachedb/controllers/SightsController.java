package com.kvs.memcachedb.controllers;

import com.google.gson.Gson;
import com.kvs.memcachedb.entity.Coords;
import com.kvs.memcachedb.entity.Sight;
import com.kvs.memcachedb.services.SightService;
import com.whalin.MemCached.MemCachedClient;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SightsController {

    private final MemCachedClient mcc;

    private final SightService service;

    @Autowired
    public SightsController(MemCachedClient mcc, SightService service) {
        this.mcc = mcc;
        this.service = service;
    }

    @GetMapping("/name_sight")
    String getSightByName(@RequestParam("name") String name, Model model) {

        String coordinates = service.getCoordsByName(name);

        StringBuilder coordBuilder = new StringBuilder(coordinates);

            model.addAttribute("coords_lat", coordBuilder.substring(0, coordBuilder.indexOf(":")));
            model.addAttribute("coords_lng", coordBuilder.substring(coordBuilder.indexOf(":") + 1,
                    coordBuilder.length()));

            Sight sight = (Sight) mcc.get(coordinates);

            model.addAttribute("name", sight.getObjectName());
            model.addAttribute("address", sight.getAddress());
            model.addAttribute("ensemble", sight.getEnsembleName());
            model.addAttribute("authors", sight.getAuthors());
            model.addAttribute("base", sight.getBase());
            model.addAttribute("date", sight.getDate());
            model.addAttribute("description", sight.getDescription());

            Pair<Pair<String, Double>, Pair<String, Double>> statistic = service.getStatisticSight(name, coordinates);

            model.addAttribute("closest_object_name", statistic.getKey().getKey());
            model.addAttribute("furthest_object_name", statistic.getValue().getKey());


        return "sight";
    }

    @GetMapping("/object_info_by_coords")
    String getSightByCoords(@RequestParam("sight_coords") String coords, Model model, RedirectAttributes redirectAttributes) {

        String[] split = coords.split("(, )");

        String coordsView = new BigDecimal(split[0].substring(1)).setScale(7, BigDecimal.ROUND_FLOOR).toString()
                + ":" + new BigDecimal(split[1].substring(0, split[1].length() - 1)).setScale(7, BigDecimal.ROUND_FLOOR).toString();


        redirectAttributes.addAttribute("name", ((Sight)mcc.get(coordsView)).getObjectName());
        return "redirect:name_sight";
    }

    @GetMapping("/closest_sights")
    String getClosestObjects(@RequestParam("addresses") String addresses, Model model) {
        String[] elements = addresses.split(";");

        ArrayList<Coords> coords = new ArrayList<>();

        for (int i = 0; i < elements.length - 1; i++) {
            List<Coords> closeObjects =
                    service.getCloseObjects(elements[i],
                                            service.getDistance(service.getLatAndLng(elements[i]),
                                                                service.getLatAndLng(elements[i + 1])));
            coords.addAll(closeObjects);
        }

        Gson gson = new Gson();

        model.addAttribute("coords", gson.toJson(coords));

        ArrayList<Coords> way = new ArrayList<>();
        for (String el : elements) {
            Pair<Double, Double> latAndLng = service.getLatAndLng(el);
            way.add(new Coords(latAndLng.getKey(), latAndLng.getValue()));
        }

        model.addAttribute("way", gson.toJson(way));

        return "sights_way";
    }

    @GetMapping("/my_position_rec")
    String getRecommends(@RequestParam("coords") String coords, Model model){
        List<Coords> closeObjects = service.getCloseObjects(coords, 100.0d);

        Gson gson = new Gson();

        model.addAttribute("close_objects", gson.toJson(closeObjects));
        return "my_position";
    }

    @GetMapping("/export_dataset")
    String getExportPage(Model model){
        List<List<String>> allDataset = service.getAllDataset();
        String s = new Gson().toJson(allDataset);
        model.addAttribute("dataset", s);
        return "save_dataset";
    }
}
