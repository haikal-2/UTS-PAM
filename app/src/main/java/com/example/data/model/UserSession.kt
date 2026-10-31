package com.example.data.model

data class UserProfile(
    val fullName: String = "Haikal",
    val username: String = "haikal",
    val email: String = "haikal@example.com",
    val phoneNumber: String = "081234567890",
    val memberSince: String = "Oktober 2026",
    val loyaltyPoints: Int = 150
)

data class DeliveryAddress(
    val recipientName: String = "",
    val address: String = "",
    val landmark: String = ""
)

data class CartItem(
    val item: MenuItem,
    val quantity: Int
)

data class PlacedOrder(
    val orderId: String,
    val customerName: String,
    val address: DeliveryAddress,
    val items: List<CartItem>,
    val subtotal: Long,
    val deliveryFee: Long = 10000,
    val total: Long,
    val timestamp: Long = System.currentTimeMillis()
)
