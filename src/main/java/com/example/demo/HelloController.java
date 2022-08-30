package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

   @GetMapping("/hello")
   public String index(@RequestParam String name, @RequestParam(required = false) String lastName) {
      if (lastName == null) lastName = "";
      return "Welcome " + name + " " + lastName;
   }


}