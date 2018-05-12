package br.com.claudiobs07.reactiveexample.domain

import org.springframework.data.mongodb.core.mapping.Document

@Document
class Car {

    String id
    String model
}
