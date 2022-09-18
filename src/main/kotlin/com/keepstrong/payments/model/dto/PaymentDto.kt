package com.keepstrong.payments.model.dto

import com.keepstrong.payments.model.Status
import java.math.BigDecimal
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

data class PaymentDto(
    val id: Long,
    val value: BigDecimal,
    val name: String,
    val cardNumber: String,
    val expirationDate: String,
    val cardCode: String,
    var status: Status?,
    val orderId: Long,
    val paymentMethodId: Long
)