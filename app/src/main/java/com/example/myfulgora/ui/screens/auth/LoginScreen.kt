package com.example.myfulgora.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myfulgora.R
import com.example.myfulgora.ui.components.FulgoraBackground
import com.example.myfulgora.ui.components.CustomDarkInput
import com.example.myfulgora.ui.components.CustomDarkPasswordInput
import com.example.myfulgora.ui.theme.AppIcons
import com.example.myfulgora.ui.theme.GreenFresh
import com.example.myfulgora.ui.viewmodel.LoginState
import com.example.myfulgora.ui.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    val state by viewModel.loginState.collectAsState()
    val username by viewModel.username.collectAsState()
    val password by viewModel.password.collectAsState()

    LaunchedEffect(state) {
        if (state is LoginState.Success) {
            onLoginSuccess()
        }
    }

    FulgoraBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // 1. LOGÓTIPO E TEXTO (Como tinhas antes)
            Image(
                painter = painterResource(id = R.drawable.logo_app),
                contentDescription = "Logo",
                modifier = Modifier.width(120.dp), // Voltou ao tamanho original
                contentScale = ContentScale.Fit
            )

            // Espaço opcional, se quiseres afastar o texto da imagem
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Welcome back, Rider",
                fontSize = 20.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(48.dp))

            // 2. INPUTS (Estilo Novo)
            CustomDarkInput(
                text = username,
                onTextChange = { viewModel.username.value = it },
                placeholder = "Username",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomDarkPasswordInput(
                text = password,
                onTextChange = { viewModel.password.value = it },
                placeholder = "Password",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )

            // 3. LINK FORGOT PASSWORD
            Spacer(modifier = Modifier.height(12.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                Text(
                    text = "Forgot Password?",
                    color = GreenFresh,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(end = 24.dp)
                        .clickable { onForgotPasswordClick() }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ERRO
            if (state is LoginState.Error) {
                Text(
                    text = (state as LoginState.Error).message,
                    color = Color(0xFFFF5252),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // 4. BOTÃO LOG IN (Estilo Novo com Imagem)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clickable(enabled = state !is LoginState.Loading) {
                        viewModel.fazerLogin()
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = AppIcons.Actions.Button),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )

                if (state is LoginState.Loading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Login",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}