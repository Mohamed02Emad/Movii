import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import com.mo.movie.core.utils.logit

enum class ButtonState { Pressed, Idle }

fun Modifier.bounceClick(shape: Shape = RoundedCornerShape(0.dp)) = composed {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(
        if (buttonState == ButtonState.Pressed) @Suppress("MagicNumber") 0.70f else 1f,
        label = "",
    )
    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = { },
        )
        .clip(shape)
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.Pressed) {
                    waitForUpOrCancellation()
                    ButtonState.Idle
                } else {
                    awaitFirstDown(false)
                    ButtonState.Pressed
                }
            }
        }
}


fun Modifier.verticalScrollListener(initialScrollPercentage : Float = 1f , onScrollValueChanged : (Float)->Unit): Modifier {
    return this.then(
        Modifier.pointerInput(Unit) {
            val MIN_SCROLL_THRESHOLD = 40.dp
            val COLLAPSED_RANGE = 75.dp - MIN_SCROLL_THRESHOLD
            var currentScrollValue = initialScrollPercentage
            while (true) {
                val dragOffset = awaitPointerEventScope {
                    val touchPoint = awaitPointerEvent()
                    val previousY = touchPoint.changes.last().previousPosition.y
                    val event = awaitPointerEvent()
                    val currentY = event.changes.last().position.y
                    currentY - previousY
                }
                if (Math.abs(dragOffset) > MIN_SCROLL_THRESHOLD.value) {
                    currentScrollValue = when {
                        dragOffset > 0f -> Math.min(
                            currentScrollValue + dragOffset / COLLAPSED_RANGE.value,
                            1.0f
                        )

                        dragOffset < 0f -> Math.max(
                            currentScrollValue - Math.abs(
                                dragOffset
                            ) / COLLAPSED_RANGE.value, 0.0f
                        )

                        else -> currentScrollValue
                    }
                    onScrollValueChanged(currentScrollValue)
                }
            }
        },

        )
}

fun Modifier.collapseHeightBy(percentage: Float): Modifier {
    return this.then(
        Modifier.layout { measurable, constraints ->
            val placeable = measurable.measure(constraints)
            val collapsedHeight = (placeable.height * percentage).toInt()
            layout(placeable.width, collapsedHeight) {
                placeable.placeRelative(0, 0)
            }
        }
    )
}

@Composable
fun CollapsibleHeightBox(percentage: Float, content: @Composable () -> Unit) {
    Box(modifier = Modifier.collapseHeightBy(percentage)) {
        content()
    }
}
