package ui// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import MainViewModel
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview()
fun App() {
    val viewModel = MainViewModel()
    var resultStr = viewModel.resultStr.collectAsState()
    MaterialTheme {
        Row(modifier = Modifier.fillMaxSize().padding(20.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Box(modifier = Modifier.fillMaxSize().weight(3f)) {
                Column(modifier = Modifier.fillMaxSize()) {
                    //命令输入
                    Row(
                        modifier = Modifier.fillMaxWidth().height(80.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        var text = remember { mutableStateOf("") }
                        TextField(
                            value = text.value,
                            onValueChange = { text.value = it },
                            modifier = Modifier.weight(10f),
                            label = { Text("输入ADB命令，并点击 √ ") }
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Button(onClick = {
                            viewModel.execute(text.value)
                        }, modifier = Modifier.height(40.dp).width(80.dp)) { Icon(Icons.Default.Done, "") }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    //第一行按钮
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        Button(onClick = { viewModel.reboot() }) {
                            Text("重启设备")
                        }
                        Button(onClick = { viewModel.getAndroidVersion() }) {
                            Text("Android版本号")
                        }
                        Button(onClick = { viewModel.getCurrentActivity() }) {
                            Text("当前Activity")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(20.dp))

            Box(
                modifier = Modifier.fillMaxSize().weight(1f)
                    .shadow(3.dp, shape = RoundedCornerShape(corner = CornerSize(5.dp))).padding(13.dp)
            ) {
                val state = rememberScrollState()
                Text(
                    text = resultStr.value,
                    modifier = Modifier.fillMaxSize().verticalScroll(enabled = true, state = state),
                    color = Color.Black,
                    fontSize = 18.sp
                )
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Adb工具", icon = painterResource("favicon.png")) {
        App()
    }
}
