package com.reactive.spring.reactiveLearning587.fluxMontotest;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;

public class VIrtualizeFluxTime {

    @Test
    void withoutVirtualTime() {
        Flux<Long> interval = Flux.interval(Duration.ofMillis(1000))
                .take(3);

        StepVerifier.create(interval.log())
                .expectSubscription()
                .expectNext(0L,1L,2L)
                .verifyComplete();
    }

    @Test
    void withVirtualTime() {
        VirtualTimeScheduler.getOrSet();

        Flux<Long> interval = Flux.interval(Duration.ofMillis(1000))
                .take(3);

        StepVerifier.withVirtualTime(() -> interval.log())
                .expectSubscription()
                .thenAwait(Duration.ofSeconds(3))
                .expectNext(0L, 1L, 2L)
                .verifyComplete();
    }

}
