package ui.right_fragment

import MainViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.LargeCard

/**
 * 右边布局
 */
@Composable
fun RightFragment(modifier: Modifier = Modifier) {
    val curFunc = MainViewModel.curFunc.collectAsState()
    Column(modifier = modifier.fillMaxSize()) {
        LargeCard(modifier = Modifier.fillMaxWidth().weight(1.5f)) {
            when(curFunc.value){
                MainViewModel.FUNC_MAIN ->{
                    PageMain()
                }
                MainViewModel.FUNC_INFO ->{
                    PageInfo()
                }
                MainViewModel.FUNC_FLASH ->{
                    PageFlash()
                }
                MainViewModel.FUNC_SETTING ->{
                    PageSetting()
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        LargeCard(modifier = Modifier.fillMaxWidth().weight(1f), verticalPadding = 10.dp, horizontalPadding = 10.dp){
            Box(modifier = Modifier.fillMaxSize()) {
                Text("",modifier = Modifier.fillMaxSize())
            }
        }
    }
}