package com.example.myfulgora.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfulgora.R
import com.example.myfulgora.ui.components.FulgoraBackground
import com.example.myfulgora.ui.theme.AppIcons
import com.example.myfulgora.ui.theme.GreenDeep
import com.example.myfulgora.ui.theme.GreenFresh
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.layout.ContentScale
import kotlinx.coroutines.delay
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff


// Cor do fundo do input (Cinzento Escuro do Mockup)
val DarkInputBackground = Color(0xFF222222)


@Composable
fun ForgotPasswordScreen(
    onNavigateBack: () -> Unit,
    onLoginAfterReset: () -> Unit
) {
    // Controla qual o passo atual (1, 2, 3 ou 4)
    var currentStep by remember { mutableIntStateOf(1) }

    FulgoraBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Botão de Voltar (Só aparece nos passos 1, 2 e 3)
            if (currentStep < 4) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                    IconButton(onClick = {
                        if (currentStep > 1) currentStep-- else onNavigateBack()
                    }) {
                        Icon(
                            // Substitui 'AppIcons.Actions.Back' pelo nome exato que deste ao ícone no ficheiro AppIcons
                            painter = painterResource(id = AppIcons.Actions.ArrowLeft1),
                            contentDescription = "Back",
                            tint = Color.Unspecified, // Podes mudar a cor se o ícone não for branco de origem
                            modifier = Modifier.size(24.dp) // Ajusta o tamanho se achares pequeno/grande
                        )                    }
                }
            } else {
                Spacer(modifier = Modifier.height(48.dp)) // Espaço no ecrã de sucesso
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Lógica para trocar o conteúdo do ecrã
            when (currentStep) {
                1 -> StepInputEmail(onNext = { currentStep = 2 })
                2 -> StepVerification(onNext = { currentStep = 3 })
                3 -> StepNewPassword(onNext = { currentStep = 4 })
                4 -> StepSuccess(onDone = onLoginAfterReset)
            }
        }
    }
}

// --- PASSO 1: EMAIL (Atualizado com Visual Dark & Ícone) ---
@Composable
fun StepInputEmail(onNext: () -> Unit) {
    var email by remember { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(90.dp))
        Text("Forget your Password?", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Enter the email address linked to your account and we'll send you a reset link.",
            color = Color.Gray,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 48.dp)
        )
        Spacer(modifier = Modifier.height(22.dp))

        // O Input Cinzento (que já tinhas gostado)
        Box(modifier = Modifier.padding(horizontal = 26.dp)) {
            CustomDarkInput(
                text = email,
                onTextChange = { email = it },
                placeholder = "Email address"
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 👇 AQUI ESTÁ A MUDANÇA: O "Botão Imagem"
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp) // Altura fixa igual à do input
                // Faz o "clique" funcionar na imagem toda
                .clickable(onClick = onNext),
            contentAlignment = Alignment.Center
        ) {
            // 1. A IMAGEM DE FUNDO (O ficheiro PNG que mandaste)
            Image(
                painter = painterResource(id = AppIcons.Actions.Button),
                contentDescription = null,
                contentScale = ContentScale.FillBounds, // Estica a imagem para encher o botão
                modifier = Modifier.fillMaxSize()
            )

            // 2. O TEXTO (Fica por cima da imagem)
            Text(
                text = "Continue",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

// --- PASSO 2: CÓDIGO (Podes atualizar este input também se quiseres manter consistência) ---
@Composable
fun StepVerification(onNext: () -> Unit) {
    var code by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    // --- LÓGICA DO TIMER ---
    // Controla se o timer está ativo
    var isTimerRunning by remember { mutableStateOf(false) }
    // Guarda os segundos restantes
    var timeLeft by remember { mutableIntStateOf(30) }

    // O efeito que faz a contagem decrescente
    LaunchedEffect(isTimerRunning) {
        if (isTimerRunning) {
            timeLeft = 30 // Reinicia o contador
            while (timeLeft > 0) {
                delay(1000L) // Espera 1 segundo
                timeLeft--   // Diminui 1 segundo
            }
            isTimerRunning = false // Quando chega a 0, desbloqueia o botão
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(90.dp))
        Text("Verification", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Please enter the 4 digit code sent to\nab*****@gmail.com",
            color = Color.Gray,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        OtpInputField(
            otpText = code,
            onOtpTextChange = { code = it },
            otpCount = 4
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botão Verify
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clickable(onClick = onNext),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = AppIcons.Actions.Button),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
            Text("Verify", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- TEXTO SEND AGAIN COM TIMER ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Haven't received it? ",
                color = Color.Gray,
                fontSize = 12.sp
            )

            // Lógica Visual:
            if (isTimerRunning) {
                // MODO BLOQUEADO (Contagem decrescente)
                Text(
                    text = "Resend in ${timeLeft}s", // Ex: "Resend in 29s"
                    color = Color.Gray, // Cinzento para indicar inativo
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                    // Sem modifier.clickable, logo não faz nada
                )
            } else {
                // MODO ATIVO (Botão Verde)
                Text(
                    text = "Send again",
                    color = GreenFresh,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        showDialog = true // Abre o Popup
                    }
                )
            }
        }
    }

    // O POP-UP
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            containerColor = Color(0xFF222222),
            title = { Text("Resend Code?", color = Color.White, fontWeight = FontWeight.Bold) },
            text = { Text("We will send a new verification code to your email address.", color = Color.Gray, fontSize = 14.sp) },

            confirmButton = {
                TextButton(
                    onClick = {
                        // 1. Fecha o popup
                        showDialog = false
                        // 2. INICIA O TIMER (Aqui é que a magia acontece)
                        isTimerRunning = true

                        // TODO: Chamar a função real de enviar email aqui
                    }
                ) {
                    Text("Resend Email", color = GreenFresh, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel", color = Color.Gray)
                }
            }
        )
    }
}

