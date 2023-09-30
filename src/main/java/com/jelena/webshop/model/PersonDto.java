package com.jelena.webshop.model;

import lombok.Data;

@Data
public class PersonDto {

    // DTO - Data Transfer Object -> Kuidas me andmed saadame ning ei pea saatma originaalse kujul
    // andmevahetus klass,   ei võta otse andmebaasist (ENTITY-PERSONIST)

    private String firstName;
    private String lastName;
    private String email;

    public void add(PersonDto personDto) {
    }
}
