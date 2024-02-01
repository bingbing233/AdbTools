package ui.info_fragment

import viewmodel.MainViewModel
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import strings.titleInfo
import styles.titleTextStyle
import ui.LargeCard
import ui.Loading
import viewmodel.InfoViewModel
import viewmodel.InstallViewModel

/**
 * 左边布局 用于展示设备信息
 */
@Composable
fun InfoFragment(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        MainDeviceInfo()
    }
}


// 设备信息
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MainDeviceInfo() {
    val info = InfoViewModel.deviceInfo.collectAsState()
    val battery = InfoViewModel.batteryInfo.collectAsState()
    val cpu = InfoViewModel.CPUInfo.collectAsState()
    val showLoading = InfoViewModel.isShowInfoLoading.collectAsState()
    val deviceList = InfoViewModel.deviceList.collectAsState()
    val state = rememberScrollState()

    val menuList = mutableListOf<ContextMenuItem>()
    menuList.add(ContextMenuItem("刷新") { InfoViewModel.getDeviceList() })
    deviceList.value.forEach {
        menuList.add(ContextMenuItem(it.deviceStr) {
            InfoViewModel.selectDevice(it.deviceId)
        })
    }

    Box(modifier = Modifier.fillMaxHeight()) {

        Column(modifier = Modifier.verticalScroll(state, true)) {
            Spacer(modifier = Modifier.height(8.dp))

            ContextMenuArea(items = { menuList }) {
                LargeCard(modifier = Modifier.clickable {
                    MainViewModel.copyText(info.value)
                }, horizontalPadding = 8.dp) {
                    Text("$titleInfo（左键复制，右键选择设备）", style = titleTextStyle)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(info.value)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            LargeCard(
                modifier = Modifier.clickable {
                    MainViewModel.copyText(battery.value)
                },
                horizontalPadding = 8.dp
            ) {
                Text("电池信息", style = titleTextStyle)
                Spacer(modifier = Modifier.height(5.dp))
                Text(battery.value)
            }

            Spacer(modifier = Modifier.height(10.dp))
            LargeCard(modifier = Modifier.clickable {
                MainViewModel.copyText(cpu.value)
            }, horizontalPadding = 8.dp) {
                Text("CPU信息", style = titleTextStyle)
                Spacer(modifier = Modifier.height(5.dp))
                Text(cpu.value)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        VerticalScrollbar(adapter = rememberScrollbarAdapter(state), modifier = Modifier.align(Alignment.CenterEnd))

        if (showLoading.value) {
            Loading(modifier = Modifier.align(Alignment.Center))
        }
    }

}
