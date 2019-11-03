package com.softwood.arango

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

import java.lang.reflect.Array

@SpringBootApplication
class ArangoApplication {

    static void main(String[] args) {
        //SpringApplication.run(ArangoApplication, args)

        Class[] runners = Arrays.asList (CrudRunner.class, RelationsRunner.class, QueryRunner.class)  //new Object[]
        System.exit(SpringApplication.exit(SpringApplication.run(runners, args)))

    }

}
