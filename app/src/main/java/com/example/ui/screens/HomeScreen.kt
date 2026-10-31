package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MenuItem
import com.example.ui.components.KopiBottomBar
import com.example.ui.components.KopiGreetingHeader
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
fun HomeScreen(
    fullName: String,
    menuList: List<MenuItem>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    cart: Map<String, Int>,
    onAddToCart: (MenuItem) -> Unit,
    onRemoveFromCart: (MenuItem) -> Unit,
    onCheckoutClick: () -> Unit,
    currentTab: BottomTab,
    onTabSelected: (BottomTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val totalCartCount = cart.values.sum()
    val subtotal = cart.entries.sumOf { (id, qty) ->
        val price = menuList.find { it.id == id }?.price ?: 0L
        price * qty
    }

    val filteredList = if (selectedCategory == "Semua") {
        menuList
    } else {
        menuList.filter { it.category.equals(selectedCategory, ignoreCase = true) }
    }

    Scaffold(
        bottomBar = {
            KopiBottomBar(
                currentTab = currentTab,
                onTabSelected = onTabSelected,
                cartCount = totalCartCount
            )
        },
        containerColor = WarmCreamBackground,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("home_menu_list"),
                contentPadding = PaddingValues(bottom = if (totalCartCount > 0) 90.dp else 20.dp)
            ) {
                // Header "Halo Nama,"
                item {
                    KopiGreetingHeader(
                        fullName = fullName,
                        subtitle = "Mau ngopi atau nyemil apa hari ini?"
                    )
                }

                // Section "Makanan & Minuman" Title matching wireframe
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Makanan & Minuman",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = EspressoTextPrimary
                            ),
                            modifier = Modifier.testTag("section_makanan_title")
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Category filter chips
                        val categories = listOf("Semua", "Kopi", "Camilan", "Minuman")
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(categories) { cat ->
                                val isSelected = cat == selectedCategory
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { onCategorySelected(cat) },
                                    label = { Text(cat) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = CoffeeBrown,
                                        selectedLabelColor = Color.White,
                                        containerColor = Color.White,
                                        labelColor = EspressoTextSecondary
                                    ),
                                    border = FilterChipDefaults.filterChipBorder(
                                        enabled = true,
                                        selected = isSelected,
                                        borderColor = if (isSelected) CoffeeBrown else OutlineBorder
                                    ),
                                    shape = RoundedCornerShape(20.dp),
                                    modifier = Modifier.testTag("filter_chip_$cat")
                                )
                            }
                        }
                    }
                }

                // Menu items cards (minimal 5 items)
                items(filteredList, key = { it.id }) { item ->
                    val quantity = cart[item.id] ?: 0
                    MenuItemCard(
                        item = item,
                        quantity = quantity,
                        onAdd = { onAddToCart(item) },
                        onRemove = { onRemoveFromCart(item) },
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                    )
                }
            }

            // Interactive Floating Cart Bar when items are selected
            AnimatedVisibility(
                visible = totalCartCount > 0,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = CoffeeBrown),
                    elevation = CardDefaults.cardElevation(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCheckoutClick() }
                        .testTag("cart_preview_banner")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(TerracottaAccent),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "$totalCartCount",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Keranjang Pesanan",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = Color.White.copy(alpha = 0.8f)
                                    )
                                )
                                Text(
                                    text = "Rp ${String.format("%,d", subtotal).replace(',', '.')}",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(TerracottaAccent)
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "Lanjut Pesan",
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Lanjut ke alamat",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MenuItemCard(
    item: MenuItem,
    quantity: Int,
    onAdd: () -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = WarmCardSurface),
        elevation = CardDefaults.cardElevation(2.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, OutlineBorder.copy(alpha = 0.6f)),
        modifier = modifier
            .fillMaxWidth()
            .testTag("menu_card_${item.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // "Foto" container matching wireframe peach/caramel box
            Box(
                modifier = Modifier
                    .size(76.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(CoffeeContainer),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = item.emoji,
                        fontSize = 32.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Foto",
                        fontSize = 10.sp,
                        color = EspressoTextSecondary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Details: Name, bullet description, and price
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = EspressoTextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "• ${item.description}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = EspressoTextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    ),
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = item.formattedPrice,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = TerracottaAccent,
                        fontSize = 14.sp
                    )
                )
            }

            // Quantity stepper or add button
            if (quantity == 0) {
                Button(
                    onClick = onAdd,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CoffeeBrown,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("add_button_${item.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Tambah ${item.name}",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Pilih", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(CoffeeContainer)
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                ) {
                    IconButton(
                        onClick = onRemove,
                        modifier = Modifier
                            .size(32.dp)
                            .testTag("remove_btn_${item.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Kurang ${item.name}",
                            tint = CoffeeBrown,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Text(
                        text = "$quantity",
                        fontWeight = FontWeight.Bold,
                        color = CoffeeBrown,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .testTag("quantity_${item.id}")
                    )

                    IconButton(
                        onClick = onAdd,
                        modifier = Modifier
                            .size(32.dp)
                            .testTag("add_btn_${item.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Tambah lagi ${item.name}",
                            tint = CoffeeBrown,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}
