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
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.data.model.UserProfile
import com.example.ui.components.KopiBottomBar
import com.example.ui.components.KopiGreetingHeader
import com.example.ui.components.KopiPrimaryButton
import com.example.ui.components.KopiTextField
import com.example.ui.navigation.BottomTab
import com.example.ui.theme.CaramelGold
import com.example.ui.theme.CoffeeBrown
import com.example.ui.theme.CoffeeContainer
import com.example.ui.theme.EspressoTextPrimary
import com.example.ui.theme.EspressoTextSecondary
import com.example.ui.theme.OutlineBorder
import com.example.ui.theme.TerracottaAccent
import com.example.ui.theme.WarmCardSurface
import com.example.ui.theme.WarmCreamBackground

@Composable
fun ProfileScreen(
    userProfile: UserProfile,
    onSaveProfile: (String) -> Unit,
    currentTab: BottomTab,
    onTabSelected: (BottomTab) -> Unit,
    onBack: () -> Unit,
    cartCount: Int = 0,
    snackbarMessage: String? = null,
    onDismissSnackbar: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = onBack)

    var inputName by remember(userProfile.fullName) { mutableStateOf(userProfile.fullName) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackbarMessage) {
        if (snackbarMessage != null) {
            snackbarHostState.showSnackbar(snackbarMessage)
            onDismissSnackbar()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            KopiBottomBar(
                currentTab = currentTab,
                onTabSelected = onTabSelected,
                cartCount = cartCount
            )
        },
        containerColor = WarmCreamBackground,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Header "Halo Nama,"
            KopiGreetingHeader(
                fullName = userProfile.fullName,
                subtitle = "Kelola data akun Kopi dan Nyemil kamu"
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                // Section "Profile Saya" matching wireframe
                Text(
                    text = "Profile Saya",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = EspressoTextPrimary
                    ),
                    modifier = Modifier.testTag("profile_saya_title")
                )

                Spacer(modifier = Modifier.height(12.dp))

                // User membership card
                Card(
                    shape = RoundedCornerShape(20.dp),
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .background(CoffeeBrown),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = userProfile.fullName.take(1).uppercase(),
                                    color = Color.White,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {
                                Text(
                                    text = userProfile.fullName,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = EspressoTextPrimary
                                    )
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "@${userProfile.username}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = EspressoTextSecondary
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(CoffeeContainer.copy(alpha = 0.6f))
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CardGiftcard,
                                    contentDescription = "Poin Loyalty",
                                    tint = TerracottaAccent,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Poin Nyemil",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Medium,
                                        color = EspressoTextPrimary
                                    )
                                )
                            }
                            Text(
                                text = "${userProfile.loyaltyPoints} Poin",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TerracottaAccent
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Subsection "Profile Setting" matching wireframe
                Text(
                    text = "Profile Setting",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = EspressoTextPrimary
                    ),
                    modifier = Modifier.testTag("profile_setting_title")
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Input "nama" as specified in wireframe
                KopiTextField(
                    value = inputName,
                    onValueChange = { inputName = it },
                    label = "Nama",
                    placeholder = "nama",
                    testTag = "profile_name_input"
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Button "Save" as specified in wireframe
                KopiPrimaryButton(
                    text = "Save",
                    onClick = { onSaveProfile(inputName) },
                    testTag = "profile_save_button"
                )

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}
