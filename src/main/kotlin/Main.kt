import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ui.function_fragment.FunctionFragment
import ui.info_fragment.InfoFragment
import ui.getDropFileTarget
import ui.right_fragment.RightFragment
import viewmodel.InfoViewModel
import viewmodel.InstallViewModel
import viewmodel.MainViewModel


fun main() = application {
    LaunchedEffect("key1") {
        launch {
            InfoViewModel.getDeviceList()
        }
    }


    Window(onCloseRequest = {
        AdbTools.killAdb()
        exitApplication()
    }, title = "Adb工具", icon = painterResource("favicon.png")) {
        // 设置窗口尺寸
        window.setSize(1200, 600)
        // 设置文件拖拽回调
        window.contentPane.dropTarget = getDropFileTarget {
            MainViewModel.updateFiles(it)
            if (MainViewModel.curFunc.value == MainViewModel.FUNC_INSTALL && InstallViewModel.autoInstall.value && InstallViewModel.deviceList.value.isNotEmpty()) {
                InstallViewModel.install(it.first().path)
            }
        }
        // 页面UI
        App()
    }

}

@Composable
fun App() {
    val hostState = remember { SnackbarHostState() }
    LaunchedEffect(Unit){
        launch {
            MainViewModel.snack.collect {
                if(it.isNotEmpty()){
                    hostState.showSnackbar(it)
                }
            }
        }
    }

    MaterialTheme {
        Scaffold(modifier = Modifier.fillMaxSize(), snackbarHost = { SnackbarHost(hostState) }) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    FunctionFragment()
                    Row(modifier = Modifier.fillMaxSize().padding(10.dp)) {
                        //整个页面大体划分为左右布局
                        InfoFragment(modifier = Modifier.weight(1.5f))
                        Spacer(modifier = Modifier.width(10.dp))
                        RightFragment(modifier = Modifier.weight(4f))
                    }
                }

            }
        }
    }
}
