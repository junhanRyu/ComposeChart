package com.luckyhan.studio.library.charts

import android.graphics.Paint
import android.graphics.Rect
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class LineChartModel(
    val value: Int,
    val label: String,
    val color: Color
)

@Composable
fun LineChart(
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
    lineColor: Color = MaterialTheme.colors.onSurface.copy(
        alpha = 0.2f
    ),
    pointPaddingRatio: Float = 0.5f,
    pointRadius: Float = 5f,
    pointStyle: DrawStyle = Fill,
    dataSpaceRatio: Float = 0.90f,
    labelSize: Float = 16f,
    labelVerticalPadding: Float = 8f,
    minValue: Int = 0,
    maxValue: Int = 100,
    countOfGuidelines: Int = 0,
    xAxis : Boolean = true,
    yAxis : Boolean = true,
    xLabel: Boolean = true,
    xLabelColor: Color = MaterialTheme.colors.onSurface,
    yLabelColor: Color = MaterialTheme.colors.onSurface,
    lineChartModels: List<LineChartModel> = emptyList()
) {
    Canvas(modifier = modifier) {
        val barSpaceWidth = size.width * dataSpaceRatio
        val barSpaceHeight = size.height * dataSpaceRatio
        val otherSpaceWidth = size.width - barSpaceWidth
        val otherSpaceHeight = size.height - barSpaceHeight
        val barSpaceWidthOffset = otherSpaceWidth


        val lineOffset = (axisStrokeWidth / 2)
        val totalPaddingSize = barSpaceWidth * pointPaddingRatio
        val totalBarsSize = barSpaceWidth * (1.0 - pointPaddingRatio)
        val barPadding =
            if (lineChartModels.isNotEmpty()) (totalPaddingSize / (lineChartModels.size + 1)) else 0f
        val barWidth =
            if (lineChartModels.isNotEmpty()) (totalBarsSize / lineChartModels.size).toFloat() else 0f
        val barMaxValue = lineChartModels.maxByOrNull { it.value }?.value ?: 1
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

        lineChartModels.forEachIndexed { index, lineModel ->
            val barHeight = (lineModel.value * barHeightRatio)
            val yOffset = barSpaceHeight - barHeight
            val xOffset =
                ((index + 1) * barPadding.toFloat()) + (index * barWidth) + otherSpaceWidth

            if (index < lineChartModels.size - 1) {
                val nextLineModel = lineChartModels[index + 1]
                val nextLineHeight = (nextLineModel.value * barHeightRatio)
                val nextYOffset = barSpaceHeight - nextLineHeight
                val nextXOffset =
                    ((index + 2) * barPadding.toFloat()) + ((index + 1) * barWidth) + otherSpaceWidth
                drawLine(
                    color = lineColor,
                    start = Offset(xOffset + (barWidth / 2f), yOffset),
                    end = Offset(nextXOffset + (barWidth / 2f), nextYOffset),
                    strokeWidth = axisStrokeWidth,
                )
            }

            drawCircle(
                color = lineModel.color,
                radius = pointRadius,
                center = Offset(xOffset + (barWidth / 2f), yOffset),
                style = pointStyle
            )

            if(xLabel){
                drawIntoCanvas {
                    val rect = Rect()
                    xPaint.getTextBounds(lineModel.label, 0, lineModel.label.length, rect)
                    val textHeight = rect.height()
                    it.nativeCanvas.drawText(
                        lineModel.label,
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
fun LineChartPreview() {
    val bars = listOf(
        LineChartModel(60, "1", MaterialTheme.colors.primary),
        LineChartModel(20, "2", MaterialTheme.colors.primary),
        LineChartModel(100, "3", MaterialTheme.colors.primary),
        LineChartModel(200, "4", MaterialTheme.colors.primary),
        LineChartModel(80, "5", MaterialTheme.colors.primary),
        LineChartModel(100, "6", MaterialTheme.colors.primary),
        LineChartModel(500, "7", MaterialTheme.colors.primary),
        LineChartModel(300, "8", MaterialTheme.colors.primary),
        LineChartModel(200, "9", MaterialTheme.colors.primary),
    )
    LineChart(
        modifier = Modifier.size(200.dp),
        lineChartModels = bars,
        minValue = 0,
        maxValue = 600,
        countOfGuidelines = 5,
        yAxis = false,
        xLabel = false
    )
}