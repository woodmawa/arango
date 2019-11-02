package com.softwood.arango
import org.springframework.context.annotation.Configuration
import com.arangodb.ArangoDB
import com.arangodb.ArangoDB.Builder;
import com.arangodb.springframework.annotation.EnableArangoRepositories;
import com.arangodb.springframework.config.AbstractArangoConfiguration;

@Configuration
@EnableArangoRepositories(basePackages = [ "com.softwood.arango" ])
//class ArangoConfiguration extends AbstractArangoConfiguration {  //changed since 3.2.0
class ArangoConfiguration implements com.arangodb.springframework.config.ArangoConfiguration {
//class ArangoConfiguration  {


    @Override
    public Builder arango() {
        return new ArangoDB.Builder().host("localhost", 8529).user("root").password(null)
    }

    @Override
    public String database() {
        return "testDB"
    }
}
