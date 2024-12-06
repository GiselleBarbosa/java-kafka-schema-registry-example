package br.com.barbosa.example

import br.com.barbosa.example.entity.Pessoa
import br.com.barbosa.example.producer.PessoaProducerImpl
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.UUID

@SpringBootApplication
class JavaKafkaSchemaRegistryExampleApplication(
    val pessoaProducerImpl: PessoaProducerImpl
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        val pessoa = Pessoa("Giselle", "Barbosa")
        Thread.sleep(7000)
        pessoaProducerImpl.enviarMensagem(UUID.randomUUID().toString(), pessoa);
    }
}

fun main(args: Array<String>) {
    runApplication<JavaKafkaSchemaRegistryExampleApplication>(*args)
}
