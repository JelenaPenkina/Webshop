package com.jelena.webshop.model;

import lombok.Data;

import java.util.ArrayList;
@Data
public class EverypayResponse {

    public String account_name;
    public String order_reference;
    public Object email;
    public Object customer_ip;
    public String customer_url;
    public String payment_created_at;
    public double initial_amount;
    public double standing_amount;
    public String payment_reference;
    public String payment_link;
    public ArrayList<PaymentMethod> payment_methods;
    public String api_username;
    public Object warnings; // Object --> Suudab alati kasutada sees ning puurida ja uurida
    public Object stan;
    public Object fraud_score;
    public String payment_state;
    public Object payment_method;
    public String currency;
    public  Object applepay_merchant_identifier;
    public String descriptor_country;


@Data
class PaymentMethod {
    public String source;
    public String display_name;
    public Object country_code;
    public String payment_link;
    public String logo_url;
    public boolean applepay_available;
    public String applepay_merchant_display_name;
}
}
