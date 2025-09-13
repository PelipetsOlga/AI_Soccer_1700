package com.manager1700.soccer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * [Text] with automatically change font size to fit [maxLines].
 *
 * @param text The text to be displayed.
 * @param modifier [Modifier] to apply to this layout node.
 * @param color [Color] to apply to the text. If [Color.Unspecified], and [style] has no color set,
 * this will be [LocalContentColor].
 * @param fontSize The size of glyphs to use when painting the text. See [TextStyle.fontSize].
 * @param fontStyle The typeface variant to use when drawing the letters (e.g., italic).
 * See [TextStyle.fontStyle].
 * @param fontWeight The typeface thickness to use when painting the text (e.g., [FontWeight.Bold]).
 * @param fontFamily The font family to be used when rendering the text. See [TextStyle.fontFamily].
 * @param letterSpacing The amount of space to add between each letter.
 * See [TextStyle.letterSpacing].
 * @param textDecoration The decorations to paint on the text (e.g., an underline).
 * See [TextStyle.textDecoration].
 * @param textAlign The alignment of the text within the lines of the paragraph.
 * See [TextStyle.textAlign].
 * @param lineHeight Line height for the [Paragraph] in [TextUnit] unit, e.g. SP or EM.
 * See [TextStyle.lineHeight].
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
fun AutoSizeText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    minFontSize: TextUnit = 10.sp,
    resizeLineHeight: Boolean = true,
) {
    AutoSizeText(
        text = AnnotatedString(text = text),
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        inlineContent = emptyMap(),
        onTextLayout = onTextLayout,
        style = style,
        minFontSize = minFontSize,
        resizeLineHeight = resizeLineHeight,
    )
}

