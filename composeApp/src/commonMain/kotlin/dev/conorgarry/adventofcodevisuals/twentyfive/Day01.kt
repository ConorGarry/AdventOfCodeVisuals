package dev.conorgarry.adventofcodevisuals.twentyfive

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Day01() {
    var currentRotation by remember { mutableStateOf(50f) }
    var targetRotation by remember { mutableStateOf(50f) }
    val rotationAnimation by animateFloatAsState(
        targetValue = targetRotation,
        animationSpec = tween(1000),
    )

    var zeroCount by remember { mutableStateOf(0) }

    Column {
        Canvas(
            modifier =
                Modifier
                    .size(300.dp)
                    .padding(16.dp),
        ) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val radius = size.minDimension / 2.5f

            // Draw outer circle
            drawCircle(
                color = Color.Gray,
                radius = radius,
                center = center,
                style = Stroke(width = 2.dp.toPx()),
            )

            // Draw dial points
            for (i in 0..99) {
                val angle = ((i * 3.6f) - 90F) * (PI / 180f)
                val pointRadius = if (i == 0) 8f else 4f
                val x = center.x + radius * cos(angle).toFloat()
                val y = center.y + radius * sin(angle).toFloat()

                drawCircle(
                    color = if (i == 0) Color.Red else Color.Black,
                    radius = pointRadius,
                    center = Offset(x, y),
                )
            }

            // Draw pointer
            val pointerAngle = ((rotationAnimation * 3.6f) - 90F) * (PI / 180f)
            val pointerX = center.x + radius * cos(pointerAngle).toFloat()
            val pointerY = center.y + radius * sin(pointerAngle).toFloat()

            drawLine(
                color = Color.Blue,
                start = center,
                end = Offset(pointerX, pointerY),
                strokeWidth = 3.dp.toPx(),
            )
        }
        Values(
            targetRotation.toInt(),
            zeroCount,
            rotationAnimation.toInt(),
        )
    }

    LaunchedEffect(Unit) {
        val rotations =
            listOf(
                "L68",
                "L30",
                "R48",
                "L5",
                "R60",
                "L55",
                "L1",
                "L99",
                "R14",
                "L82",
            )

        rotations.forEach { rotation ->
            val direction = rotation.first()
            val amount = rotation.substring(1).toInt()

            targetRotation =
                when (direction) {
                    'L' -> (currentRotation - amount + 100) % 100
                    'R' -> (currentRotation + amount) % 100
                    else -> currentRotation
                }
            currentRotation = targetRotation
            delay(1500)
            if (currentRotation == 0F) {
                zeroCount++
            }
        }
    }
}

@Composable
fun Values(targetRotation: Int, zeroCount: Int, angle: Int) {
    Column {
        Text("Target: $targetRotation")
        Text("Angle: $angle")
        Text("Total Zeroes: $zeroCount")
    }
}

@Preview
@Composable
fun PreviewDay01() {
    Day01()
}