// --- PASSO 3: NOVA PASSWORD ---
@Composable
fun StepNewPassword(onNext: () -> Unit) {
    var pass1 by remember { mutableStateOf("") }
    var pass2 by remember { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(90.dp))
        // Título
        Text("Create New Password", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)

        Spacer(modifier = Modifier.height(16.dp))

        // Descrição (com o padding lateral de 32.dp para ficar alinhado com os anteriores)
        Text("Please enter a new password", fontSize = 14.sp, color = Color.Gray, textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 32.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "Your new password must be different from your previous password",
            color = Color.Gray,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(26.dp))

        // 👇 INPUT 1: Nova Password (com "olho" e texto oculto)
        CustomDarkPasswordInput(
            text = pass1,
            onTextChange = { pass1 = it },
            placeholder = "Password"
        )

        Spacer(modifier = Modifier.height(16.dp)) // Espaço menor entre os dois inputs

        // 👇 INPUT 2: Confirmar Password
        CustomDarkPasswordInput(
            text = pass2,
            onTextChange = { pass2 = it },
            placeholder = "Confirm Password"
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 👇 BOTÃO COM IMAGEM (Igual aos anteriores)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clickable(onClick = onNext),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = AppIcons.Actions.Button),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )

            Text(
                text = "Reset Password",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

// --- PASSO 4: SUCESSO ---
@Composable
fun StepSuccess(onDone: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize() // Ocupa o ecrã todo para poder centrar
            .padding(top=100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // 1. Marca (Topo)
        Image(
            painter = painterResource(id = R.drawable.logo_crop),
            contentDescription = "Logo",
            modifier = Modifier.width(180.dp) // Ajusta o tamanho se necessário
        )
        Spacer(modifier = Modifier.height(30.dp))


        // 2. Título Verde
        Text(
            text = "Password updated",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = GreenFresh
        )

        Spacer(modifier = Modifier.height(4.dp))

        // 3. Descrição (Com o padding lateral de 32dp para consistência)
        Text(
            text = "Your password has been changed successfully.\nYou can now Log In with your new password.",
            color = Color.Gray,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        // 4. O BOTÃO "LOG IN" (Com a tua imagem de fundo)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clickable(onClick = onDone),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = AppIcons.Actions.Button),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )

            Text(
                text = "Login",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        // Um pequeno espaço em branco no fundo para equilíbrio visual
        Spacer(modifier = Modifier.height(50.dp))
    }
}

// ==========================================
// 👇 O COMPONENTE MÁGICO DO INPUT CINZENTO
// ==========================================
@Composable
fun CustomDarkInput(
    text: String,
    onTextChange: (String) -> Unit,
    placeholder: String
) {
    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 16.sp
        ),
        cursorBrush = SolidColor(GreenFresh), // O cursor a piscar fica Verde
        singleLine = true,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp) // Altura confortável
                    .background(
                        color = DarkInputBackground, // O Cinzento Escuro
                        shape = RoundedCornerShape(10.dp) // Cantos arredondados
                    )
                    .padding(horizontal = 16.dp), // Margem interna
                contentAlignment = Alignment.CenterStart
            ) {
                // Placeholder
                if (text.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
                // O texto que escreves
                innerTextField()
            }
        }
    )
}

