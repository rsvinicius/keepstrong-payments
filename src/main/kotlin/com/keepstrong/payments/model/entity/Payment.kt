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
    private val id: Long,

    @Positive
    @NotNull
    private val value: BigDecimal,

    @NotBlank
    @Size(max = 100)
    private val name: String,

    @NotBlank
    @Size(max = 19)
    private val cardNumber: String,

    @NotBlank
    @Size(max = 7)
    private val expirationDate: String,

    @NotBlank
    @Size(min= 3, max = 3)
    private val cardCode: String,

    @NotNull
    @Enumerated(EnumType.STRING)
    private val status: Status,

    @NotNull
    private val orderId: Long,

    @NotNull
    private val paymentMethodId: Long
)