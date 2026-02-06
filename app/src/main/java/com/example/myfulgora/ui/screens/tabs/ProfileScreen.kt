package com.example.myfulgora.ui.screens.tabs

import android.webkit.WebSettings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Stars
import androidx.compose.material.icons.rounded.WorkspacePremium
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfulgora.ui.components.FulgoraBackground
import com.example.myfulgora.ui.components.FulgoraInfoCard
import com.example.myfulgora.ui.components.FulgoraTopBar
import com.example.myfulgora.ui.theme.AppIcons
import com.example.myfulgora.ui.theme.Dimens
import com.example.myfulgora.ui.theme.GreenFresh
import androidx.compose.foundation.Image
import com.example.myfulgora.ui.theme.White

@Composable
fun ProfileScreen(
    onMenuClick: () -> Unit = {}
) {
    FulgoraBackground {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val screenW = maxWidth
            val iconSize = screenW * Dimens.IconScaleRatio
            val paddingSide = screenW * Dimens.SideMarginRatio

            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = paddingSide)
                    .padding(top = Dimens.TopPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 1. TOP BAR
                FulgoraTopBar(
                    iconSize = iconSize,
                    onMenuClick = onMenuClick
                )

                Spacer(modifier = Modifier.height(Dimens.PaddingExtraLarge))

                // 2. HEADER TEXT
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Profile",
                        fontSize = Dimens.TextSizeHeader,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                }

                Spacer(modifier = Modifier.height(Dimens.PaddingLarge))

                // 3. NOME/EMAIL/FOTO
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(Dimens.PaddingMedium))
                    Spacer(modifier = Modifier.height(Dimens.PaddingMedium))
                    Icon(
                        painter = painterResource(id = AppIcons.Profile.profile),
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier.size(90.dp)
                    )
                    Spacer(modifier = Modifier.width(Dimens.PaddingMedium))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "John Doe",
                            color = White,
                            fontSize = 24.sp
                        )
                        Text(
                            text = "johndoe@gmail.com",
                            color = Color.Gray.copy(alpha = 0.7f),
                            fontSize = 20.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Dimens.PaddingMedium))

                // 4. QUAL A TUA MOTA
                FulgoraInfoCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "AJP BIKE NAME",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Connected",
                                color = Color.Gray.copy(alpha = 0.7f),
                                fontSize = 12.sp
                            )
                            Text(
                                text = "VIM",
                                color = Color.Gray.copy(alpha = 0.7f),
                                fontSize = 12.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(Dimens.PaddingMedium))
                        Image(
                            painter = painterResource(id = AppIcons.Dashboard.MainBike),
                            contentDescription = null,
                            modifier = Modifier.size(80.dp)
                        )
                        Icon(
                            painter = painterResource(id = AppIcons.Dashboard.ArrowRight0),
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // 5. ADD/REMOVE MOTORCYCLE
                FulgoraInfoCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Add/Remove Motorcycle",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Icon(
                            painter = painterResource(id = AppIcons.Dashboard.ArrowRight0),
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Dimens.PaddingMedium))

                // 6. ACCOUNT
                FulgoraInfoCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Account",
                            color = GreenFresh,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(Dimens.PaddingMedium))

                    val rewards = listOf("Edit Name", "Edit Email", "Change Password")
                    rewards.forEachIndexed { index, reward ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = reward, color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                            Icon(
                                painter = painterResource(id = AppIcons.Dashboard.ArrowRight0),
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        if (index < rewards.size - 1) {
                            HorizontalDivider(color = Color.Gray.copy(alpha = 0.1f))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(Dimens.PaddingMedium))


                // 7. SUPPORT
                FulgoraInfoCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Support",
                            color = GreenFresh,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(Dimens.PaddingMedium))

                    val rewards = listOf("Assistence", "Dealership COntact", "More to Come")
                    rewards.forEachIndexed { index, reward ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = reward, color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                            Icon(
                                painter = painterResource(id = AppIcons.Dashboard.ArrowRight0),
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        if (index < rewards.size - 1) {
                            HorizontalDivider(color = Color.Gray.copy(alpha = 0.1f))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(Dimens.ScrollBottomPadding))
            }
        }
    }
}