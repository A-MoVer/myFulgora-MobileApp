package com.example.myfulgora.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfulgora.R
import com.example.myfulgora.ui.components.FulgoraBackground
import com.example.myfulgora.ui.theme.AppIcons
import kotlinx.coroutines.launch
import androidx.compose.foundation.shape.RoundedCornerShape

// Modelo de dados de cada página
data class OnboardingPage(
    val title: String,
    val description: String,
    val imageRes: Int,
    val imageScale: Float
)

@Composable
fun OnboardingScreen(onFinish: () -> Unit) {

    val pages = listOf(
        OnboardingPage(
            "Go green,\nRide Confidently",
            "Know your ride at a glance. Track battery, range, and key bike stats in real time so you always ride with confidence.",
            R.drawable.img_onboarding_1,
            imageScale = 0.9f
        ),
        OnboardingPage(
            "All Bikes,\nOne App",
            "Keep all your bikes connected in one place. Check status, battery, and location, anytime, anywhere.",
            R.drawable.img_onboarding_2,
            imageScale = 1.25f
        ),
        OnboardingPage(
            "Join the \nCommunity",
            "Join a community built around smarter mobility. Track progress, take on challenges, and share achievements with others.",
            R.drawable.img_onboarding_3,
            imageScale = 1.1f
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    FulgoraBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()      // Protege o topo (relógio/bateria)
                .navigationBarsPadding(), // Protege o fundo (botões do Android)
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // --- TOPO: LOGO ---
            // Adicionei um Spacer para dar "ar" no topo como no mockup
            Spacer(modifier = Modifier.height(20.dp))

            // --- CENTRO: CARROSSEL (PAGER) ---
            // O weight(1f) empurra o topo para cima e o rodapé para baixo, ocupando o meio
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { pageIndex ->
                OnboardingPageContent(pages[pageIndex])
            }

            // --- RODAPÉ: INDICADORES E BOTÃO ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 50.dp), // Espaço extra no fundo para não colar à borda
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // INDICATORS (Bolinhas)
                Row(
                    modifier = Modifier.padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(pages.size) { index ->
                        val isSelected = pagerState.currentPage == index
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                // 1. TAMANHO REDUZIDO:
                                .size(if (isSelected) 8.dp else 6.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected)
                                        Color(0xFFFFFFFF) // Cor das bolinhas
                                    else
                                        Color.White.copy(alpha = 0.2f) // 2. TRANSPARÊNCIA: Cinzento muito subtil nas outras
                                )
                        )
                    }
                }

                // BOTÃO ou TEXTO NEXT
                if (pagerState.currentPage == pages.lastIndex) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp) // Ajusta a altura conforme a tua imagem
                            // 👇 CORREÇÃO RIPPLE: Recorta a área de clique para ficar redonda
                            // Se o botão for totalmente redondo nas pontas, usa 32.dp (metade da altura)
                            .clip(RoundedCornerShape(32.dp))
                            .clickable(onClick = onFinish)
                    ) {
                        // 1. A IMAGEM DE FUNDO
                        Image(
                            painter = painterResource(id = R.drawable.button), // O teu png do botão
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds, // Estica a imagem para encher o botão
                            modifier = Modifier.fillMaxSize()
                        )

                        // 2. O TEXTO POR CIMA
                        Text(
                            text = "Get Started",
                            fontSize = 18.sp,
                            // Escolhe a cor que contraste com o teu botão (Branco ou Preto?)
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    // Botão Texto Simples "Next" (Seguinte)
                    TextButton(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        },
                        modifier = Modifier.height(50.dp) // Mantém a altura consistente com o botão
                    ) {
                        Text(
                            "Next",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OnboardingPageContent(page: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 48.dp), // Mantém o texto apertadinho nas laterais
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // --- GAVETA 1: ÁREA DA IMAGEM (50% do ecrã) ---
        Box(
            modifier = Modifier
                .weight(0.5f) // Ocupa metade da altura disponível
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter // Cola a imagem ao fundo desta área
        ) {
            Image(
                painter = painterResource(page.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp) // Tamanho fixo para a imagem
                    .graphicsLayer {
                        scaleX = page.imageScale
                        scaleY = page.imageScale
                    },
                contentScale = ContentScale.Fit // Garante que a imagem não é cortada
            )
        }

        // Espaço fixo entre a área da imagem e o título
        Spacer(modifier = Modifier.height(32.dp))

        // --- GAVETA 2: ÁREA DO TEXTO (Resto do ecrã) ---
        Column(
            modifier = Modifier
                .weight(0.5f) // Ocupa a outra metade
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = page.title,
                color = Color.White,
                fontSize = 24.sp, // Tamanho fixo e legível
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 32.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = page.description,
                color = Color(0xFFB0BEC5),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
        }
    }
}


