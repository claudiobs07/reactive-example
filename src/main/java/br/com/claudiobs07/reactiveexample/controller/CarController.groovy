package br.com.claudiobs07.reactiveexample.controller

import br.com.claudiobs07.reactiveexample.domain.Car
import br.com.claudiobs07.reactiveexample.domain.CarEvents
import br.com.claudiobs07.reactiveexample.service.FluxCarService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class CarController {

    @Autowired
    FluxCarService fluxCarService

    @GetMapping("/cars")
    Flux<Car> all() {
        return fluxCarService.getAll();
    }

    @GetMapping("/cars/{carId}")
    Mono<Car> byId(@PathVariable String carId) {
        return fluxCarService.getById(carId);
    }

    @GetMapping(value = "/cars/{carId}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<CarEvents> eventsOfStreams(@PathVariable String carId) {
        return fluxCarService.getStreams(carId);
    }
}
