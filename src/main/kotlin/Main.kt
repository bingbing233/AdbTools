import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.*

fun main() = application {
    Window(onCloseRequest = {
        AdbTools.killAdb()
        exitApplication()
    }, title = "Adb工具", icon = painterResource("favicon.png")) {
        // 设置窗口尺寸
        window.setSize(1600, 800)
        // 设置文件拖拽回调
        window.contentPane.dropTarget = getDropFileTarget {
            MainViewModel.updateFiles(it)
        }
        // 页面UI
        App()
    }

}

@Composable
@Preview()
fun App() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxSize().padding(10.dp)) {
                //整个页面大体划分为左右布局
                LeftFragment(modifier = Modifier.weight(1f))
                RightFragment(modifier = Modifier.weight(4f))
            }
        }
    }
}





//@Composable
//fun oldUI(){
//    val viewModel = MainViewModel
//    val resultStr = viewModel.resultStr.collectAsState()
//    Column(modifier = Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.SpaceBetween) {
//        Box(modifier = Modifier.fillMaxSize().weight(3f)) {
//            Column(modifier = Modifier.fillMaxSize()) {
//                Text("自定义命令", style = MaterialTheme.typography.h6)
//                Spacer(modifier = Modifier.height(3.dp))
//                //命令输入
//                Row(
//                    modifier = Modifier.fillMaxWidth().height(80.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    val text = remember { mutableStateOf("") }
//                    OutlinedTextField(
//                        value = text.value,
//                        onValueChange = { text.value = it },
//                        modifier = Modifier.weight(10f).onKeyEvent {
//                            if (it.type == KeyEventType.KeyUp && it.toString().contains("Enter")) {
//                                viewModel.execute(text.value)
//                            }
//                            false
//                        },
//                        singleLine = true,
//                        label = { Text("输入ADB命令") },
//                        keyboardActions = KeyboardActions {
//                            println(this.defaultKeyboardAction(ImeAction.Done))
//                        }
//                    )
//                    Spacer(modifier = Modifier.width(10.dp))
//                    Button(onClick = {
//                        viewModel.execute(text.value)
//                    }, modifier = Modifier.height(50.dp).width(100.dp)) { Text("执行") }
//                }
//
//                Spacer(modifier = Modifier.height(10.dp))
//
//                //包名命令
//                Text("包名命令", style = MaterialTheme.typography.h6)
//                Spacer(modifier = Modifier.height(3.dp))
//                Row(
//                    modifier = Modifier.fillMaxWidth().height(80.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    val text = remember { mutableStateOf("") }
//                    OutlinedTextField(
//                        value = text.value,
//                        onValueChange = { text.value = it },
//                        modifier = Modifier.weight(10f),
//                        singleLine = true,
//                        label = { Text("输入App包名 ") },
//                        keyboardActions = KeyboardActions {
//                            println(this.defaultKeyboardAction(ImeAction.Done))
//                        }
//                    )
//                    Spacer(modifier = Modifier.width(10.dp))
//                    Button(onClick = {
//                        viewModel.clearAppData(text.value)
//                    }, modifier = Modifier.height(50.dp).width(100.dp)) { Text("清除数据") }
//
//                    Spacer(modifier = Modifier.width(10.dp))
//                    Button(onClick = {
//                        viewModel.uninstall(text.value)
//                    }, modifier = Modifier.height(50.dp).width(100.dp)) { Text("卸载应用") }
//
//                    Spacer(modifier = Modifier.width(10.dp))
//                    Button(onClick = {
//                        viewModel.getPkgPath(text.value)
//                    }, modifier = Modifier.height(50.dp).width(100.dp)) { Text("包名路径") }
//                }
//
//                Spacer(modifier = Modifier.height(10.dp))
//
//                //文件命令
//                Text("文件命令", style = MaterialTheme.typography.h6)
//                Spacer(modifier = Modifier.height(3.dp))
//                Row(
//                    modifier = Modifier.fillMaxWidth().height(80.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    val text = remember { mutableStateOf("") }
//                    OutlinedTextField(
//                        value = text.value,
//                        onValueChange = { text.value = it },
//                        modifier = Modifier.weight(10f),
//                        singleLine = true,
//                        label = { Text("输入文件路径 ") },
//                        keyboardActions = KeyboardActions {
//                            println(this.defaultKeyboardAction(ImeAction.Done))
//                        }
//                    )
//                    Spacer(modifier = Modifier.width(10.dp))
//                    Button(onClick = {
//                        viewModel.install(text.value)
//                    }, modifier = Modifier.height(50.dp).width(100.dp)) { Text("安装apk") }
//
//                    Spacer(modifier = Modifier.width(10.dp))
//                    Button(onClick = {
//                        viewModel.pushFile(text.value)
//                    }, modifier = Modifier.height(50.dp).width(100.dp)) { Text("文件传输") }
//
//                }
//
//                Spacer(modifier = Modifier.height(10.dp))
//
//                //第一行按钮
//                Text("快捷命令", style = MaterialTheme.typography.h6)
//                Spacer(modifier = Modifier.height(3.dp))
//                FlowLayout(modifier = Modifier, horizontalSpace = 10) {
//                    Button(onClick = { viewModel.reboot() }) {
//                        Text("重启设备")
//                    }
//                    Button(onClick = { viewModel.getAndroidVersion() }) {
//                        Text("Android版本号")
//                    }
//                    Button(onClick = { viewModel.getCurrentActivity() }) {
//                        Text("当前Activity")
//                    }
//                    Button(onClick = { viewModel.getDevices() }) {
//                        Text("已连接设备")
//                    }
//                    Button(onClick = { viewModel.getConnectionState() }) {
//                        Text("设备状态")
//                    }
//                    Button(onClick = { viewModel.getAllPkgName() }) {
//                        Text("查看全部app包名")
//                    }
//                    Button(onClick = { viewModel.getSysPkgName() }) {
//                        Text("系统app包名")
//                    }
//                    Button(onClick = { viewModel.get3PkgName() }) {
//                        Text("第三方app包名")
//                    }
//                    Button(onClick = { viewModel.screenShot() }) {
//                        Text("屏幕截图")
//                    }
//                    Button(onClick = { viewModel.getDeviceInfo() }) {
//                        Text("设备信息")
//                    }
//                    Button(onClick = { viewModel.getCPUInfo() }) {
//                        Text("CPU信息")
//                    }
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.width(20.dp))
//
//        Box(
//            modifier = Modifier.fillMaxSize().weight(1f)
//                .shadow(3.dp, shape = RoundedCornerShape(corner = CornerSize(5.dp))).padding(13.dp)
//        ) {
//            val state = rememberScrollState()
//            BasicTextField(
//                value = resultStr.value,
//                onValueChange = {},
//                modifier = Modifier.fillMaxSize().verticalScroll(enabled = true, state = state),
//                readOnly = true,
//                textStyle = MaterialTheme.typography.body1
//            )
//            VerticalScrollbar(modifier = Modifier.align(Alignment.CenterEnd), adapter = ScrollbarAdapter(state))
//        }
//    }
//
//}
