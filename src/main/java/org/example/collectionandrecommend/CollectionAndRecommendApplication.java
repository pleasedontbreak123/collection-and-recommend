package org.example.collectionandrecommend;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@EnableAspectJAutoProxy
@SpringBootApplication
public class CollectionAndRecommendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollectionAndRecommendApplication.class, args);
    }

}
