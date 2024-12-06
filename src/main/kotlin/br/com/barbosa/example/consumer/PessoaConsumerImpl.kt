package br.com.barbosa.example.consumer;

import br.com.barbosa.example.entity.Pessoa;
import br.com.barbosa.example.entity.PessoaDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import kotlin.concurrent.thread

@Component
public class PessoaConsumerImpl {

    @KafkaListener(topics = ["Pessoa"], groupId = "pessoa-consumer")

    fun consume(@Payload pessoaDTO: PessoaDTO) {

        Thread.sleep(15000)

        val pessoa = Pessoa(pessoaDTO.getNome().toString(), pessoaDTO.getSobrenome().toString())
        println("Pessoa recebida com sucesso: " + pessoa.toString())
    }
}