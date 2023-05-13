package com.keepstrong.payments.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.keepstrong.payments.client.OrdersClient
import com.keepstrong.payments.model.Status
import com.keepstrong.payments.model.dto.PaymentDto
import com.keepstrong.payments.model.entity.Payment
import com.keepstrong.payments.repository.PaymentRepository
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val objectMapper: ObjectMapper,
    private val ordersClient: OrdersClient
) {

    fun getAllPayments(pageable: Pageable): Page<PaymentDto> {
        return paymentRepository
            .findAll(pageable)
            .map { p -> convertPaymentToPaymentDto(p) }
    }

    fun getPaymentById(id: Long): PaymentDto {
        val payment = findPaymentById(id)

        return convertPaymentToPaymentDto(payment)
    }

    fun createPayment(paymentDto: PaymentDto): PaymentDto {
        val payment = convertPaymentDtoToPayment(paymentDto).apply {
            status = Status.CREATED
        }

        paymentRepository.save(payment)

        return convertPaymentToPaymentDto(payment)
    }

    fun updatePayment(id: Long, paymentDto: PaymentDto): PaymentDto {
        val payment = convertPaymentDtoToPayment(paymentDto).apply {
            this.id = paymentDto.id
        }

        paymentRepository.save(payment)

        return convertPaymentToPaymentDto(payment)
    }

    fun deletePayment(id: Long) {
        paymentRepository.deleteById(id)
    }

    @CircuitBreaker(name = "confirmPayment", fallbackMethod = "pendingAuthorizedPaymentIntegration")
    fun confirmPayment(id: Long) {
        val payment = findPaymentById(id).apply {
            status = Status.CONFIRMED
        }

        paymentRepository.save(payment)

        ordersClient.approveOrderPayment(payment.orderId)
    }

    fun pendingAuthorizedPaymentIntegration(id: Long, throwable: Throwable) {
        val payment = findPaymentById(id).apply {
            status = Status.CONFIRMED_WITHOUT_INTEGRATION
        }

        paymentRepository.save(payment)
    }

    private fun findPaymentById(id: Long): Payment =
        paymentRepository.findById(id).orElseThrow { EntityNotFoundException() }

    private fun convertPaymentDtoToPayment(paymentDto: PaymentDto): Payment =
        objectMapper.convertValue(paymentDto, Payment::class.java)

    private fun convertPaymentToPaymentDto(payment: Payment): PaymentDto =
        objectMapper.convertValue(payment, PaymentDto::class.java)
}