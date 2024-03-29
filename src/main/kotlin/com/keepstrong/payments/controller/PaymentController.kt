package com.keepstrong.payments.controller

import com.keepstrong.payments.model.dto.PaymentDto
import com.keepstrong.payments.service.PaymentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@Tag(name = "PaymentController")
@RestController
@Validated
@RequestMapping("/payments")
class PaymentController(
        private val paymentService: PaymentService,
        private val rabbitTemplate: RabbitTemplate
) {
    @Operation(summary = "List all payments")
    @ApiResponses(
            ApiResponse(responseCode = "200", description = "All payments listed"),
    )
    @GetMapping
    fun listAllPayments(@PageableDefault(size = 10) pageable: Pageable): Page<PaymentDto> {
        return paymentService.getAllPayments(pageable)
    }

    @Operation(summary = "Get payment by id")
    @ApiResponses(
            ApiResponse(responseCode = "200", description = "Payment found"),
            ApiResponse(responseCode = "404", description = "Payment not found")
    )
    @GetMapping("/{id}")
    fun getPaymentById(@PathVariable @NotNull id: Long): ResponseEntity<PaymentDto> {
        val paymentDto = paymentService.getPaymentById(id)

        return ResponseEntity.ok(paymentDto)
    }

    @Operation(summary = "Create payment")
    @ApiResponses(
            ApiResponse(responseCode = "200", description = "Payment created")
    )
    @PostMapping
    fun registerPayment(
            @RequestBody @Valid paymentDto: PaymentDto,
            uriBuilder: UriComponentsBuilder
    ): ResponseEntity<PaymentDto> {
        val createdPaymentDto = paymentService.createPayment(paymentDto)
        val address = uriBuilder.path("/payments/{id}").buildAndExpand(createdPaymentDto.id).toUri()

        rabbitTemplate.convertAndSend("payment.ex", "", createdPaymentDto)

        return ResponseEntity.created(address).body(createdPaymentDto)
    }

    @Operation(summary = "Update payment")
    @ApiResponses(
            ApiResponse(responseCode = "200", description = "Payment updated")
    )
    @PutMapping("/{id}")
    fun updatePayment(
            @PathVariable @NotNull id: Long,
            @RequestBody @Valid paymentDto: PaymentDto
    ): ResponseEntity<PaymentDto> {
        val updatedPaymentDto = paymentService.updatePayment(id, paymentDto)

        return ResponseEntity.ok(updatedPaymentDto)
    }

    @Operation(summary = "Delete payment")
    @ApiResponses(
            ApiResponse(responseCode = "204", description = "Payment deleted")
    )
    @DeleteMapping("/{id}")
    fun deletePayment(@PathVariable @NotNull id: Long): ResponseEntity<PaymentDto> {
        paymentService.deletePayment(id)

        return ResponseEntity.noContent().build()
    }

    @Operation(summary = "Confirm payment")
    @ApiResponses(
            ApiResponse(responseCode = "200", description = "Payment confirmed")
    )
    @PatchMapping("/{id}/confirm")
    fun confirmPayment(
            @PathVariable @NotNull id: Long
    ): ResponseEntity<Unit> {
        paymentService.confirmPayment(id)

        return ResponseEntity.ok().build()
    }

    @GetMapping("/port")
    fun getPort(
            @Value("\${local.server.port}")
            port: String
    ): String {
        return String.format("Request served by the instance running on the port %s", port)
    }
}