package com.reactive.spring.reactiveLearning587.fluxMontotest;


import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxAndMonoTransformTest {

    private List<String> nameList = Arrays.asList("Jack", "Adam", "Jenny");

    @Test
    void fluxWithMap() {
        Flux<String> fluxMap = Flux.fromStream(nameList.stream())
                .map(e -> e.toUpperCase())
                .log();

        StepVerifier.create(fluxMap)
                .expectNext("JACK","ADAM","JENNY")
                .verifyComplete();
    }

    @Test
    void fluxWithMapandRepeat() {
        Flux<Integer> repeat = Flux.fromIterable(nameList)
                .map(e -> e.length())
                .repeat(1)
                .log();

        StepVerifier.create(repeat)
                .expectNext(4,4,5,4,4,5)
                .verifyComplete();
    }

    @Test
    void fluxWithMapAndFilter() {

        Flux<String> map = Flux.fromIterable(nameList)
                .filter(e -> e.length() > 4)
                .map(e -> e.toUpperCase())
                .log();

        StepVerifier.create(map)
                .expectNext("JENNY")
                .verifyComplete();
    }

    @Test
    void fluxWithFlatMap() {
        List<String> stringList = Arrays.asList("A", "B", "C", "D", "E", "F");

        Flux<String> stringFlux = Flux.fromIterable(stringList)
                .flatMap(s -> Flux.fromIterable(getCollectionOfElements(s)))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete();
    }

    @Test
    void fluxWithflatMapParallel() {
        List<String> stringList = Arrays.asList("A", "B", "C", "D", "E", "F");

        /*Flux.fromIterable(stringList)
                .window(2) //Fulx<Flux<String>>
                .flatMapSequential( fluxOfStringElement -> fluxOfStringElement.map(this::getCollectionOfElements).subscribeOn());*/

    }

    private List<String> getCollectionOfElements(String s) {
        try{
            Thread.sleep(1000);
        }catch (InterruptedException exception){
            exception.printStackTrace();
        }
        return Arrays.asList(s,"newValue");
    }
}
