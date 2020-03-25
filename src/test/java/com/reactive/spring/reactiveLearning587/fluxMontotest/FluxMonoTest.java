package com.reactive.spring.reactiveLearning587.fluxMontotest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@ExtendWith(MockitoExtension.class)
public class FluxMonoTest {

    @Test
    void fluxTest() {

        Flux<String> healthWatchEvents = Flux.just("Rx", "Tx", "Ax")
                .concatWith(Flux.just("Allergy"));
              //  .concatWith(Flux.error(new RuntimeException("runtime exception")));

        healthWatchEvents
                .log()
                .subscribe(element -> System.out.println("Element from flux:"+element),
                        error -> System.err.println("Error received:"+error),
                        () -> System.out.println("Execution completed"));
    }

    @Test
    void givenNElements_whenNElemenetsPublished_ThenCheckElementSizeAndIfExecutionCompleted() {

        Flux<String> fluxElements = Flux.just("Spring", "Spring Boot", "Reactor Spring")
                .log();

        StepVerifier.create(fluxElements)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void givenPublisher_whenErrorOccured_verifyError() {
        String exceptionMessage = "runtime Exception";
        Flux<String> fluxElements = Flux.just("Spring", "Spring Boot", "Reactor Spring")
                .concatWith(Flux.error(new RuntimeException(exceptionMessage)))
                .log();

        StepVerifier.create(fluxElements)
                .expectNextCount(3)
                .expectErrorMessage(exceptionMessage)
                .verify();

    }

    @Test
    void givenPublisher_whenElementsPublished_checkElementSequence() {

        String[] publishedElements = {"Rx", "Tx", "Cx"};
        Flux<String> fluxElements = Flux.just(publishedElements);

        StepVerifier.create(fluxElements.log())
                .expectNext(publishedElements)
                .verifyComplete();
    }

    @Test
    void givenMonoPublisher_checkMonoElement() {

        String expectedMonoElement = "one Element";
        Mono<String> monoElement = Mono.just(expectedMonoElement);

        StepVerifier.create(monoElement.log())
                .expectNext(expectedMonoElement)
                .verifyComplete();
    }

    @Test
    void givenMonoPublisher_checkError() {

        Mono<Mono<Object>> monoError = Mono.error(new RuntimeException("run time exception"));

        StepVerifier.create(monoError.log())
                .expectError(RuntimeException.class)
                .verify();

    }

    @Test
    void fluxWithIterable() {
        List<String> stringList = Arrays.asList("Hello", "World", "Good Evening");
        Flux<String> stringFlux = Flux.fromIterable(stringList);

        StepVerifier.create(stringFlux.log())
                .expectNext("Hello", "World", "Good Evening")
                .verifyComplete();
    }

    @Test
    void fluxWithStreams() {
        List<String> stringList = Arrays.asList("Hello", "World", "Good Evening");
        Flux<String> stringFlux = Flux.fromStream(stringList.stream());
        StepVerifier.create(stringFlux.log())
                .expectNext("Hello", "World", "Good Evening")
                .verifyComplete();
    }

    @Test
    void monoWithSupplier() {
        Supplier<String> supplier = () -> "Hello";

        Mono<String> stringMono = Mono.fromSupplier(supplier);

        StepVerifier.create(stringMono)
                .expectNext("Hello")
                .verifyComplete();
    }

    @Test
    void fluxWithRange() {
        Flux<Integer> rangeFlux = Flux.range(2, 5);

        StepVerifier.create(rangeFlux.log())
                .expectNext(2,3,4,5,6 )
                .verifyComplete();
    }


}