/**
 * [Text] with automatically change font size to fit [maxLines].
 *
 * @param text The text to be displayed.
 * @param modifier [Modifier] to apply to this layout node.
 * @param color [Color] to apply to the text. If [Color.Unspecified], and [style] has no color set,
 * this will be [LocalContentColor].
 * @param fontSize The size of glyphs to use when painting the text. See [TextStyle.fontSize].
 * @param fontStyle The typeface variant to use when drawing the letters (e.g., italic).
 * See [TextStyle.fontStyle].
 * @param fontWeight The typeface thickness to use when painting the text (e.g., [FontWeight.Bold]).
 * @param fontFamily The font family to be used when rendering the text. See [TextStyle.fontFamily].
 * @param letterSpacing The amount of space to add between each letter.
 * See [TextStyle.letterSpacing].
 * @param textDecoration The decorations to paint on the text (e.g., an underline).
 * See [TextStyle.textDecoration].
 * @param textAlign The alignment of the text within the lines of the paragraph.
 * See [TextStyle.textAlign].
 * @param lineHeight Line height for the [Paragraph] in [TextUnit] unit, e.g. SP or EM.
 * See [TextStyle.lineHeight].
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
fun AutoSizeText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    minFontSize: TextUnit = 10.sp,
    resizeLineHeight: Boolean = true,
) {
    val textColor = color.takeOrElse {
        style.color.takeOrElse {
            LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
        }
    }
    val mergedStyle = style.merge(
        TextStyle(
            color = textColor,
            fontSize = fontSize,
            fontWeight = fontWeight,
            textAlign = textAlign ?: TextAlign.Start,
            lineHeight = lineHeight,
            fontFamily = fontFamily,
            textDecoration = textDecoration,
            fontStyle = fontStyle,
            letterSpacing = letterSpacing,
        ),
    )

    BasicAutoSizeText(
        text = text,
        style = mergedStyle,
        modifier = modifier,
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

internal data class SampleAutoSizeText(
    val text: String,
    val textAlign: TextAlign? = null,
    val fontSize: TextUnit = TextUnit.Unspecified,
    val lineHeight: TextUnit = TextUnit.Unspecified,
    val resizeLineHeight: Boolean = true,
    val style: TextStyle? = null,
    val minFontSize: TextUnit = 10.sp,
    val maxLines: Int,
    val modifier: Modifier = Modifier,
)

internal class SampleAutoSizeTextProvider :
    PreviewParameterProvider<SampleAutoSizeText> {

    override val values = sequenceOf(
        SampleAutoSizeText(
            text = "Hello World",
            fontSize = 20.sp,
            lineHeight = 30.sp,
            maxLines = 2,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth(),
        ),
        SampleAutoSizeText(
            text = "Hello World d jghkjdf gkjdhd kjfgh dgfgjkdhfd fhgkjdf dgdfgdfgd f dfg dfg",
            fontSize = 20.sp,
            lineHeight = 30.sp,
            maxLines = 2,
        ),
        SampleAutoSizeText(
            text = "Hello World d jghkjdf gkjdhd kjfgh dgfgjkdhfd fhgkjdf dgdfgdfgd f dfg dfg",
            maxLines = 2,
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 40.sp,
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.None,
                ),
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false,
                ),
            ),
        ),
        SampleAutoSizeText(
            text = "Hello World d jghkjdf gkjdhd kjfgh",
            fontSize = 16.sp,
            lineHeight = 20.sp,
            maxLines = 1,
        ),
        SampleAutoSizeText(
            text = "Hello World d jghkjdf gkjdhd kjfgh",
            fontSize = 16.sp,
            lineHeight = TextUnit.Unspecified,
            maxLines = 1,
        ),
        SampleAutoSizeText(
            text = "Hello World d jghkjdf gkjdhd kjfgh",
            fontSize = 20.sp,
            lineHeight = 30.sp,
            minFontSize = 18.sp,
            maxLines = 1,
        ),
        SampleAutoSizeText(
            text = "Hello World d jghkjdf gkjdhd kjfgh dfgdfgd dfdgdfgdfdg",
            minFontSize = 10.sp,
            maxLines = 1,
            resizeLineHeight = false,
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 40.sp,
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.None,
                ),
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false,
                ),
            ),
        ),
    )
}

@Preview
@Composable
private fun AutoSizeTextPreview(@PreviewParameter(SampleAutoSizeTextProvider::class) textData: SampleAutoSizeText) {
    MaterialTheme {
        Surface {
            Column(modifier = Modifier.width(300.dp)) {
                Text(
                    text = "text = \"${textData.text}\",\n fontSize = ${textData.fontSize},\n lineHeight = ${textData.lineHeight},\n maxLines = ${textData.maxLines},\n textAlign = ${textData.textAlign},\n resizeLineHeight = ${textData.resizeLineHeight},\n modifier = ${textData.modifier},\n style = ${textData.style},\n minFontSize = ${textData.minFontSize}",
                    color = Color.Blue,
                    fontSize = 12.sp,
                )

                AutoSizeText(
                    text = textData.text,
                    fontSize = textData.fontSize,
                    lineHeight = textData.lineHeight,
                    maxLines = textData.maxLines,
                    textAlign = textData.textAlign,
                    modifier = textData.modifier,
                    style = textData.style ?: LocalTextStyle.current,
                    minFontSize = textData.minFontSize,
                    resizeLineHeight = textData.resizeLineHeight,
                )
            }
        }
    }
}

@Preview
@Composable
private fun AutoSizeEditTextPreview() {
    MaterialTheme {
        Surface {
            Column(modifier = Modifier.width(300.dp)) {
                var text by remember { mutableStateOf("Hello world") }

                Text(
                    text = "text = \"$text\",\n fontSize = ${20.sp},\n lineHeight = ${30.sp},\n maxLines = 1,\n editable",
                    color = Color.Blue,
                    fontSize = 12.sp,
                )

                Row(modifier = Modifier.fillMaxWidth()) {
                    AutoSizeText(
                        text = text,
                        fontSize = 20.sp,
                        lineHeight = 30.sp,
                        maxLines = 1,
                        modifier = Modifier.weight(weight = 1F, fill = false),
                    )
                    Box(modifier = Modifier
                        .size(50.dp)
                        .background(Color.Blue))
                }
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
