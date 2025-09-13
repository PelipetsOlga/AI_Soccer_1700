package com.manager1700.soccer.ui.components

import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.*

/**
 * Remember new text style with automatically resizable font size and line height.
 * Should be used inside [androidx.compose.foundation.layout.BoxWithConstraints] to be able to fetch [BoxWithConstraintsScope.constraints] for font size computation.
 *
 * Already have [AutoSizeLineText] and [AutoSizeText] who use it.
 * And you can also make your own implementation for example for [androidx.compose.material.TextField].
 *
 * Example with TextField:
 * ```kotlin
 * BoxWithConstrains{
 *     val textStyle = rememberAutoSizedTextStyle(
 *              text = text,
 *              textStyle = MaterialTheme.typography.h3,
 *              maxLines = 1
 *           )
 *
 *     TextField(
 *        text = text,
 *        textStyle = textStyle,
 *        maxLines = 1,
 *     )
 * }
 * ```
 *
 * See [Original](https://gist.github.com/dovahkiin98/cd4e1f639cc4f6392018e327d0db44d8)
 *
 * @param text text that is displayed on the screen and could be cropped if it too long
 * @param textStyle original text style of composable that is displaying [text].
 * If [text] could be displayed without overflowing than this text style will be used,
 * if text is overflowing thant this text style will be used but with another [TextStyle.fontSize] and [TextStyle.lineHeight]
 * @param maxLines maximum amount of lines that is acceptable to be displayed.
 * If text could take more thant this [maxLines] than [TextStyle.fontSize] and [TextStyle.lineHeight] will be reduced to fit this [maxLines]
 * @param minFontSize minimum font size that could be applied to the text
 * @return new text style with changed [TextStyle.fontSize] and [TextStyle.lineHeight] so it could fit to [maxLines]
 */
@Deprecated("use rememberAutoSizedTextStyle() with passing constraints explisitly")
@Composable
fun BoxWithConstraintsScope.rememberAutoSizedTextStyle(
    text: String,
    textStyle: TextStyle,
    maxLines: Int,
    minFontSize: TextUnit = 10.sp,
): TextStyle {
    val annotatedText = remember(text) { AnnotatedString(text) }

    return rememberAutoSizedTextStyle(
        text = annotatedText,
        textStyle = textStyle,
        maxLines = maxLines,
        minFontSize = minFontSize,
    )
}

/**
 * Remember new text style with automatically resizable font size and line height.
 * Should be used inside [androidx.compose.foundation.layout.BoxWithConstraints] to be able to fetch [BoxWithConstraintsScope.constraints] for font size computation.
 *
 * Already have [AutoSizeLineText] and [AutoSizeText] who use it.
 * And you can also make your own implementation for example for [androidx.compose.material.TextField].
 *
 * Example with TextField:
 * ```kotlin
 * BoxWithConstrains{
 *     val textStyle = rememberAutoSizedTextStyle(
 *              text = text,
 *              textStyle = MaterialTheme.typography.h3,
 *              maxLines = 1
 *           )
 *
 *     TextField(
 *        text = text,
 *        textStyle = textStyle,
 *        maxLines = 1,
 *     )
 * }
 * ```
 *
 * See [Original](https://gist.github.com/dovahkiin98/cd4e1f639cc4f6392018e327d0db44d8)
 *
 * @param text text that is displayed on the screen and could be cropped if it too long
 * @param textStyle original text style of composable that is displaying [text].
 * If [text] could be displayed without overflowing than this text style will be used,
 * if text is overflowing thant this text style will be used but with another [TextStyle.fontSize] and [TextStyle.lineHeight]
 * @param maxLines maximum amount of lines that is acceptable to be displayed.
 * If text could take more thant this [maxLines] than [TextStyle.fontSize] and [TextStyle.lineHeight] will be reduced to fit this [maxLines]
 * @param minFontSize minimum font size that could be applied to the text
 * @param resizeLineHeight enable resizing of line height relative to resized font
 * @return new text style with changed [TextStyle.fontSize] and [TextStyle.lineHeight] so it could fit to [maxLines]
 */
@Deprecated("use rememberAutoSizedTextStyle() with passing constraints explisitly")
@Composable
fun BoxWithConstraintsScope.rememberAutoSizedTextStyle(
    text: AnnotatedString,
    textStyle: TextStyle,
    maxLines: Int,
    minFontSize: TextUnit = 10.sp,
    resizeLineHeight: Boolean = true,
): TextStyle {
    return rememberAutoSizedTextStyle(
        text = text,
        textStyle = textStyle,
        maxLines = maxLines,
        constraints = constraints,
        minFontSize = minFontSize,
        resizeLineHeight = resizeLineHeight,
    )
}

/**
 * Remember new text style with automatically resizable font size and line height.
 * Should be used inside [androidx.compose.foundation.layout.BoxWithConstraints] to be able to fetch [BoxWithConstraintsScope.constraints] for font size computation.
 *
 * Already have [AutoSizeLineText] and [AutoSizeText] who use it.
 * And you can also make your own implementation for example for [androidx.compose.material.TextField].
 *
 * Example with TextField:
 * ```kotlin
 * BoxWithConstrains{
 *     val textStyle = rememberAutoSizedTextStyle(
 *              text = text,
 *              textStyle = MaterialTheme.typography.h3,
 *              maxLines = 1
 *           )
 *
 *     TextField(
 *        text = text,
 *        textStyle = textStyle,
 *        maxLines = 1,
 *     )
 * }
 * ```
 *
 * See [Original](https://gist.github.com/dovahkiin98/cd4e1f639cc4f6392018e327d0db44d8)
 *
 * @param text text that is displayed on the screen and could be cropped if it too long
 * @param textStyle original text style of composable that is displaying [text].
 * If [text] could be displayed without overflowing than this text style will be used,
 * if text is overflowing thant this text style will be used but with another [TextStyle.fontSize] and [TextStyle.lineHeight]
 * @param maxLines maximum amount of lines that is acceptable to be displayed.
 * If text could take more thant this [maxLines] than [TextStyle.fontSize] and [TextStyle.lineHeight] will be reduced to fit this [maxLines]
 * @param minFontSize minimum font size that could be applied to the text
 * @param resizeLineHeight enable resizing of line height relative to resized font
 * @param constraints how wide and tall the text is allowed to be. [Constraints.maxWidth]
 * will define the width of the MultiParagraph. [Constraints.maxHeight] helps defining the
 * number of lines that fit with ellipsis is true. [Constraints.minWidth] defines the minimum
 * width the resulting [TextLayoutResult.size] will report. [Constraints.minHeight] is no-op.
 * @return new text style with changed [TextStyle.fontSize] and [TextStyle.lineHeight] so it could fit to [maxLines]
 */
