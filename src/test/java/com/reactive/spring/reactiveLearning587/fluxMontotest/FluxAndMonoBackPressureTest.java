package com.reactive.spring.reactiveLearning587.fluxMontotest;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoBackPressureTest {

    @Test
    void backPressureTest() {
        Flux<Integer> range = Flux.range(1, 10);

        StepVerifier.create(range.log())
                .expectSubscription()
                .thenRequest(1)
                .expectNext(1)
                .thenRequest(1)
                .expectNext(2)
                .thenCancel()
                .verify();
    }

    @Test
    void backPressureNotATest() {
        Flux<Integer> finiteFlux = Flux.range(1, 10);

        finiteFlux.subscribe( v -> System.out.println("Element:"+v)
        , error -> System.err.println("Exception Occured:"+error)
        , () -> System.out.println("Completed")
        , s -> s.request(5));
    }

    @Test
    void backPressure_Cancel_NotATest() {
        Flux<Integer> finiteFlux = Flux.range(1, 10);

        finiteFlux.subscribe( v -> System.out.println("Element:"+v)
                , error -> System.err.println("Exception Occured:"+error)
                , () -> System.out.println("Completed")
                , s -> s.cancel());
    }

    @Test
    void customizedBackPressure() {
        Flux<Integer> finiteFlux = Flux.range(1, 10);

        finiteFlux.subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnNext(Integer value) {
                System.out.println("Element:"+value);
                if ( value == 6){
                    cancel();
                }
            }
        });
    }
}
