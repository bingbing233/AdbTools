package ui

import MainViewModel
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import color.colorItemSelected
import color.colorItemUnSelected

/**
 * 通用MenuItem样式
 */
@Preview
@Composable
fun MenuItem(func:String = "",icon: ImageVector, text: String, onClick: () -> Unit) {
    val curFunc = MainViewModel.curFunc.collectAsState()
    val bgColor = if(func == curFunc.value) colorItemSelected else colorItemUnSelected
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .background(color = bgColor)
        .clickable {
            onClick()
            MainViewModel.setCurFunc(func)
        }
        .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
        ) {
        Icon(icon, "")
        Spacer(modifier = Modifier.width(20.dp))
        Text(text)
    }
}

/**
 * 通用Card样式:Card外观的Column
 */
@Composable
fun LargeCard(modifier: Modifier = Modifier,content: @Composable () -> Unit) {
    Card(modifier = modifier.fillMaxWidth().padding(10.dp), shape = MaterialTheme.shapes.medium, elevation = 8.dp,) {
        Column(modifier = Modifier.fillMaxWidth()) {
            content()
        }
    }
}

@Composable
fun MediumCard(content: @Composable () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(10.dp), shape = MaterialTheme.shapes.medium) {
        Column(modifier = Modifier.fillMaxWidth()) {
            content()
        }
    }
}