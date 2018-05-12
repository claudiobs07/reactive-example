package br.com.claudiobs07.reactiveexample.repository

import br.com.claudiobs07.reactiveexample.domain.Car
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CarRepository extends ReactiveMongoRepository<Car, String>{

}