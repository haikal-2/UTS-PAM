package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.KopiViewModel
import com.example.ui.navigation.Screen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.OrderAddressScreen
import com.example.ui.screens.OrderConfirmationScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.RegisterScreen
import com.example.ui.screens.SignUpGatewayScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.theme.KopiDanNyemilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KopiDanNyemilTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    KopiApp()
                }
            }
        }
    }
}

@Composable
fun KopiApp(
    viewModel: KopiViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val screen = uiState.currentScreen) {
        is Screen.Splash -> {
            SplashScreen(
                onStartNowClick = {
                    viewModel.navigateTo(Screen.SignUp)
                }
            )
        }

        is Screen.SignUp -> {
            SignUpGatewayScreen(
                onRegisterClick = {
                    viewModel.navigateTo(Screen.Register)
                },
                onLoginClick = {
                    viewModel.navigateTo(Screen.Login)
                },
                onBack = {
                    viewModel.navigateTo(Screen.Splash)
                }
            )
        }

        is Screen.Login -> {
            LoginScreen(
                onLoginSuccess = { user, pass ->
                    viewModel.login(user, pass)
                },
                onNavigateToRegister = {
                    viewModel.navigateTo(Screen.Register)
                },
                onBack = {
                    viewModel.navigateTo(Screen.SignUp)
                }
            )
        }

        is Screen.Register -> {
            RegisterScreen(
                onRegisterSuccess = { fullName, username, pass ->
                    viewModel.register(fullName, username, pass)
                },
                onNavigateToLogin = {
                    viewModel.navigateTo(Screen.Login)
                },
                onBack = {
                    viewModel.navigateTo(Screen.SignUp)
                }
            )
        }

        is Screen.Home -> {
            HomeScreen(
                fullName = uiState.userProfile.fullName,
                menuList = uiState.menuList,
                selectedCategory = uiState.selectedCategory,
                onCategorySelected = { viewModel.setCategory(it) },
                cart = uiState.cart,
                onAddToCart = { viewModel.addToCart(it) },
                onRemoveFromCart = { viewModel.removeFromCart(it) },
                onCheckoutClick = {
                    viewModel.navigateTo(Screen.OrderAddress)
                },
                currentTab = uiState.selectedTab,
                onTabSelected = { viewModel.onTabSelected(it) }
            )
        }

        is Screen.Profile -> {
            ProfileScreen(
                userProfile = uiState.userProfile,
                onSaveProfile = { newName ->
                    viewModel.updateProfile(newName)
                },
                currentTab = uiState.selectedTab,
                onTabSelected = { viewModel.onTabSelected(it) },
                onBack = {
                    viewModel.navigateTo(Screen.Home)
                },
                cartCount = uiState.totalCartCount,
                snackbarMessage = uiState.snackbarMessage,
                onDismissSnackbar = { viewModel.clearSnackbar() }
            )
        }

        is Screen.OrderAddress -> {
            OrderAddressScreen(
                currentAddress = uiState.deliveryAddress,
                cartItems = uiState.cartItems,
                subtotal = uiState.subtotal,
                deliveryFee = uiState.deliveryFee,
                total = uiState.totalPrice,
                onOrderAndSendClick = { name, address, landmark ->
                    viewModel.placeOrder(name, address, landmark)
                },
                onBack = {
                    viewModel.navigateTo(Screen.Home)
                }
            )
        }

        is Screen.OrderConfirmation -> {
            OrderConfirmationScreen(
                fullName = uiState.userProfile.fullName,
                placedOrder = uiState.lastPlacedOrder,
                onOkClick = {
                    viewModel.resetOrderAndGoHome()
                }
            )
        }
    }
}
