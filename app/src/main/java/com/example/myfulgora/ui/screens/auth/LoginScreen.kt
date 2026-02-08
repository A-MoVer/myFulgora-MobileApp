package com.example.myfulgora.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.* // Importante para o remember e mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myfulgora.R
import com.example.myfulgora.ui.components.FulgoraTextField
import com.example.myfulgora.ui.components.FulgoraPasswordField
import com.example.myfulgora.ui.components.FulgoraBackground
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
    var usernameInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }

    // O Estado do login (Loading, Erro, Sucesso) continua a vir do ViewModel
    val loginState by viewModel.loginState.collectAsState()

    // Se o login for sucesso, navega
    LaunchedEffect(loginState) {
        if (loginState is LoginState.Success) {
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

            // 1. LOGÓTIPO
            Image(
                painter = painterResource(id = R.drawable.logo_app),
                contentDescription = "Logo",
                modifier = Modifier.width(120.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.login_welcome),
                fontSize = 20.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(48.dp))

            // 2. INPUT USERNAME (Agora com variáveis locais)
            FulgoraTextField(
                text = usernameInput,
                onTextChange = { usernameInput = it }, // 👈 Isto garante que consegues escrever!
                placeholder = "Username",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 3. INPUT PASSWORD
            FulgoraPasswordField(
                text = passwordInput,
                onTextChange = { passwordInput = it }, // 👈 Isto garante que consegues escrever!
                placeholder = "Password",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )

            // 4. ESQUECI A PASSWORD
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

            // MENSAGEM DE ERRO (Se houver)
            if (loginState is LoginState.Error) {
                Text(
                    text = (loginState as LoginState.Error).message,
                    color = Color(0xFFFF5252),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // 5. BOTÃO DE LOGIN
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .clickable(enabled = loginState !is LoginState.Loading) {
                        // 👇 Enviamos o texto local para o ViewModel fazer o trabalho
                        viewModel.fazerLogin(usernameInput, passwordInput)
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = AppIcons.Actions.Button),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )

                if (loginState is LoginState.Loading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
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