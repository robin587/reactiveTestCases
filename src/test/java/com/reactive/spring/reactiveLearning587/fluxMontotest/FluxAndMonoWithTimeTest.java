package com.reactive.spring.reactiveLearning587.fluxMontotest;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.web.ServletTestExecutionListener;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoWithTimeTest {

    @Test
    void infiniteSequence() {

        Flux<Long> finiteFlux = Flux.interval(Duration.ofMillis(200))
                .take(3);

        StepVerifier.create(finiteFlux.log())
                .expectSubscription()
                .expectNext(0L,1L,2L)
                .verifyComplete();
    }

    @Test
    void infiniteSequenceMap() {

        Flux<String> finiteFlux = Flux.interval(Duration.ofMillis(200))
                .map( longValue -> String.valueOf(longValue))
                .take(3);

        StepVerifier.create(finiteFlux.log())
                .expectSubscription()
                .expectNext("0","1","2")
                .verifyComplete();
    }
}
