package ui.right_fragment

import viewmodel.MainViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.LargeCard
import viewmodel.InstallViewModel

/**
 * 右边布局
 */
@Composable
fun RightFragment(modifier: Modifier = Modifier) {
    val curFunc = MainViewModel.curFunc.collectAsState()
    val log = MainViewModel.log.collectAsState()
    Column(modifier = modifier.fillMaxSize()) {
        LargeCard(modifier = Modifier.fillMaxWidth().weight(1.5f)) {
            when (curFunc.value) {
                MainViewModel.FUNC_MAIN -> {
                    PageMain()
                }

                MainViewModel.FUNC_INFO -> {
                    PageInfo()
                }

                MainViewModel.FUNC_FLASH -> {
                    PageFlash()
                }

                MainViewModel.FUNC_SETTING -> {
                    PageSetting()
                }

                MainViewModel.FUNC_INSTALL -> {
                    PageInstall()
                    InstallViewModel.getDeviceList()
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        LargeCard(modifier = Modifier.fillMaxWidth().weight(1f), verticalPadding = 10.dp, horizontalPadding = 10.dp) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(log.value, modifier = Modifier.fillMaxSize())
            }
        }
    }
}