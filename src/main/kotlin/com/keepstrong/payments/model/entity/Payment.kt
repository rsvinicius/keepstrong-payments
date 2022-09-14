package com.keepstrong.payments.model.entity

import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

@Entity
@Table(name = "payments")
data class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

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
    var status: Status,

    @NotNull
    val orderId: Long,

    @NotNull
    val paymentMethodId: Long
)