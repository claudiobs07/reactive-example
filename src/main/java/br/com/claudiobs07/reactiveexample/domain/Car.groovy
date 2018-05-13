package br.com.claudiobs07.reactiveexample.domain

import groovy.transform.ToString
import org.springframework.data.mongodb.core.mapping.Document

@Document
@ToString
class Car {

    String id
    String model
}
