package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.KopiPrimaryButton
import com.example.ui.components.KopiTextField
import com.example.ui.theme.CoffeeBrown
import com.example.ui.theme.CoffeeContainer
import com.example.ui.theme.EspressoTextPrimary
import com.example.ui.theme.EspressoTextSecondary
import com.example.ui.theme.OutlineBorder
import com.example.ui.theme.TerracottaAccent
import com.example.ui.theme.WarmCreamBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegisterSuccess: (String, String, String) -> Unit,
    onNavigateToLogin: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = onBack)

    var fullName by remember { mutableStateOf("Haikal") }
    var username by remember { mutableStateOf("haikal") }
    var password by remember { mutableStateOf("kopi123") }
    var passwordVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("register_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
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
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(CoffeeContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "📝", fontSize = 28.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Register",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = EspressoTextPrimary
                ),
                modifier = Modifier.testTag("register_heading")
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Buat akun baru untuk nikmati kopi dan camilan favorit",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = EspressoTextSecondary
                )
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Inputs according to wireframe: nama lengkap, username, password
            KopiTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = "Nama Lengkap",
                placeholder = "nama lengkap",
                testTag = "register_fullname_input"
            )

            Spacer(modifier = Modifier.height(18.dp))

            KopiTextField(
                value = username,
                onValueChange = { username = it },
                label = "Username",
                placeholder = "username",
                testTag = "register_username_input"
            )

            Spacer(modifier = Modifier.height(18.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Password",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Medium,
                        color = EspressoTextSecondary
                    ),
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = {
                        Text(
                            text = "password",
                            color = EspressoTextSecondary.copy(alpha = 0.6f)
                        )
                    },
                    shape = RoundedCornerShape(14.dp),
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = if (passwordVisible) "Sembunyikan password" else "Tampilkan password",
                                tint = EspressoTextSecondary
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = CoffeeBrown,
                        unfocusedBorderColor = OutlineBorder,
                        focusedTextColor = EspressoTextPrimary,
                        unfocusedTextColor = EspressoTextPrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("register_password_input")
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            KopiPrimaryButton(
                text = "Register",
                onClick = { onRegisterSuccess(fullName, username, password) },
                testTag = "register_submit_button"
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sudah memiliki akun? ",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = EspressoTextSecondary
                    )
                )
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TerracottaAccent,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .clickable { onNavigateToLogin() }
                        .padding(4.dp)
                        .testTag("go_to_login_text")
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
