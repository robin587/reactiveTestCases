package com.reactive.spring.reactiveLearning587.fluxMontotest;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoErrorTest {

    @Test
    void fluxWithException() {

        Flux<String> fluxWithException = Flux.just("Hello", "World")
                .concatWith(Flux.error(new RuntimeException("run time exception")))
                .concatWith(Flux.just("Its me"));

        StepVerifier.create(fluxWithException.log())
                .expectSubscription()
                .expectNext("Hello","World")
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void fluxErrorHandling() {

        Flux<String> fluxWithException = Flux.just("Hello", "World")
                .concatWith(Flux.error(new RuntimeException("run time exception")))
                .concatWith(Flux.just("Its me"))
                .onErrorResume( exception -> {
                    System.err.println("Exception occured:"+exception);
                    return Flux.just("onErrorCaught");
                });

        StepVerifier.create(fluxWithException.log())
                .expectSubscription()
                .expectNext("Hello","World")
                .expectNext("onErrorCaught")
                .verifyComplete();
    }

    @Test
    void fluxErrorHandling_OnErrorReturn() {

        Flux<String> fluxWithException = Flux.just("Hello", "World")
                .concatWith(Flux.error(new RuntimeException("run time exception")))
                .concatWith(Flux.just("Its me"))
                .onErrorReturn("onErrorReturn");

        StepVerifier.create(fluxWithException.log())
                .expectSubscription()
                .expectNext("Hello","World")
                .expectNext("onErrorReturn")
                .verifyComplete();
    }

    @Test
    void fluxErrorHandling_OnErrorReturn_withRetry() {
        Flux<String> fluxWithException = Flux.just("Hello", "World")
                .concatWith(Flux.error(new RuntimeException("run time exception")))
                .concatWith(Flux.just("Its me"))
                .retryBackoff(2,Duration.ofSeconds(2))
                .onErrorReturn("returned Error");

        StepVerifier.create(fluxWithException.log())
                .expectSubscription()
                .expectNext("Hello","World")
                .expectNext("Hello","World")
                .expectNext("Hello","World")
                .expectNext("returned Error")
                .verifyComplete();
    }

    @Test
    void fluxErrorHandling_OnErrorMap_withRetry() {
        Flux<String> fluxWithException = Flux.just("Hello", "World")
                .concatWith(Flux.error(new RuntimeException("run time exception")))
                .concatWith(Flux.just("Its me"))
                .onErrorMap( e -> new MyNewException(e));

        StepVerifier.create(fluxWithException.log())
                .expectSubscription()
                .expectNext("Hello","World")
                .expectError(MyNewException.class)
                .verify();
    }


}
