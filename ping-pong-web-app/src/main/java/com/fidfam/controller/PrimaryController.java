package com.fidfam.controller;

import com.fidfam.model.TestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Josh on 4/28/17.
 */
@RestController
public class PrimaryController {

    @Autowired
    public TestModel model;

    @RequestMapping("/hello")
    public TestModel index(){
        return model;
    }
}
