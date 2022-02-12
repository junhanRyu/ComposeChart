package com.luckyhan.studio.composechart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luckyhan.studio.composechart.ui.theme.ComposeChartTheme
import com.luckyhan.studio.library.charts.BarChart
import com.luckyhan.studio.library.charts.BarModel
import com.luckyhan.studio.library.charts.LineChart
import com.luckyhan.studio.library.charts.LineChartModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeChartTheme {
                // A surface container using the 'background' color from the theme
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    Scaffold() {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
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
            val lines = listOf(
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
            Spacer(modifier = Modifier.height(16.dp))
            BarChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .padding(horizontal = 16.dp),
                barModels = bars,
                axisStrokeWidth = 4f,
                labelSize = 48f,
                labelVerticalPadding = 10f,
                minValue = 0,
                maxValue = 600,
                countOfGuidelines = 3,
                yAxis = false,
            )

            Spacer(modifier = Modifier.height(16.dp))
            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .padding(horizontal = 16.dp),
                lineChartModels = lines,
                axisStrokeWidth = 6f,
                lineColor = MaterialTheme.colors.secondary,
                pointRadius = 12f,
                labelSize = 48f,
                labelVerticalPadding = 12f,
                minValue = 0,
                maxValue = 600
            )
        }
    }
}

@Composable
@Preview
fun AppPreview() {
    ComposeChartTheme() {
        // A surface container using the 'background' color from the theme
        MyApp()
    }
}