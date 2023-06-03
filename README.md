# Keepstrong-Payments

# Table of Contents
1. [Overview](#overview)
2. [Requirements](#requirements)
3. [Usage](#usage)
4. [API Reference](#api-reference)
5. [License](#license)

## Overview

The Payment Service is responsible for managing payment transactions and processing payments for orders within the [keepstrong-delivery](https://github.com/rsvinicius/keepstrong-delivery) project. It provides endpoints to create, retrieve, update, and delete payment information. **Please note that this is a sample implementation without actual payment integration.**

## Requirements

To successfully set up and run the Delivery Project, the following requirements must be met:
- Java 17+
- MySQL
- RabbitMQ
- Eureka Server
- Eureka Gateway
- IntelliJ IDEA / Netbeans / Eclipse

## Usage

Please make sure to refer to the [keepstrong-delivery](https://github.com/rsvinicius/keepstrong-delivery) documentation for detailed instructions on how to set up and start the service successfully.

## API Reference

The Payment Service exposes a set of RESTful APIs to manage payment transactions. Below are the available endpoints:

- GET `/payments`: Retrieve all payments information.
- POST `/payments`: Create a new payment transaction.
- GET `/payments/{paymentId}`: Retrieve payment information for a specific payment ID.
- PUT `/payments/{paymentId}`: Update payment information for a specific payment ID.
- DELETE `/payments/{paymentId}`: Delete payment information for a specific payment ID.
- PATCH `/payments/{paymentId}/confirm`: Confirm the specified payment transaction.

Please note that these endpoints are placeholders and do not perform actual payment processing. Modify the implementation to integrate with your desired payment gateway or service.

For detailed information on request and response formats for each endpoint, as well as example usage, refer to the swagger documentation.

## License

The Project is licensed under the MIT License, promoting open-source collaboration and allowing users to utilize, modify, and distribute the project with minimal restrictions. This license fosters transparency and encourages the growth of open-source software.