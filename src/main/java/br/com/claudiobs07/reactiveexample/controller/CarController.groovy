package br.com.claudiobs07.reactiveexample.controller

import br.com.claudiobs07.reactiveexample.domain.Car
import br.com.claudiobs07.reactiveexample.domain.CarEvents
import br.com.claudiobs07.reactiveexample.service.FluxCarService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Slf4j
@CompileStatic
@RestController
@RequestMapping("/cars")
class CarController {

    @Autowired
    FluxCarService fluxCarService

    @PostMapping
    Mono<Car> create(@RequestBody Car car) {
        log.info("creating car=${car}")
        fluxCarService.create(car)
    }

    @GetMapping("/{carId}")
    Mono<Car> byId(@PathVariable String carId) {
        log.info("getting car by id=${carId}")
        fluxCarService.getById(carId)
    }

    @GetMapping
    Flux<Car> all() {
        log.info("getting all cars")
        fluxCarService.getAll()
    }

    @GetMapping(value = "{carId}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<CarEvents> eventsOfStreams(@PathVariable String carId) {
        fluxCarService.getStreams(carId)
    }
}
