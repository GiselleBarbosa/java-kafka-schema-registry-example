package br.com.barbosa.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JavaKafkaSchemaRegistryExampleApplication

fun main(args: Array<String>) {
    runApplication<JavaKafkaSchemaRegistryExampleApplication>(*args)
}