@Composable
fun OtpInputField(
    otpText: String,
    onOtpTextChange: (String) -> Unit,
    otpCount: Int = 4 // 👈 Podes mudar isto para 6 se precisares no futuro
) {
    BasicTextField(
        value = otpText,
        onValueChange = {
            // Só deixa escrever se não ultrapassar o número de quadrados
            // e se for numérico (opcional)
            if (it.length <= otpCount) {
                onOtpTextChange(it)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            // A Linha que contém os quadrados
            Row(
                horizontalArrangement = Arrangement.Center, // Centra os quadrados no ecrã
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Cria X quadrados repetidos
                repeat(otpCount) { index ->

                    // Verifica qual o número neste índice (se existir)
                    val char = when {
                        index >= otpText.length -> "" // Se ainda não escreveu, vazio
                        else -> otpText[index].toString()
                    }

                    // Se este quadrado é o próximo a ser escrito (Foco visual)
                    val isFocused = index == otpText.length

                    // O Desenho do Quadrado Individual
                    Box(
                        modifier = Modifier
                            .width(60.dp)  // Largura do quadrado
                            .height(60.dp) // Altura do quadrado
                            .padding(4.dp) // Espaço entre eles
                            .background(
                                color = DarkInputBackground, // O teu cinzento escuro
                                shape = RoundedCornerShape(12.dp)
                            )
                            // Opcional: Borda verde se for o quadrado ativo
                            .border(
                                width = if (isFocused) 1.dp else 0.dp,
                                color = if (isFocused) GreenFresh else Color.Transparent,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = char,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun CustomDarkPasswordInput(
    text: String,
    onTextChange: (String) -> Unit,
    placeholder: String
) {
    // Estado para controlar se mostra a senha ou não
    var isPasswordVisible by remember { mutableStateOf(false) }

    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 16.sp
        ),
        cursorBrush = SolidColor(GreenFresh),
        singleLine = true,
        // 👇 A Mágica: Se visível -> Texto normal. Se não -> Bolinhas
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    // Altura mais fina (45dp) igual ao que definimos antes
                    .height(45.dp)
                    .background(DarkInputBackground, RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Caixa para o texto + Placeholder
                Box(modifier = Modifier.weight(1f)) {
                    if (text.isEmpty()) {
                        Text(text = placeholder, color = Color.Gray, fontSize = 16.sp)
                    }
                    innerTextField()
                }

                // Ícone do Olho (Clicável)
                IconButton(
                    onClick = { isPasswordVisible = !isPasswordVisible },
                    modifier = Modifier.size(20.dp) // Ícone discreto
                ) {
                    Icon(
                        // Troca o ícone dependendo do estado
                        imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle Password",
                        tint = Color.Gray // Cinzento para não distrair
                    )
                }
            }
        }
    )
}