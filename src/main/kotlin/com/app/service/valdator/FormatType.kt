package com.app.service.valdator

enum class FormatType {
    EMAIL,
    URL,
    DATE,
    TIME,
    DATETIME,
    IPV4,
    IPV6,
    UUID,
    HEX,
    BASE64,
    CUSTOM // For custom regex patterns
} 