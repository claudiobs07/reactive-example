package br.com.claudiobs07.reactiveexample.service

import br.com.claudiobs07.reactiveexample.domain.Car
import br.com.claudiobs07.reactiveexample.domain.CarEvents
import br.com.claudiobs07.reactiveexample.repository.CarRepository
import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

import java.time.Duration
import java.time.LocalDate
import java.util.stream.Stream

@Service
@CompileStatic
class FluxCarService {

    @Autowired
    CarRepository carRepository

    Mono<Car> create(Car car) {
        carRepository.save(car)
    }

    Mono<Car> getById(String carId) {
        carRepository.findById(carId)
    }

    Flux<Car> getAll() {
        carRepository.findAll()
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    Flux<CarEvents> getStreams(String carId) {
        getById(carId).flatMapMany { car ->
            Flux<Long> interval = Flux.interval(Duration.ofSeconds(1))
            Flux<CarEvents> events = Flux.fromStream(
                    Stream.generate { new CarEvents(car: car, createdAt: LocalDate.now()) }) as Flux<CarEvents>
            Flux.zip(interval, events).map { it } as CarEvents
        } as Flux<CarEvents>
    }

}
