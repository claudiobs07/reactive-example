package br.com.claudiobs07.reactiveexample.component

import br.com.claudiobs07.reactiveexample.domain.Car
import br.com.claudiobs07.reactiveexample.repository.CarRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Slf4j
@Component
class DummyData implements CommandLineRunner {

    static final String[]  carModels = [ "Koenigsegg One:1", "Hennessy Venom GT", "Bugatti Veyron Super Sport",
                "SSC Ultimate Aero", "McLaren F1", "Pagani Huayra", "Noble M600",
                "Aston Martin One-77", "Ferrari LaFerrari", "Lamborghini Aventador" ]

    @Autowired
    CarRepository carRepository

    @Override
    void run(String... args) {
        carRepository.deleteAll().thenMany {
            Flux.just(carModels)
                .map { new Car(id: UUID.randomUUID().toString(), model: it) }
                .flatMap { Car car -> carRepository.save(car) }
                .subscribe { log.info(it as String) }
        }
    }
}