@Composable
fun rememberAutoSizedTextStyle(
    text: String,
    textStyle: TextStyle,
    maxLines: Int,
    constraints: Constraints,
    minFontSize: TextUnit = 10.sp,
    resizeLineHeight: Boolean = true,
): TextStyle {
    val annotatedText = remember(text) { AnnotatedString(text) }

    return rememberAutoSizedTextStyle(
        text = annotatedText,
        textStyle = textStyle,
        maxLines = maxLines,
        constraints = constraints,
        minFontSize = minFontSize,
        resizeLineHeight = resizeLineHeight,
    )
}

/**
 * Remember new text style with automatically resizable font size and line height.
 * Should be used inside [androidx.compose.foundation.layout.BoxWithConstraints] to be able to fetch [BoxWithConstraintsScope.constraints] for font size computation.
 *
 * Already have [AutoSizeLineText] and [AutoSizeText] who use it.
 * And you can also make your own implementation for example for [androidx.compose.material.TextField].
 *
 * Example with TextField:
 * ```kotlin
 * BoxWithConstrains{
 *     val textStyle = rememberAutoSizedTextStyle(
 *              text = text,
 *              textStyle = MaterialTheme.typography.h3,
 *              maxLines = 1
 *           )
 *
 *     TextField(
 *        text = text,
 *        textStyle = textStyle,
 *        maxLines = 1,
 *     )
 * }
 * ```
 *
 * See [Original](https://gist.github.com/dovahkiin98/cd4e1f639cc4f6392018e327d0db44d8)
 *
 * @param text text that is displayed on the screen and could be cropped if it too long
 * @param textStyle original text style of composable that is displaying [text].
 * If [text] could be displayed without overflowing than this text style will be used,
 * if text is overflowing thant this text style will be used but with another [TextStyle.fontSize] and [TextStyle.lineHeight]
 * @param maxLines maximum amount of lines that is acceptable to be displayed.
 * If text could take more thant this [maxLines] than [TextStyle.fontSize] and [TextStyle.lineHeight] will be reduced to fit this [maxLines]
 * @param minFontSize minimum font size that could be applied to the text
 * @param resizeLineHeight enable resizing of line height relative to resized font
 * @param constraints how wide and tall the text is allowed to be. [Constraints.maxWidth]
 * will define the width of the MultiParagraph. [Constraints.maxHeight] helps defining the
 * number of lines that fit with ellipsis is true. [Constraints.minWidth] defines the minimum
 * width the resulting [TextLayoutResult.size] will report. [Constraints.minHeight] is no-op.
 * @return new text style with changed [TextStyle.fontSize] and [TextStyle.lineHeight] so it could fit to [maxLines]
 */
@Composable
fun rememberAutoSizedTextStyle(
    text: AnnotatedString,
    textStyle: TextStyle,
    maxLines: Int,
    constraints: Constraints,
    minFontSize: TextUnit = 10.sp,
    resizeLineHeight: Boolean = true,
): TextStyle {
    val safeMinFontSize =
        minFontSize.takeIf { it.isSpecified && it < textStyle.fontSize } ?: textStyle.fontSize

    val textMeasurer = rememberTextMeasurer()

    return remember(resizeLineHeight, text, safeMinFontSize, maxLines, textStyle, constraints) {
        var newStyle = textStyle

        fun shouldShrink(): Boolean {
            val layout = textMeasurer.measure(
                constraints = constraints,
                text = text,
                style = newStyle,
                maxLines = maxLines,
            )
            return layout.hasVisualOverflow
        }

        var prevTextStyle = newStyle

        fun findAndSetOptimalFontSize(fontSizeStep: TextUnit) {
            while (shouldShrink() && newStyle.fontSize > minFontSize) {
                prevTextStyle = newStyle
                newStyle = textStyle.copy(
                    fontSize = (newStyle.fontSize.value - fontSizeStep.value).sp,
                )
            }
        }

        // Find optimal font size with font size iteration 1.sp
        findAndSetOptimalFontSize(fontSizeStep = 1.sp)

        // If font size was changed try to find ever more optimal font size with font size iteration 0.1.sp
        if (prevTextStyle.fontSize != newStyle.fontSize) {
            newStyle = prevTextStyle

            findAndSetOptimalFontSize(fontSizeStep = 0.1.sp)
        }

        return@remember if (resizeLineHeight && newStyle.fontSize != textStyle.fontSize) {
            val newLineHeight = if (textStyle.lineHeight.isSpecified) {
                newStyle.fontSize.value / textStyle.fontSize.value * textStyle.lineHeight
            } else {
                textStyle.lineHeight
            }
            newStyle.copy(lineHeight = newLineHeight)
        } else {
            newStyle
        }
    }
}
