package com.morocco.mpt.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainPage {

    @GetMapping("/")
    public String Hello(){
        return ("Welcome to MPT-PLUS");
    }
}
