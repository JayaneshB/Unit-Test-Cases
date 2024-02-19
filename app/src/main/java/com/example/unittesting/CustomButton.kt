package com.example.unittesting

import android.util.Log
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unittesting.animation.animateInAsState
import com.example.unittesting.animation.sizeAnimationMethod
import com.example.unittesting.enumeration.ButtonState
import kotlinx.coroutines.delay

@Composable
fun ComposeCustomButton(
    onClick: () -> Unit,
    buttonState: ButtonState,
    buttonText: String,
    width:Dp,
    height:Dp,
    enabled: Boolean = true,
    cornerRadius: Int = 100
) {

    var isButtonEnabled by remember { mutableStateOf(enabled) }
    var buttonWidth by remember { mutableStateOf(width) }
    var buttonHeight by remember { mutableStateOf(height) }
    var alphaValue by remember { mutableFloatStateOf(1f) }
    val previousButtonState by remember { mutableStateOf(ButtonState.Idle) }
    var radius by remember { mutableIntStateOf(cornerRadius) }
    var showLoader by remember { mutableStateOf(false) }

    when(buttonState) {
        ButtonState.Idle -> {
            if(height > width)
                buttonHeight = height
            else
                buttonWidth = width
            alphaValue = 1f
            radius = cornerRadius
            showLoader = false
            isButtonEnabled = enabled
        }
        ButtonState.Loading -> {
            if(previousButtonState != ButtonState.Loading) {
                showLoader = true
                LaunchedEffect(key1 = "Loading", block = {
                    if (height > width)
                        buttonHeight = width
                    else
                        buttonWidth = height
                    alphaValue = 0f
                    radius = 50

                    delay(2000)

                    if(height > width)
                        buttonHeight = height
                    else
                        buttonWidth = width
                    alphaValue = 1f
                    radius = cornerRadius
                    showLoader = false
                    isButtonEnabled = enabled
                })
            }

        }

        ButtonState.Failure -> {
            LaunchedEffect(key1 = "Failure", block ={
                if(height > width)
                    buttonHeight = width
                else
                    buttonWidth = height
                alphaValue = 0f
                radius = 50

                delay(2000)

                if(height > width)
                    buttonHeight = height
                else
                    buttonWidth = width
                alphaValue = 1f
                radius = cornerRadius
                isButtonEnabled = enabled

            })
        }

    }

    Box(
        modifier = Modifier.graphicsLayer { alpha = 1f },
        contentAlignment = Alignment.Center) {

        Button(
            onClick = {
                Log.e("Button","Clicked")
                if (isButtonEnabled) {
                    isButtonEnabled = false
                    onClick()
                }
                isButtonEnabled = true
            },
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFF0071E7)),
            modifier = Modifier
                .padding(12.dp)
                .clickable(enabled = isButtonEnabled,
                    indication = if (enabled) LocalIndication.current else null,
                    interactionSource = remember { MutableInteractionSource() }) {}
                .size(
                    width = sizeAnimationMethod(targetValue = buttonWidth, timeInMillis = 1000),
                    height = sizeAnimationMethod(targetValue = buttonHeight, timeInMillis = 1000)
                ),
            shape = RoundedCornerShape(
                animateInAsState(
                    targetValue = radius,
                    durationMillis = 1000
                )
            )
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                if (showLoader) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(top = 13.dp)
                            .graphicsLayer(alpha = 0.5f)
                            .align(Alignment.CenterVertically),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                }

                Text(
                    text = buttonText,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(12.dp)
                        .weight(1f),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Medium,
                        fontStyle = FontStyle.Normal,
                        color = Color.White
                    )
                )
            }
        }

    }
}

