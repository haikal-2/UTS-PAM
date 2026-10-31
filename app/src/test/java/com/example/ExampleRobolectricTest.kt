package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.ui.KopiViewModel
import com.example.ui.navigation.Screen
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Kopi dan Nyemil", appName)
  }

  @Test
  fun `verify initial menu and cart flow`() {
    val viewModel = KopiViewModel()
    val state = viewModel.uiState.value

    // Check minimum 5 menu items
    assertTrue("Harus memiliki minimal 5 item menu", state.menuList.size >= 5)

    // Add item to cart
    val firstItem = state.menuList.first()
    viewModel.addToCart(firstItem)

    val updatedState = viewModel.uiState.value
    assertEquals(1, updatedState.totalCartCount)
    assertEquals(firstItem.price, updatedState.subtotal)

    // Update profile
    viewModel.updateProfile("Budi Santoso")
    assertEquals("Budi Santoso", viewModel.uiState.value.userProfile.fullName)

    // Place order
    val orderSuccess = viewModel.placeOrder(
        name = "Budi Santoso",
        address = "Jl. Sudirman No. 10",
        landmark = "Dekat halte"
    )
    assertTrue(orderSuccess)
    assertEquals(Screen.OrderConfirmation, viewModel.uiState.value.currentScreen)
    assertNotNull(viewModel.uiState.value.lastPlacedOrder)
  }
}
