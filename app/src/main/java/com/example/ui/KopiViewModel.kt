package com.example.ui

import androidx.lifecycle.ViewModel
import com.example.data.model.CartItem
import com.example.data.model.DeliveryAddress
import com.example.data.model.DummyData
import com.example.data.model.MenuItem
import com.example.data.model.PlacedOrder
import com.example.data.model.UserProfile
import com.example.ui.navigation.BottomTab
import com.example.ui.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

data class KopiUiState(
    val currentScreen: Screen = Screen.Splash,
    val selectedTab: BottomTab = BottomTab.HOME,
    val userProfile: UserProfile = UserProfile(),
    val menuList: List<MenuItem> = DummyData.menuItems,
    val selectedCategory: String = "Semua",
    val cart: Map<String, Int> = emptyMap(), // menuItemId -> quantity
    val deliveryAddress: DeliveryAddress = DeliveryAddress(
        recipientName = "Haikal",
        address = "Jl. Merdeka No. 45, Kebayoran Baru",
        landmark = "Pagar hitam depan taman perumahan"
    ),
    val lastPlacedOrder: PlacedOrder? = null,
    val snackbarMessage: String? = null
) {
    val totalCartCount: Int
        get() = cart.values.sum()

    val cartItems: List<CartItem>
        get() = cart.mapNotNull { (id, qty) ->
            menuList.find { it.id == id }?.let { CartItem(it, qty) }
        }

    val subtotal: Long
        get() = cartItems.sumOf { it.item.price * it.quantity }

    val deliveryFee: Long
        get() = if (cartItems.isEmpty()) 0L else 10000L

    val totalPrice: Long
        get() = subtotal + deliveryFee
}

class KopiViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(KopiUiState())
    val uiState: StateFlow<KopiUiState> = _uiState.asStateFlow()

    fun navigateTo(screen: Screen) {
        val tab = when (screen) {
            Screen.Home -> BottomTab.HOME
            Screen.OrderAddress, Screen.OrderConfirmation -> BottomTab.ORDER
            Screen.Profile -> BottomTab.PROFILE
            else -> _uiState.value.selectedTab
        }
        _uiState.update { it.copy(currentScreen = screen, selectedTab = tab) }
    }

    fun onTabSelected(tab: BottomTab) {
        when (tab) {
            BottomTab.HOME -> navigateTo(Screen.Home)
            BottomTab.ORDER -> {
                // If cart is empty, auto add 1 signature item (Kopi Susu Gula Aren) so user has an order to confirm
                if (_uiState.value.cart.isEmpty() && _uiState.value.menuList.isNotEmpty()) {
                    val defaultItem = _uiState.value.menuList.first()
                    addToCart(defaultItem)
                }
                navigateTo(Screen.OrderAddress)
            }
            BottomTab.PROFILE -> navigateTo(Screen.Profile)
        }
    }

    fun login(username: String, pass: String): Boolean {
        if (username.isBlank() || pass.isBlank()) {
            _uiState.update { it.copy(snackbarMessage = "Mohon isi username dan password") }
            return false
        }
        // If username was entered, use it for greeting
        val currentName = if (username.equals("haikal", ignoreCase = true) || username.equals("adehaikal", ignoreCase = true)) {
            "Haikal"
        } else {
            username.replaceFirstChar { it.uppercase() }
        }
        _uiState.update {
            it.copy(
                userProfile = it.userProfile.copy(username = username, fullName = currentName),
                currentScreen = Screen.Home,
                selectedTab = BottomTab.HOME,
                deliveryAddress = it.deliveryAddress.copy(recipientName = currentName)
            )
        }
        return true
    }

    fun register(fullName: String, username: String, pass: String): Boolean {
        if (fullName.isBlank() || username.isBlank() || pass.isBlank()) {
            _uiState.update { it.copy(snackbarMessage = "Mohon lengkapi seluruh data pendaftaran") }
            return false
        }
        _uiState.update {
            it.copy(
                userProfile = it.userProfile.copy(fullName = fullName, username = username),
                currentScreen = Screen.Home,
                selectedTab = BottomTab.HOME,
                deliveryAddress = it.deliveryAddress.copy(recipientName = fullName)
            )
        }
        return true
    }

    fun updateProfile(name: String) {
        if (name.isBlank()) {
            _uiState.update { it.copy(snackbarMessage = "Nama tidak boleh kosong") }
            return
        }
        _uiState.update {
            it.copy(
                userProfile = it.userProfile.copy(fullName = name),
                deliveryAddress = it.deliveryAddress.copy(recipientName = name),
                snackbarMessage = "Profil berhasil disimpan!"
            )
        }
    }

    fun setCategory(category: String) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun addToCart(item: MenuItem) {
        _uiState.update { state ->
            val newCart = state.cart.toMutableMap()
            val currentQty = newCart[item.id] ?: 0
            newCart[item.id] = currentQty + 1
            state.copy(cart = newCart)
        }
    }

    fun removeFromCart(item: MenuItem) {
        _uiState.update { state ->
            val newCart = state.cart.toMutableMap()
            val currentQty = newCart[item.id] ?: 0
            if (currentQty <= 1) {
                newCart.remove(item.id)
            } else {
                newCart[item.id] = currentQty - 1
            }
            state.copy(cart = newCart)
        }
    }

    fun updateDeliveryAddress(name: String, address: String, landmark: String) {
        _uiState.update {
            it.copy(
                deliveryAddress = DeliveryAddress(
                    recipientName = name,
                    address = address,
                    landmark = landmark
                )
            )
        }
    }

    fun placeOrder(name: String, address: String, landmark: String): Boolean {
        if (name.isBlank() || address.isBlank()) {
            _uiState.update { it.copy(snackbarMessage = "Mohon isi Nama dan Alamat pengiriman") }
            return false
        }
        // Ensure at least 1 item is in cart
        if (_uiState.value.cart.isEmpty()) {
            val defaultItem = _uiState.value.menuList.first()
            addToCart(defaultItem)
        }

        val updatedAddress = DeliveryAddress(name, address, landmark)
        val state = _uiState.value
        val order = PlacedOrder(
            orderId = "KPN-" + UUID.randomUUID().toString().take(6).uppercase(),
            customerName = name,
            address = updatedAddress,
            items = state.cartItems,
            subtotal = state.subtotal,
            deliveryFee = 10000L,
            total = state.subtotal + 10000L
        )

        _uiState.update {
            it.copy(
                deliveryAddress = updatedAddress,
                lastPlacedOrder = order,
                userProfile = it.userProfile.copy(fullName = name),
                currentScreen = Screen.OrderConfirmation,
                selectedTab = BottomTab.ORDER
            )
        }
        return true
    }

    fun resetOrderAndGoHome() {
        _uiState.update {
            it.copy(
                cart = emptyMap(),
                currentScreen = Screen.Home,
                selectedTab = BottomTab.HOME
            )
        }
    }

    fun clearSnackbar() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }
}
