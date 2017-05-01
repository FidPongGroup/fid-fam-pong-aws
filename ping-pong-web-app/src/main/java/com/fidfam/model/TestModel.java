package com.fidfam.model;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by Josh on 4/28/17.
 */
@lombok.Value
@Component
public class TestModel {

    //@org.springframework.beans.factory.annotation.Value("${tacos}")
    private  String tacos;

    //@org.springframework.beans.factory.annotation.Value("${rule}")
    private  String rule;

    public TestModel(@Value("${tacos}") String tacos, @Value("${rule}")String rule) {
        this.tacos = tacos;
        this.rule = rule;
    }
}
