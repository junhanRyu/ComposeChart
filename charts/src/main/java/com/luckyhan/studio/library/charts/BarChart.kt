package com.luckyhan.studio.library.charts

import android.graphics.Paint
import android.graphics.Rect
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class BarModel(
    val value: Int,
    val label: String,
    val color: Color
)

@Composable
fun BarChart(
    modifier: Modifier = Modifier,
    axisStrokeWidth: Float = 2f,
    xAxisColor: Color = MaterialTheme.colors.onSurface.copy(
        alpha = 0.2f
    ),
    yAxisColor: Color = MaterialTheme.colors.onSurface.copy(
        alpha = 0.2f
    ),
    guidelineColor: Color = MaterialTheme.colors.onSurface.copy(
        alpha = 0.2f
    ),
    barPaddingRatio: Float = 0.5f,
    barsSpaceRatio: Float = 0.90f,
    labelSize: Float = 16f,
    labelVerticalPadding: Float = 8f,
    countOfGuidelines: Int = 0,
    xAxis : Boolean = true,
    yAxis : Boolean = true,
    xLabel: Boolean = true,
    xLabelColor: Color = MaterialTheme.colors.onSurface,
    yLabelColor: Color = MaterialTheme.colors.onSurface,
    minValue: Int = 0,
    maxValue: Int = 100,
    barModels: List<BarModel> = emptyList()
) {
    Canvas(modifier = modifier) {
        val barSpaceWidth = size.width * barsSpaceRatio
        val barSpaceHeight = size.height * barsSpaceRatio
        val otherSpaceWidth = size.width - barSpaceWidth
        val otherSpaceHeight = size.height - barSpaceHeight
        val barSpaceWidthOffset = otherSpaceWidth


        val lineOffset = (axisStrokeWidth / 2)
        val totalPaddingSize = barSpaceWidth * barPaddingRatio
        val totalBarsSize = barSpaceWidth * (1.0 - barPaddingRatio)
        val barPadding =
            if (barModels.isNotEmpty()) (totalPaddingSize / (barModels.size + 1)) else 0f
        val barWidth =
            if (barModels.isNotEmpty()) (totalBarsSize / barModels.size).toFloat() else 0f
        val barMaxValue = barModels.maxByOrNull { it.value }?.value ?: 1
        val barHeightRatio = barSpaceHeight / maxValue

        val xPaint = Paint()
        xPaint.textAlign = Paint.Align.CENTER
        xPaint.textSize = labelSize
        xPaint.color = xLabelColor.toArgb()
        xPaint.isAntiAlias = true

        val yPaint = Paint()
        yPaint.textAlign = Paint.Align.CENTER
        yPaint.textSize = labelSize
        yPaint.color = yLabelColor.toArgb()
        yPaint.isAntiAlias = true

        if (countOfGuidelines > 0) {
            //guidelines

            val valueOffset = maxValue / (countOfGuidelines + 1)
            val yPositionOffset = barSpaceHeight / (countOfGuidelines + 1)
            var guidelineValue = maxValue - valueOffset
            var yPosition = yPositionOffset
            while (guidelineValue > 0) {
                drawIntoCanvas {
                    val rect = Rect()
                    yPaint.getTextBounds(
                        guidelineValue.toString(),
                        0,
                        minValue.toString().length,
                        rect
                    )
                    it.nativeCanvas.drawText(
                        guidelineValue.toString(),
                        (otherSpaceWidth) / 2f - axisStrokeWidth,
                        yPosition,
                        yPaint
                    )
                }

                // x axis
                drawLine(
                    guidelineColor,
                    start = Offset(otherSpaceWidth - lineOffset, yPosition + lineOffset),
                    end = Offset(size.width, yPosition + lineOffset),
                    strokeWidth = axisStrokeWidth,
                )
                guidelineValue -= valueOffset
                yPosition += yPositionOffset
            }
        }

        barModels.forEachIndexed { index, barModel ->
            val barHeight = (barModel.value * barHeightRatio)
            val yOffset = barSpaceHeight - barHeight
            val xOffset =
                ((index + 1) * barPadding.toFloat()) + (index * barWidth) + otherSpaceWidth
            drawRect(barModel.color, Offset(xOffset, yOffset), Size(barWidth, barHeight))

            if (xLabel) {
                drawIntoCanvas {
                    val rect = Rect()
                    xPaint.getTextBounds(barModel.label, 0, barModel.label.length, rect)
                    val textWidth = rect.width()
                    val textHeight = rect.height()
                    it.nativeCanvas.drawText(
                        barModel.label,
                        xOffset + (barWidth / 2f),
                        barSpaceHeight + textHeight + axisStrokeWidth + labelVerticalPadding,
                        xPaint
                    )
                }
            }
        }

        //max
        drawIntoCanvas {
            val rect = Rect()
            yPaint.getTextBounds(maxValue.toString(), 0, maxValue.toString().length, rect)
            val textHeight = rect.height()
            it.nativeCanvas.drawText(
                maxValue.toString(),
                (otherSpaceWidth) / 2f - axisStrokeWidth,
                textHeight.toFloat() + 4f,
                yPaint
            )
        }

        //min
        drawIntoCanvas {
            val rect = Rect()
            yPaint.getTextBounds(minValue.toString(), 0, minValue.toString().length, rect)
            it.nativeCanvas.drawText(
                minValue.toString(),
                (otherSpaceWidth) / 2f - axisStrokeWidth,
                barSpaceHeight,
                yPaint
            )
        }






        if(xAxis){
            // x axis
            drawLine(
                xAxisColor,
                start = Offset(otherSpaceWidth - lineOffset, barSpaceHeight + lineOffset),
                end = Offset(size.width, barSpaceHeight + lineOffset),
                strokeWidth = axisStrokeWidth,
            )
        }

        if(yAxis){
            // y axis
            drawLine(
                yAxisColor,
                start = Offset(otherSpaceWidth - axisStrokeWidth, 0f),
                end = Offset(otherSpaceWidth - axisStrokeWidth, barSpaceHeight + axisStrokeWidth),
                strokeWidth = axisStrokeWidth,
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun BarsPreview() {
    val bars = listOf(
        BarModel(60, "1", MaterialTheme.colors.primary),
        BarModel(20, "2", MaterialTheme.colors.primary),
        BarModel(100, "3", MaterialTheme.colors.primary),
        BarModel(200, "4", MaterialTheme.colors.primary),
        BarModel(80, "5", MaterialTheme.colors.primary),
        BarModel(100, "6", MaterialTheme.colors.primary),
        BarModel(500, "7", MaterialTheme.colors.primary),
        BarModel(300, "8", MaterialTheme.colors.primary),
        BarModel(200, "9", MaterialTheme.colors.primary),
    )
    BarChart(modifier = Modifier.size(200.dp), barModels = bars, yAxis = false, minValue = 0, maxValue = 600, countOfGuidelines = 3)
}