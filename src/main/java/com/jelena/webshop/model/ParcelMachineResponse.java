package com.jelena.webshop.model;

import lombok.Data;

import java.util.List;

@Data
public class ParcelMachineResponse {

    private List<OmnivaPM> omnivaPMs;
    private List<SmartpostPM> smartpostPMs;
}
