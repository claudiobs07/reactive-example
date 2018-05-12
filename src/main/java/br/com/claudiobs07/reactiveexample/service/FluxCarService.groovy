package br.com.claudiobs07.reactiveexample.service

import br.com.claudiobs07.reactiveexample.domain.Car
import br.com.claudiobs07.reactiveexample.domain.CarEvents
import br.com.claudiobs07.reactiveexample.repository.CarRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

import java.time.Duration
import java.time.LocalDate
import java.util.stream.Stream

@Service
class FluxCarService {

    @Autowired
    CarRepository carRepository

    Flux<Car> getAll() {
        carRepository.findAll()
    }

    Mono<Car> getById(String carId) {
        carRepository.findById(carId)
    }

    Flux<CarEvents> getStreams(String carId) {
        getById(carId).flatMapMany {
            Flux<Long> interval = Flux.interval(Duration.ofSeconds(1))
            Flux<CarEvents> events = Flux.fromStream(
                    Stream.generate { new CarEvents(car: car, createdAt: LocalDate.now()) })
            Flux.zip(interval, events).map(Tuple2.getT2(it))
        } as Flux<CarEvents>
    }


}
