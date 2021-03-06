# ComposeChart

This is a simple chart library for android compose.


* How to

Step 1. Add the JitPack repository to your build file
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Step 2. Add the dependency
```
dependencies {
	        implementation 'com.github.junhanRyu:ComposeChart:Tag'
	}
```


* BarChart

![compose_barchart](https://user-images.githubusercontent.com/24242836/152200463-acd1ff1e-7b57-40c4-a062-f05c510a9cc8.PNG)

```
BarChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp).padding(horizontal = 16.dp),
                barModels = bars,
                axisStrokeWidth = 4f,
                labelSize= 48f,
                labelVerticalPadding = 10f,
                minValue = 0,
                maxValue = 600
            )
```

* LineChart

![compose_linechart](https://user-images.githubusercontent.com/24242836/152200503-76555f10-e61e-4bda-ac51-c38a1d2dcec5.PNG)

```
LineChart(
                modifier = Modifier
                    .fillMaxWidth().height(240.dp).padding(horizontal = 16.dp),
                lineChartModels = lines,
                axisStrokeWidth = 6f,
                lineColor = MaterialTheme.colors.secondary,
                pointRadius = 12f,
                labelSize= 48f,
                labelVerticalPadding = 12f,
                minValue = 0,
                maxValue = 600
            )
```

* License

```
Copyright Junhan.ryu

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
