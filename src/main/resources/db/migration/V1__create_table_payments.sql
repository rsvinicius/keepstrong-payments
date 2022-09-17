CREATE TABLE payments
(
    id              bigint(20) NOT NULL AUTO_INCREMENT,
    value           decimal(19, 2) NOT NULL,
    name            varchar(100) DEFAULT NULL,
    cardNumber      varchar(19)  DEFAULT NULL,
    expirationDate  varchar(7)   DEFAULT NULL,
    cardCode        varchar(3)   DEFAULT NULL,
    status          varchar(255)   NOT NULL,
    paymentMethodId bigint(20) NOT NULL,
    orderId         bigint(20) NOT NULL,
    PRIMARY KEY (id)
);