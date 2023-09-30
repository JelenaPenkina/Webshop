package com.jelena.webshop.controller;

import com.jelena.webshop.entity.Person;
import com.jelena.webshop.model.PersonDto;
import com.jelena.webshop.repository.PersonRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Log4j2
@RestController
public class PersonController {

    @Autowired
    PersonRepository personRepository;

  // GET ALL PERSONS
    @GetMapping("persons")
    public List<Person> getPersons() {
        return personRepository.findAll();
    }


    @GetMapping("person-public2")
    public List<PersonDto> getPersonsPublic2() {
        List<Person> persons = personRepository.findAll(); // VÕtab kõik inimesed andmebaasist
        List<PersonDto> personDtos = new ArrayList<>();    // teeb tühja ning lühendatud baasist, teeb tühja array listi
        for (Person p : persons) {
            PersonDto personDto = new PersonDto();
            personDto.setFirstName(p.getFirstName());
            personDto.setLastName(p.getLastName());
            personDto.setEmail(p.getEmail());
            personDto.add(personDto);                      // paneb juurde tühja listi andmed
        }
        return personDtos;
    }
    // SEDA TAHAME AUTOMATISEERIDA
    @GetMapping("person-public")
    public List<PersonDto> getPersonsPublic() {
        List<Person> persons = personRepository.findAll();
        ModelMapper modelMapper = new ModelMapper(); // @Autowired //
        // ModelMapper -> kui teen päringuid, siis tagatausatl ta tekitab iga kord uue mälukoha ModelMapperi
        //  lisadedes juurde librabry modelMapper annab Ta võimaluseläbi punkti kasutada kõike, mis Tal sees on
        System.out.println(modelMapper);
        log.info(modelMapper);  //
        System.out.println("VIGA");
        log.error("VIGA!!");  // näeb ilusti ära, kus on olemas viga consolis ilusti näha, kus see asub

      return persons.stream().map(e -> modelMapper.map(e, PersonDto.class)).collect(Collectors.toList());
      // avan andmebaasist; saan modeleerida; saan anda ükskõik millise nime;
//        map. -> saan teisendada,

//        for(Person p: persons) {
//            modelMapper.map(p, new PersonDto());
//        }
//        modelMapper.map(persons, personDtos);

    }


    @GetMapping("person/{id}")
    public Person getPerson(@PathVariable Long id) {
        return personRepository.findById(id).get();
    }

    @DeleteMapping("person/{id}")
    public List<Person> deletePerson(@PathVariable Long id) {
        personRepository.deleteById(id);
        return personRepository.findAll();
    }

    @PostMapping("person")
    public List<Person> addPerson(@RequestBody Person person) {
        if(person.getId() == null || !personRepository.existsById(person.getId())) {
            personRepository.save(person);
        }
        return personRepository.findAll();
    }
    
    @PutMapping("person")
    public List<Person> editPerson(@RequestBody Person person) {
        if(personRepository.existsById(person.getId())) {
            personRepository.save(person);
        }
        return personRepository.findAll();
    }

}
