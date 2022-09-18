package com.keepstrong.payments.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.keepstrong.payments.model.Status
import com.keepstrong.payments.model.dto.PaymentDto
import com.keepstrong.payments.model.entity.Payment
import com.keepstrong.payments.repository.PaymentRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val objectMapper: ObjectMapper
) {

    fun getAllPayments(pageable: Pageable): Page<PaymentDto> {
        return paymentRepository
            .findAll(pageable)
            .map { p -> objectMapper.convertValue(p, PaymentDto::class.java) }
    }

    fun getPaymentById(id: Long): PaymentDto {
        val payment = paymentRepository.findById(id).orElseThrow { EntityNotFoundException() }

        return objectMapper.convertValue(payment, PaymentDto::class.java)
    }

    fun createPayment(paymentDto: PaymentDto): PaymentDto {
        val payment = objectMapper.convertValue(paymentDto, Payment::class.java).apply {
            status = Status.CREATED
        }

        paymentRepository.save(payment)

        return objectMapper.convertValue(payment, PaymentDto::class.java)
    }

    fun updatePayment(id: Long, paymentDto: PaymentDto): PaymentDto {
        val payment = objectMapper.convertValue(paymentDto, Payment::class.java).apply {
            this.id = paymentDto.id
        }

        paymentRepository.save(payment)

        return objectMapper.convertValue(payment, PaymentDto::class.java)
    }

    fun deletePayment(id: Long) {
        paymentRepository.deleteById(id)
    }
}