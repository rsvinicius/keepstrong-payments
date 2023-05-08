package com.keepstrong.payments.client

import jakarta.validation.constraints.NotNull
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping

@FeignClient(
    name = "orders-ms",
    path = "/orders"
)
interface OrdersClient {
    @PutMapping("/{id}/paid")
    fun approveOrderPayment(
        @PathVariable @NotNull id: Long
    ): ResponseEntity<Unit>
}