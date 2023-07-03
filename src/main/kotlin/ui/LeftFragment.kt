package ui

import MainViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sun.tools.javac.Main

/**
 * 左边布局
 */
@Composable
fun LeftFragment(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        MainFuncMenu()
    }
}

// 左边菜单样式
@Composable
private fun MainFuncMenu() {
    LargeCard {
        MenuItem(MainViewModel.FUNC_MAIN,Icons.Default.Home, "主页") { }
        MenuItem(MainViewModel.FUNC_INFO,Icons.Default.Info, "设备信息") { }
        MenuItem(MainViewModel.FUNC_SETTING,Icons.Default.Settings, "设置") { }
    }
}
