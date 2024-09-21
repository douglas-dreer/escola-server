package br.com.escola.server.person;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public class PeopleRest {

    @GetMapping("/name")
    public String returnName(){
        return "nome da pessoa";
    }

}
