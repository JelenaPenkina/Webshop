package com.jelena.webshop.controller;

import com.jelena.webshop.entity.Order;
import com.jelena.webshop.entity.Person;
import com.jelena.webshop.entity.Product;
import com.jelena.webshop.model.EverypayData;
import com.jelena.webshop.model.EverypayLink;
import com.jelena.webshop.model.EverypayResponse;
import com.jelena.webshop.repository.OrderRepository;
import com.jelena.webshop.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@RestController
public class OrderController {
    // Order hakkab liikuma andmebaasi ainult läbi makse
    // Post ja Put pole tegelikult vaja
    @Autowired
    OrderRepository orderRepository;

    @GetMapping("order")
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("order/{id}")
    public Order getOrder(@PathVariable Long id) {

        return orderRepository.findById(id).get();
    }



    @Autowired  // Peab kogu aeg eraldi olema kirjutatud, sest töötab ning aktiveerub nii
    PersonRepository personRepository;

    // LISAMISE UUE ORDERI ANDMEBAASI SEL HETKEL KUI MAKSET ALUSTATAKSE
    @PostMapping("payment/{personId}")
    public EverypayLink payment(@PathVariable Long personId, @RequestBody List<Product> products) {
        double sum = products.stream().mapToDouble(Product::getPrice).sum();
//        products.stream().mapToDouble(e -> e.getPrice() * 1.1).sum();

//       Teine võimalus kuidas teha:
//       double sum2 = 0;
//        for(Product p: products) {
//            sum2 += p.getPrice();
//        }
        Person person = personRepository.findById(personId).get();

        Order order = new Order();
        order.setPaid(false);
        order.setTotalSum(sum);
        order.setCreationDate(new Date());
        order.setProducts(products);
        order.setPerson(person);

        Order dbOrder = orderRepository.save(order);

        RestTemplate restTemplate = new RestTemplate();

        String url = "https://igw-demo.every-pay.com/api/v4/payments/oneoff";

        // Fn + END klaviatuuri vajutades läheb rea lõppu

        EverypayData data = new EverypayData();
        data.setApi_username("abc12345");
        data.setAccount_name("EUR3D1");

        // Nad saaksid ise muutuda iga kord iseseisvalt
        data.setAmount(sum);
        data.setOrder_reference(dbOrder.getId().toString());

        // esialgu suvalised numbrid/tähed ja pärast genereerib id järgi kasutajat ning kuupäeva järgselt
        data.setNonce("a9b7f7" + dbOrder.getId() + new Date());

        // ZonedDateTime genereerib iseseisvalt õige aja
        data.setTimestamp(ZonedDateTime.now().toString());
        data.setCustomer_url("https://maksmine.web.app/makse");


        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Basic ZTplMzQ1MzI2NDI0NzM1NDM3NDQ1NjhnaGpmamhmZXJ0ZXFxNA==");
        HttpEntity<EverypayData> httpEntity = new HttpEntity<>(data, headers);

        // JSON KUJUL STRINGI ASEMEL    EverypayResponse

        ResponseEntity<EverypayResponse> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, EverypayResponse.class);

        EverypayLink everypayLink = new EverypayLink();

        everypayLink.setLink(response.getBody().payment_link);
        return everypayLink;

//        orderRepository.save(new Order(1L,false,sum,new Date(),products,new Person()));

        // FRONT END -> Ei saa hästi hakkama andmete vastuvõtmisega/saatmisega, kui tegu on Stringiga
    }
}



//    * märk aitab läbi korrutamist hinnale palju paremini(valuutavahetus, soodustused, käibemaks jne)
//     mapToDouble -> võiamldab teisendada (nt summiks)
//     Kui lisaksime ainukt map(), siis oleks keerulisem midagi lisada pärast punkte funktsioone


//    kustutab ID järgi
//    @DeleteMapping("order")
//    public List<Order> deleteOrder(@PathVariable Long id) {
//       orderRepository.deleteById(id);
//        return orderRepository.findAll();
//    }

//    @PutMapping("editOrders")
//    public List<Order> editOrders(@RequestBody Order order) {
//        if(orderRepository.existsById(order.getId())) {
//            orderRepository.save(order);
//        }
//        return orderRepository.findAll();
//    }



