package br.com.barbosa.example.producer

import br.com.barbosa.example.entity.Pessoa
import br.com.barbosa.example.entity.PessoaDTO
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class PessoaProducerImpl(private val pessoaTemplate: KafkaTemplate<String, PessoaDTO>) {

    val topicName = "Pessoa"

    fun enviarMensagem(messageId: String, payload: Pessoa) {

        val dto = createDTO(payload);
        enviarPessoaMensagem(messageId, dto);
    }

    private fun enviarPessoaMensagem(messageId: String, dto: PessoaDTO) {

        val message = criaMensagemComHeaders(messageId, dto, topicName)

        pessoaTemplate.send(message).whenComplete { result, exception ->
            if (exception != null) {
                println("Erro no envio. MessageId $messageId, Erro: ${exception.message}")
            } else {
                println("Pessoa enviada com sucesso. MessageId $messageId, Offset: ${result?.recordMetadata?.offset()}")
            }
        }
    }

    private fun createDTO(payload: Pessoa): PessoaDTO {
        return PessoaDTO.newBuilder()
            .setNome(payload.nome)
            .setSobrenome(payload.sobrenome)
            .build()
    }

    private fun criaMensagemComHeaders(messageId: String, pessoaDTO: PessoaDTO, topic: String): Message<PessoaDTO> {
        return MessageBuilder.withPayload(pessoaDTO)
            .setHeader("hash", pessoaDTO.hashCode())
            .setHeader("version", "1.0.0")
            .setHeader("endOfLife", LocalDate.now().plusDays(1))
            .setHeader("type", "Pessoa")
            .setHeader("cid", messageId)
            .setHeader("hashCode", pessoaDTO.hashCode())
            .setHeader("timeStamp", System.currentTimeMillis().toString())
            .setHeader(KafkaHeaders.TOPIC, topic)
            .setHeader(KafkaHeaders.CORRELATION_ID, messageId)
            .build()

    }
}
