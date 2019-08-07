package com.softwood.arango

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
class ArangoApplication {

    static void main(String[] args) {
        //SpringApplication.run(ArangoApplication, args)

        Object[] runner = [CrudRunner.class]  //new Object[]
        System.exit(SpringApplication.exit(SpringApplication.run(CrudRunner.class/*runner*/, args)))

    }

}
