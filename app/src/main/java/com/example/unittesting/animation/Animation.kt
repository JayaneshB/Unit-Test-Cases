package com.example.unittesting.animation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.Dp

@Composable
fun sizeAnimationMethod(targetValue: Dp, timeInMillis: Int): Dp {
    val size by animateDpAsState(
        targetValue = targetValue,
        tween(
            durationMillis = timeInMillis,
            easing = LinearOutSlowInEasing
        )
    )

    return size

}


@Composable
fun animateInAsState(targetValue: Int, durationMillis: Int): Int {

    val newValue by animateIntAsState(targetValue, animationSpec = tween(durationMillis))
    return newValue

}

@Composable
fun ssAnimateFloatAsState(targetValue: Float, durationMillis: Int): Float {
    val newValue by animateFloatAsState(targetValue, animationSpec = tween(durationMillis))
    return newValue
}

@Composable
fun ssRepeatedDpAnimation(initialValue: Dp, targetValue: Dp, durationMillis: Int): Dp {
    val repeatedDp by rememberInfiniteTransition().animateValue(
        initialValue = initialValue,
        targetValue = targetValue,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    return repeatedDp
}