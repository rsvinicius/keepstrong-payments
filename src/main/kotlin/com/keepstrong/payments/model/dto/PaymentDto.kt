package com.keepstrong.payments.model.dto

import com.keepstrong.payments.model.entity.Status
import java.math.BigDecimal

data class PaymentDto(
    private val id: Long,
    private val value: BigDecimal,
    private val name: String,
    private val cardNumber: String,
    private val expirationDate: String,
    private val cardCode: String,
    private val status: Status,
    private val orderId: Long,
    private val paymentMethodId: Long
)