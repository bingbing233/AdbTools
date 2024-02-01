package ui.function_fragment

import viewmodel.MainViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import strings.*
import ui.HorizontalMediumCard
import ui.HorizontalMenuItem
import ui.LargeCard
import ui.MenuItem
import viewmodel.InfoViewModel

@Composable
fun FunctionFragment(modifier: Modifier = Modifier) {
    MainFuncMenu()
}

// 左边菜单样式
@Composable
private fun MainFuncMenu() {
    val curFunc = MainViewModel.curFunc.collectAsState()
    val selectFunc = curFunc.value
    HorizontalMediumCard(verticalPadding = 0.dp) {
        // apk安装
        HorizontalMenuItem(MainViewModel.FUNC_INSTALL == selectFunc, Icons.Default.Phone, titleInstall) {
            MainViewModel.setCurFunc(MainViewModel.FUNC_INSTALL)
        }
        // 快捷功能
        HorizontalMenuItem(MainViewModel.FUNC_MAIN == selectFunc, Icons.Default.Home, titleMain) {
            MainViewModel.setCurFunc(MainViewModel.FUNC_MAIN)
        }
        // 刷机相关
        HorizontalMenuItem(MainViewModel.FUNC_FLASH == selectFunc, Icons.Default.AccountBox, titleFlash) {
            MainViewModel.setCurFunc(MainViewModel.FUNC_FLASH)
        }
    }
}