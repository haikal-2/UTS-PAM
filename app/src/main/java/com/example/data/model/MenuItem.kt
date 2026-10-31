package com.example.data.model

data class MenuItem(
    val id: String,
    val name: String,
    val category: String, // "Kopi", "Camilan"
    val description: String,
    val price: Long,
    val formattedPrice: String = "Rp ${String.format("%,d", price).replace(',', '.')}",
    val imageResId: Int? = null,
    val emoji: String = "☕"
)

object DummyData {
    val menuItems = listOf(
        MenuItem(
            id = "menu_1",
            name = "Kopi Susu Gula Aren",
            category = "Kopi",
            description = "Espresso, susu segar, gula aren murni",
            price = 22000,
            emoji = "☕"
        ),
        MenuItem(
            id = "menu_2",
            name = "Donat Cokelat",
            category = "Camilan",
            description = "Donat empuk kentang, glaze cokelat Belgia",
            price = 12000,
            emoji = "🍩"
        ),
        MenuItem(
            id = "menu_3",
            name = "Sourdough Loaf",
            category = "Camilan",
            description = "Roti fermentasi alami, kerak renyah lembut",
            price = 35000,
            emoji = "🍞"
        ),
        MenuItem(
            id = "menu_4",
            name = "Cappuccino",
            category = "Kopi",
            description = "Espresso ganda, microfoam susu lembut",
            price = 25000,
            emoji = "☕"
        ),
        MenuItem(
            id = "menu_5",
            name = "Matcha Latte",
            category = "Minuman",
            description = "Matcha Uji Jepang asli, steamed fresh milk",
            price = 27000,
            emoji = "🍵"
        ),
        MenuItem(
            id = "menu_6",
            name = "Butter Croissant",
            category = "Camilan",
            description = "Pastry berlapis mentega Prancis harum & renyah",
            price = 20000,
            emoji = "🥐"
        ),
        MenuItem(
            id = "menu_7",
            name = "Caramel Cold Brew",
            category = "Kopi",
            description = "Seduhan dingin 16 jam dengan sentuhan karamel",
            price = 28000,
            emoji = "🧊"
        )
    )
}
