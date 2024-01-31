import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.LeftFragment
import ui.getDropFileTarget
import ui.right_fragment.RightFragment


fun main() = application {

    LaunchedEffect("key1") {
        MainViewModel.refreshAllInfo()
    }

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

// TODO: snackBar 显示问题
@Composable
fun App() {
    val snackText = MainViewModel.snackText.collectAsState()
    val scope = rememberCoroutineScope()
    val hostState = SnackbarHostState()

    MaterialTheme {
        Scaffold(modifier = Modifier.fillMaxSize(), snackbarHost = { SnackbarHost(hostState) }) {
            Box(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier.fillMaxSize().padding(10.dp)) {
                    //整个页面大体划分为左右布局
                    LeftFragment(modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(10.dp))
                    RightFragment(modifier = Modifier.weight(4f))
                }
            }
        }

    }
}
