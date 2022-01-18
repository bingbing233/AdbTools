// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.FlowLayout

@Composable
@Preview()
fun App() {
    val viewModel = MainViewModel()
    val resultStr = viewModel.resultStr.collectAsState()
    MaterialTheme {
        Column(modifier = Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.SpaceBetween) {
            Box(modifier = Modifier.fillMaxSize().weight(3f)) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text("自定义命令", style = MaterialTheme.typography.h6)
                    Spacer(modifier = Modifier.height(3.dp))
                    //命令输入
                    Row(
                        modifier = Modifier.fillMaxWidth().height(80.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val text = remember { mutableStateOf("") }
                        OutlinedTextField(
                            value = text.value,
                            onValueChange = { text.value = it },
                            modifier = Modifier.weight(10f).onKeyEvent {
                                if (it.type == KeyEventType.KeyUp && it.toString().contains("Enter")) {
                                    viewModel.execute(text.value)
                                }
                                false
                            },
                            singleLine = true,
                            label = { Text("输入ADB命令") },
                            keyboardActions = KeyboardActions {
                                println(this.defaultKeyboardAction(ImeAction.Done))
                            }
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Button(onClick = {
                            viewModel.execute(text.value)
                        }, modifier = Modifier.height(50.dp).width(100.dp)) { Text("执行") }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    //清除数据
                    Text("包名命令", style = MaterialTheme.typography.h6)
                    Spacer(modifier = Modifier.height(3.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth().height(80.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val text = remember { mutableStateOf("") }
                        OutlinedTextField(
                            value = text.value,
                            onValueChange = { text.value = it },
                            modifier = Modifier.weight(10f),
                            singleLine = true,
                            label = { Text("输入App包名 ") },
                            keyboardActions = KeyboardActions {
                                println(this.defaultKeyboardAction(ImeAction.Done))
                            }
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Button(onClick = {
                            viewModel.clearAppData(text.value)
                        }, modifier = Modifier.height(50.dp).width(100.dp)) { Text("清除数据") }

                        Spacer(modifier = Modifier.width(10.dp))
                        Button(onClick = {
                            viewModel.uninstall(text.value)
                        }, modifier = Modifier.height(50.dp).width(100.dp)) { Text("卸载应用") }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    //文件命令
                    Text("文件命令", style = MaterialTheme.typography.h6)
                    Spacer(modifier = Modifier.height(3.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth().height(80.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val text = remember { mutableStateOf("") }
                        OutlinedTextField(
                            value = text.value,
                            onValueChange = { text.value = it },
                            modifier = Modifier.weight(10f),
                            singleLine = true,
                            label = { Text("输入文件路径 ") },
                            keyboardActions = KeyboardActions {
                                println(this.defaultKeyboardAction(ImeAction.Done))
                            }
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Button(onClick = {
                            viewModel.install(text.value)
                        }, modifier = Modifier.height(50.dp).width(100.dp)) { Text("安装apk") }

                        Spacer(modifier = Modifier.width(10.dp))
                        Button(onClick = {
                            viewModel.pushFile(text.value)
                        }, modifier = Modifier.height(50.dp).width(100.dp)) { Text("文件传输") }

                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    //第一行按钮
                    Text("快捷命令", style = MaterialTheme.typography.h6)
                    Spacer(modifier = Modifier.height(3.dp))
                    FlowLayout(modifier = Modifier, horizontalSpace = 10) {
                        Button(onClick = { viewModel.reboot() }) {
                            Text("重启设备")
                        }
                        Button(onClick = { viewModel.getAndroidVersion() }) {
                            Text("Android版本号")
                        }
                        Button(onClick = { viewModel.getCurrentActivity() }) {
                            Text("当前Activity")
                        }
                        Button(onClick = { viewModel.getDevices() }) {
                            Text("已连接设备")
                        }
                        Button(onClick = { viewModel.getConnectionState() }) {
                            Text("设备状态")
                        }
                        Button(onClick = { viewModel.getAllPkgName() }) {
                            Text("查看全部app包名")
                        }
                        Button(onClick = { viewModel.screenShot() }) {
                            Text("屏幕截图")
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
                BasicTextField(
                    value = resultStr.value,
                    onValueChange = {},
                    modifier = Modifier.fillMaxSize().verticalScroll(enabled = true, state = state),
                    readOnly = true,
                    textStyle = MaterialTheme.typography.body1
                )
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Adb工具", icon = painterResource("favicon.png")) {
        window.setSize(800, 800)
        App()
    }
}
