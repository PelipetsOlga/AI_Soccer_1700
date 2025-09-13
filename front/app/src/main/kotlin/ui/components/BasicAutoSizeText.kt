package com.manager1700.soccer.ui.components

import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * [Text] with automatically change font size to fit [maxLines].
 *
 * @param text The text to be displayed.
 * @param modifier [Modifier] to apply to this layout node.
 * @param softWrap Whether the text should break at soft line breaks. If false, the glyphs in the
 * text will be positioned as if there was unlimited horizontal space. If [softWrap] is false,
 * [overflow] and TextAlign may have unexpected effects.
 * @param maxLines An optional maximum number of lines for the text to span, wrapping if
 * necessary.  If it is not null, then it must be greater than 1.
 * @param style Style configuration for the text such as color, font, line height etc.
 * @param minFontSize minimum allowed font size of the text.
 * @param resizeLineHeight enable resizing of line height relative to resized font
 */
@Composable
fun BasicAutoSizeText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    minFontSize: TextUnit = 10.sp,
    resizeLineHeight: Boolean = true,
) {
    BasicAutoSizeText(
        text = AnnotatedString(text = text),
        modifier = modifier,
        style = style,
        onTextLayout = onTextLayout,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        inlineContent = inlineContent,
        minFontSize = minFontSize,
        resizeLineHeight = resizeLineHeight,
    )
}

/**
 * [Text] with automatically change font size to fit [maxLines].
 *
 * @param text The text to be displayed.
 * @param modifier [Modifier] to apply to this layout node.
 * @param softWrap Whether the text should break at soft line breaks. If false, the glyphs in the
 * text will be positioned as if there was unlimited horizontal space. If [softWrap] is false,
 * [overflow] and TextAlign may have unexpected effects.
 * @param maxLines An optional maximum number of lines for the text to span, wrapping if
 * necessary.  If it is not null, then it must be greater than 1.
 * @param style Style configuration for the text such as color, font, line height etc.
 * @param minFontSize minimum allowed font size of the text.
 * @param inlineContent A map store composables that replaces certain ranges of the text. It's
 * used to insert composables into text layout. Check [InlineTextContent] for more information.
 * @param resizeLineHeight enable resizing of line height relative to resized font
 */
@Composable
fun BasicAutoSizeText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    minFontSize: TextUnit = 10.sp,
    resizeLineHeight: Boolean = true,
) {
    val textConstraints = remember { mutableStateOf(Constraints()) }
    val autoSizedTextStyle = rememberAutoSizedTextStyle(
        text = text,
        textStyle = style,
        maxLines = maxLines,
        minFontSize = minFontSize,
        resizeLineHeight = resizeLineHeight,
        constraints = textConstraints.value,
    )

    Layout(
        modifier = modifier,
        content = {
            BasicText(
                text = text,
                overflow = overflow,
                softWrap = softWrap,
                maxLines = maxLines,
                minLines = minLines,
                inlineContent = inlineContent,
                onTextLayout = onTextLayout,
                style = autoSizedTextStyle,
            )
        },
    ) { children, constraints ->
        textConstraints.value = Constraints(maxWidth = constraints.maxWidth)

        val placeable = children[0].measure(constraints)
        layout(width = placeable.measuredWidth, height = placeable.measuredHeight) {
            placeable.placeRelative(0, 0)
        }
    }
}
