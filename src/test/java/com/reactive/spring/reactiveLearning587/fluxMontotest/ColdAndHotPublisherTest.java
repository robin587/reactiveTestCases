package com.reactive.spring.reactiveLearning587.fluxMontotest;

import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.util.BsonUtils;
import reactor.core.Disposable;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class ColdAndHotPublisherTest {

    @Test
    void coldPublisher_NotATest() throws InterruptedException {

        Flux<String> flux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofSeconds(1));

        flux.subscribe( e -> System.out.println("Subscriber 1:"+e));
        flux.subscribe( e -> System.out.println("Subscriber 2:"+e));

        Thread.sleep(4000);
    }

    @Test
    void hotPublisher_NotATest() throws InterruptedException {
        Flux<String> flux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofSeconds(1));

        ConnectableFlux<String> publish1 = flux.publish();
        publish1.connect();
        publish1.subscribe( e -> System.out.println("Subscriber 1:"+e));

        Thread.sleep(3000);
        publish1.subscribe( e -> System.out.println("Subscriber 2:"+e));


        Thread.sleep(4000);
    }
}
