package com.jelena.webshop.controller;

import com.jelena.webshop.model.OmnivaPM;
import com.jelena.webshop.model.ParcelMachineResponse;
import com.jelena.webshop.model.SmartpostPM;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
public class ParcelMachineController {

    // 1 @PathVariable  URL: parcel-machines/{country}     parcel-machines/EE
    // 5+ @RequestBody   URL: parcel-machines   POST, PUT   {"country": EE} <-- eraldi mudel
    // 2-4 @RequestParam  URL: parcel-machines?country=EE    @RequestParam String country


    @GetMapping("parcel-machines/{country]")
    public ParcelMachineResponse getParcelMachines(@PathVariable String country) {

        RestTemplate restTemplate = new RestTemplate(); // Teeb API päringuid

        String omnivaUrl = "https://www.omniva.ee/locations.json";

       ResponseEntity<OmnivaPM[]> omnivaResponse =  restTemplate.exchange(
               // url kuhu,  meetodi liik,   body mille saadan,    mida ma seal lehelt saan
               omnivaUrl, HttpMethod.GET, null, OmnivaPM[].class);
//  ResponseEntity --> siia sisse liiguvad tagastatavad andmed, body, staatuskood, headerid


       List<OmnivaPM> omnivaPMs = Arrays.stream(omnivaResponse.getBody())
               .filter(e -> e.a0_NAME.equals(country))
               .toList();

        String smartpostUrl = "https://www.smartpost.ee/places.json";
        ResponseEntity<SmartpostPM[]> smartpostResponse =  restTemplate.exchange(
                smartpostUrl, HttpMethod.GET, null, SmartpostPM[].class);

//        return response.getBody();
        ParcelMachineResponse response = new ParcelMachineResponse();
        response.setOmnivaPMs(omnivaPMs);
        if (country.equals("EE")) {
            response.setSmartpostPMs(Arrays.asList(smartpostResponse.getBody()));
        } else {
            response.setSmartpostPMs(new ArrayList<>());
        }

        return response;

    }
}
