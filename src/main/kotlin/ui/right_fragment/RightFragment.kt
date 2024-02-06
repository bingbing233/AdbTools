package ui.right_fragment

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sun.tools.javac.Main
import ui.LargeCard
import viewmodel.InstallViewModel
import viewmodel.MainViewModel

/**
 * 右边布局
 */
@Composable
fun RightFragment(modifier: Modifier = Modifier) {
    val curFunc = MainViewModel.curFunc.collectAsState()
    val log = MainViewModel.log.collectAsState()
    val scrollState = rememberScrollState(0)
    val showLogArea = MainViewModel.showLogArea.collectAsState()
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

        if(showLogArea.value){
            LargeCard(modifier = Modifier.fillMaxWidth().weight(1f), verticalPadding = 10.dp, horizontalPadding = 10.dp) {
                Text("日志输出", style = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold))
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(log.value, modifier = Modifier.fillMaxSize().verticalScroll(scrollState))
                    VerticalScrollbar(
                        adapter = ScrollbarAdapter(scrollState),
                        modifier = Modifier.height(100.dp).align(Alignment.CenterEnd)
                    )
                    Image(
                        imageVector = Icons.Outlined.Delete,
                        "",
                        modifier = Modifier.clickable { MainViewModel.clearLog() }.align(Alignment.TopEnd).border(
                            shape = RoundedCornerShape(100.dp), border = BorderStroke(
                                0.dp,
                                Color.White
                            )
                        ).size(30.dp)
                    )
                }
            }
        }
    }
}