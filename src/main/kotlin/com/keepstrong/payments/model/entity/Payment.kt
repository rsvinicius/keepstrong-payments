package com.keepstrong.payments.model.entity

import com.keepstrong.payments.model.Status
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import java.math.BigDecimal

@Entity
@Table(name = "payments")
data class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Positive
    @NotNull
    val value: BigDecimal,

    @NotBlank
    @Size(max = 100)
    val name: String,

    @NotBlank
    @Size(max = 19)
    val cardNumber: String,

    @NotBlank
    @Size(max = 7)
    val expirationDate: String,

    @NotBlank
    @Size(min= 3, max = 3)
    val cardCode: String,

    @NotNull
    @Enumerated(EnumType.STRING)
    var status: Status?,

    @NotNull
    val orderId: Long,

    @NotNull
    val paymentMethodId: Long
)