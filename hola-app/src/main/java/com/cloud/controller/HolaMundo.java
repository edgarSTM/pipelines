package com.cloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaMundo {

	    @GetMapping("/hola")
	    public String saludar() {
	        return "Hola!! Accedio con las claves correctas! 🚀";
	    }
}
