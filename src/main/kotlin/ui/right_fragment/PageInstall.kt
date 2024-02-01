package ui.right_fragment

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import module.DeviceBean
import viewmodel.InstallViewModel
import viewmodel.MainViewModel

@Composable
fun PageInstall() {
    Box(modifier = Modifier.fillMaxSize().padding(10.dp)) {
        FuncInstall()
    }
}

@Composable
private fun FuncInstall() {
    val deviceListState = InstallViewModel.deviceList.collectAsState()
    val autoInstall = InstallViewModel.autoInstall.collectAsState()
    val fileName = MainViewModel.fileList.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            "设备列表",
            style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        )
        Spacer(modifier = Modifier.height(4.dp))

        Row(modifier = Modifier.height(40.dp), verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(10.dp))
            Button(onClick = { InstallViewModel.killAdb() }) {
                Text("重启ADB")
            }
            Spacer(modifier = Modifier.width(10.dp))

            Button(onClick = { InstallViewModel.selectAllDevice() }) {
                Text("全选")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(onClick = { InstallViewModel.getDeviceList() }) {
                Text("刷新")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(onClick = {
                if (MainViewModel.fileList.value.isNotEmpty()) {
                    InstallViewModel.install(MainViewModel.fileList.value.first().path)
                } else {
                    MainViewModel.setSnack("请添加文件")
                }

            }) {
                Text("安装")
            }
            Spacer(modifier = Modifier.width(20.dp))
            Text("自动安装")
            Checkbox(checked = autoInstall.value, onCheckedChange = { InstallViewModel.autoInstall(it) })
        }
        Spacer(modifier = Modifier.height(10.dp))
        deviceListState.value.forEachIndexed { index, deviceBean ->
            DeviceListItem(deviceBean) {
                InstallViewModel.selectDevice(index, it)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (fileName.value.isNotEmpty()) {
            Text("文件名:${fileName.value.first()}")
        }
    }
}

@Composable
private fun DeviceListItem(bean: DeviceBean, onCheck: (Boolean) -> Unit) {
    Row(modifier = Modifier.height(20.dp)) {
        Text(text = bean.deviceStr)
        Spacer(modifier = Modifier.width(1.dp))
        Checkbox(checked = bean.check, onCheckedChange = { onCheck(it) })
    }
}