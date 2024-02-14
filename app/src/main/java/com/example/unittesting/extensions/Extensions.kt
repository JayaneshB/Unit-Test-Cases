package com.example.unittesting.extensions

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import com.example.unittesting.enumeration.State


fun Modifier.pulsateClick() = composed {
    var buttonState by  remember { mutableStateOf( State.Idle) }
    //Animate the  state
    val scale by animateFloatAsState(targetValue = if(buttonState == State.Pressed) 0.5f else 1f)
    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == State.Pressed) {
                    waitForUpOrCancellation()
                    State.Idle
                } else {
                    awaitFirstDown()
                    State.Pressed
                }
            }
        }
        .clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }) { }
}

fun Modifier.pressClick() = composed {

    var buttonState by remember {
        mutableStateOf(State.Idle)
    }

    val scale by animateFloatAsState(targetValue = if(buttonState == State.Pressed) 0f else -20f)

    this
        .graphicsLayer {
            translationY = scale
        }
        .clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }) { }
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == State.Pressed) {
                    waitForUpOrCancellation()
                    State.Idle
                } else {
                    awaitFirstDown(false)
                    State.Pressed
                }
            }
        }
        .clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }) { }
}

fun Modifier.shakeEffect() = composed {

    var state by remember { mutableStateOf(State.Idle) }

    val trans by animateFloatAsState(
        targetValue = if(state == State.Pressed) 0f else -50f,
        animationSpec = repeatable(
            iterations = 3,
            animation = tween(durationMillis = 50, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    this
        .graphicsLayer {
            translationX = trans
        }
        .pointerInput(state) {
            awaitPointerEventScope {
                state = if (state == State.Pressed) {
                    waitForUpOrCancellation()
                    State.Idle
                } else {
                    awaitFirstDown(false)
                    State.Pressed
                }
            }
        }
        .clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }) { }
}