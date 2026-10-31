package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CartItem
import com.example.data.model.DeliveryAddress
import com.example.ui.components.KopiPrimaryButton
import com.example.ui.components.KopiTextField
import com.example.ui.theme.CaramelGold
import com.example.ui.theme.CoffeeBrown
import com.example.ui.theme.CoffeeContainer
import com.example.ui.theme.EspressoTextPrimary
import com.example.ui.theme.EspressoTextSecondary
import com.example.ui.theme.OutlineBorder
import com.example.ui.theme.TerracottaAccent
import com.example.ui.theme.WarmCardSurface
import com.example.ui.theme.WarmCreamBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderAddressScreen(
    currentAddress: DeliveryAddress,
    cartItems: List<CartItem>,
    subtotal: Long,
    deliveryFee: Long,
    total: Long,
    onOrderAndSendClick: (String, String, String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = onBack)

    var recipientName by remember(currentAddress.recipientName) { mutableStateOf(currentAddress.recipientName) }
    var address by remember(currentAddress.address) { mutableStateOf(currentAddress.address) }
    var landmark by remember(currentAddress.landmark) { mutableStateOf(currentAddress.landmark) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Alamat",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = EspressoTextPrimary
                        ),
                        modifier = Modifier.testTag("alamat_screen_title")
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("address_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali ke Home",
                            tint = CoffeeBrown
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = WarmCreamBackground
                )
            )
        },
        containerColor = WarmCreamBackground,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Order Summary Preview Card
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = WarmCardSurface),
                elevation = CardDefaults.cardElevation(2.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, OutlineBorder.copy(alpha = 0.6f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.ReceiptLong,
                            contentDescription = "Detail Pesanan",
                            tint = CoffeeBrown,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Ringkasan Pesanan",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = EspressoTextPrimary
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (cartItems.isEmpty()) {
                        Text(
                            text = "1x Kopi Susu Gula Aren (Default Signature)",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = EspressoTextSecondary
                            )
                        )
                    } else {
                        cartItems.forEach { cartItem ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 3.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${cartItem.quantity}x ${cartItem.item.name}",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = EspressoTextPrimary
                                    )
                                )
                                Text(
                                    text = "Rp ${String.format("%,d", cartItem.item.price * cartItem.quantity).replace(',', '.')}",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Medium,
                                        color = EspressoTextSecondary
                                    )
                                )
                            }
                        }
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 10.dp),
                        color = OutlineBorder.copy(alpha = 0.5f)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Ongkos Kirim",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = EspressoTextSecondary
                            )
                        )
                        Text(
                            text = "Rp 10.000",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = EspressoTextSecondary
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total Pembayaran",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = EspressoTextPrimary
                            )
                        )
                        Text(
                            text = "Rp ${String.format("%,d", if (total > 0) total else 32000L).replace(',', '.')}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TerracottaAccent
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Form inputs exactly matching wireframe:
            // 1. Nama Lengkap
            // 2. Alamat
            // 3. Patokan atau detail lengkap

            KopiTextField(
                value = recipientName,
                onValueChange = { recipientName = it },
                label = "Nama Lengkap",
                placeholder = "Nama Lengkap",
                testTag = "address_name_input"
            )

            Spacer(modifier = Modifier.height(18.dp))

            KopiTextField(
                value = address,
                onValueChange = { address = it },
                label = "Alamat",
                placeholder = "Alamat",
                singleLine = false,
                maxLines = 3,
                testTag = "address_street_input"
            )

            Spacer(modifier = Modifier.height(18.dp))

            KopiTextField(
                value = landmark,
                onValueChange = { landmark = it },
                label = "Patokan atau detail lengkap",
                placeholder = "Patokan atau detail lengkap",
                singleLine = false,
                maxLines = 3,
                testTag = "address_landmark_input"
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Button "Order dan Kirim" matching wireframe
            KopiPrimaryButton(
                text = "Order dan Kirim",
                onClick = {
                    onOrderAndSendClick(recipientName, address, landmark)
                },
                testTag = "order_dan_kirim_button"
            )

            Spacer(modifier = Modifier.height(36.dp))
        }
    }
}
