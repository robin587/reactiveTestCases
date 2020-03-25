package com.reactive.spring.reactiveLearning587.fluxMontotest;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxMonoFilterTest {

    @Test
    void fluxWithStreamFilter() {
        List<String> stringList = Arrays.asList("jack", "adam", "jenny");
        Flux<String> filterFlux = Flux.fromIterable(stringList)
                .filter(e -> e.startsWith("j"));

        StepVerifier.create(filterFlux.log())
                .expectNext("jack","jenny")
                .verifyComplete();
    }
}
