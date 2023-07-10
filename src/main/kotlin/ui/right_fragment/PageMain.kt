package ui.right_fragment

import MainViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect
import strings.titleMain
import ui.MediumCard

@Composable
fun PageMain() {
    Box(modifier = Modifier.fillMaxSize().padding(10.dp)) {
        Column(modifier = Modifier.fillMaxSize()) {
            FuncInstall()
        }
    }
}

@Composable
fun FuncInstall() {
    val files = remember { mutableStateOf("") }
    val check = remember { mutableStateOf(false) }

    LaunchedEffect("func_install") {
        MainViewModel.fileList.collect {
            if (MainViewModel.autoInstall.value) {
                MainViewModel.installApk()
            }
            files.value = ""
            it.forEach { file ->
                val absPath = file.absoluteFile
                files.value += "\n $absPath"
            }
        }
    }

    MediumCard( verticalPadding = 10.dp, horizontalPadding = 10.dp) {
        Row(modifier = Modifier.wrapContentWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text("安装应用：把apk拖拽到应用窗口内，点击安装即可。")

            Spacer(modifier = Modifier.width(10.dp))
            Text("自动安装")
            Checkbox(checked = check.value, onCheckedChange = {
                check.value = it
                MainViewModel.setAutoInstall(it)
            })

            Spacer(modifier = Modifier.width(10.dp))
            Button(onClick = { MainViewModel.installApk() }) {
                Text("安装")
            }
        }
        Text(files.value)
    }
}