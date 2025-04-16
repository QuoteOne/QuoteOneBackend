package com.app.service.calculator


// 定義長度單位的 Enum 類別
enum class LengthUnit(val conversionFactor: Double) {
    CAI(30.3),        // 1才 = 30.3公分
    CHI(30.3),        // 1尺 = 30.3公分
    CUN(3.03),        // 1寸 = 3.03公分
    CENTIMETER(1.0),  // 1公分 = 1公分
    MILLIMETER(0.1),  // 1毫米 = 0.1公分
    METER(100.0),     // 1米 = 100公分
    KILOMETER(100000.0); // 1公里 = 100,000公分

    // 用於長度單位轉換，轉換為公分
    fun convertToCentimeters(value: Double): Double {
        return value * conversionFactor
    }
}

// 定義面積單位的 Enum 類別
enum class AreaUnit(val conversionFactor: Double) {
    PING(330.6),      // 1坪 = 330.6公分
    SQUARE_CENTIMETER(1.0),  // 1平方公分 = 1平方公分
    SQUARE_METER(10000.0),   // 1平方公尺 = 10,000平方公分
    SQUARE_FEET(929.03), // 1平方英尺 = 929.03平方公分
    SQUARE_YARD(836.1), // 1平方碼 = 836.1平方公分
    TAIWAN_PING(3.3058), // 1市坪 = 3.3058平方米 = 3,305.8平方公分
    MU(666.7);        // 1畝 ≈ 666.7平方米 = 666,700平方公分

    // 用於面積單位轉換，轉換為平方公分
    fun convertToCentimeterSquare(value: Double): Double {
        return value * conversionFactor * conversionFactor
    }
}