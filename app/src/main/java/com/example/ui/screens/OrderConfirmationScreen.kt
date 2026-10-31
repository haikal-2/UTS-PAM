package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Moped
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PlacedOrder
import com.example.ui.components.KopiGreetingHeader
import com.example.ui.components.KopiPrimaryButton
import com.example.ui.theme.CaramelGold
import com.example.ui.theme.CoffeeBrown
import com.example.ui.theme.CoffeeContainer
import com.example.ui.theme.EspressoTextPrimary
import com.example.ui.theme.EspressoTextSecondary
import com.example.ui.theme.OutlineBorder
import com.example.ui.theme.SuccessBadgeGreen
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TerracottaAccent
import com.example.ui.theme.WarmCardSurface
import com.example.ui.theme.WarmCreamBackground

@Composable
fun OrderConfirmationScreen(
    fullName: String,
    placedOrder: PlacedOrder?,
    onOkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = onOkClick)

    val scaleAnim = remember { Animatable(0.2f) }

    LaunchedEffect(Unit) {
        scaleAnim.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCreamBackground)
            .padding(24.dp)
            .testTag("order_confirmation_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 88.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header "Halo Nama," as shown in wireframe
            KopiGreetingHeader(
                fullName = fullName,
                modifier = Modifier.padding(horizontal = 0.dp)
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Large Circular Green OK badge as shown in wireframe
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .scale(scaleAnim.value)
                    .clip(CircleShape)
                    .background(Color(0xFFB9F6CA)),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(104.dp)
                        .clip(CircleShape)
                        .background(SuccessBadgeGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Sukses",
                            tint = Color.White,
                            modifier = Modifier.size(34.dp)
                        )
                        Text(
                            text = "OK",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp,
                            modifier = Modifier.testTag("ok_badge_text")
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Message as specified in wireframe:
            // "Terima kasih sudah memesan tunggu aja sebentar kurir kami segera mengantarkan pesananmu!"
            Text(
                text = "Terima kasih sudah memesan tunggu aja sebentar kurir kami segera mengantarkan pesananmu!",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 17.sp,
                    lineHeight = 25.sp,
                    fontWeight = FontWeight.Medium,
                    color = EspressoTextPrimary
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .testTag("confirmation_message_text")
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Order status details card
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
                        .padding(18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Nomor Pesanan",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = EspressoTextSecondary
                            )
                        )
                        Text(
                            text = placedOrder?.orderId ?: "KPN-782194",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = CoffeeBrown
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Moped,
                                contentDescription = "Kurir",
                                tint = TerracottaAccent,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Estimasi Pengantaran",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = EspressoTextSecondary
                                )
                            )
                        }
                        Text(
                            text = "15 - 25 Menit",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TerracottaAccent
                            )
                        )
                    }

                    if (placedOrder != null && placedOrder.address.address.isNotBlank()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Alamat Pengiriman:",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = EspressoTextSecondary,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = placedOrder.address.address,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = EspressoTextPrimary
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Button "Oke" anchored at bottom as shown in wireframe
        KopiPrimaryButton(
            text = "Oke",
            onClick = onOkClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp),
            testTag = "confirmation_oke_button"
        )
    }
}
