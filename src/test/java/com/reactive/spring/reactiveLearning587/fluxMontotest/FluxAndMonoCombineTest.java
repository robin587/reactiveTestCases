package com.reactive.spring.reactiveLearning587.fluxMontotest;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;

public class FluxAndMonoCombineTest {

    @Test
    void fluxWithMerge() {
        String[] strings = {"One", "Two", "Three"};
        String[] strings2 = {"Four", "Five", "Six"};

        Flux<String> fluxElements1 = Flux.just(strings);
        Flux<String> fluxElements2 = Flux.just(strings2);
        Flux<String> merge = Flux.merge(fluxElements1, fluxElements2);

        StepVerifier.create(merge.log())
                .expectSubscription()
                .expectNext("One", "Two", "Three","Four", "Five", "Six")
                .verifyComplete();
    }

    @Test
    void fluxWithMerge_WithDelay() {
        String[] strings = {"One", "Two", "Three"};
        String[] strings2 = {"Four", "Five", "Six"};

        Flux<String> fluxElements1 = Flux.just(strings).delayElements(Duration.ofSeconds(1));
        Flux<String> fluxElements2 = Flux.just(strings2).delayElements(Duration.ofSeconds(1));
        Flux<String> merge = Flux.merge(fluxElements1, fluxElements2);

        StepVerifier.create(merge.log())
                .expectSubscription()
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    void fluxWithConcat_WithDelay() {
        String[] strings = {"One", "Two", "Three"};
        String[] strings2 = {"Four", "Five", "Six"};

        Flux<String> fluxElements1 = Flux.just(strings).delayElements(Duration.ofSeconds(1));
        Flux<String> fluxElements2 = Flux.just(strings2).delayElements(Duration.ofSeconds(1));
        Flux<String> merge = Flux.concat(fluxElements1, fluxElements2);

        StepVerifier.create(merge.log())
                .expectSubscription()
                .expectNext("One", "Two", "Three","Four", "Five", "Six")
                .verifyComplete();
    }

    @Test
    void fluxWithZip() {
        String[] strings = {"One", "Two", "Three"};
        String[] strings2 = {"Four", "Five", "Six"};

        Flux<String> fluxElements1 = Flux.just(strings).delayElements(Duration.ofSeconds(1));
        Flux<String> fluxElements2 = Flux.just(strings2).delayElements(Duration.ofSeconds(1));
        Flux<String> zip = Flux.zip(fluxElements1, fluxElements2, (t1, t2) -> t1.concat(t2));

        StepVerifier.create(zip.log())
                .expectSubscription()
                .expectNext("OneFour","TwoFive","ThreeSix")
                .verifyComplete();
    }

}
