package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import color.colorItemSelected
import color.colorItemUnSelected

/**
 * 通用MenuItem样式
 * @param selected 是否选中
 * @param icon 图标
 * @param text 内容
 * @param onClick 点击事件
 */
@Preview
@Composable
fun MenuItem(selected: Boolean = false, icon: ImageVector, text: String, onClick: () -> Unit) {
    val bgColor = if (selected) colorItemSelected else colorItemUnSelected
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .background(color = bgColor)
        .clickable {
            onClick()
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
fun LargeCard(modifier: Modifier = Modifier,verticalPadding:Dp = 8.dp,horizontalPadding:Dp = 0.dp, content: @Composable () -> Unit) {
    Card(modifier = modifier, shape = RoundedCornerShape(8.dp), elevation = 8.dp) {
        Box(modifier = Modifier.padding(horizontalPadding,verticalPadding)) {
            Column(modifier = Modifier.fillMaxWidth()) {
                content()
            }
        }
    }
}

/**
 * 通用Card样式:Card外观的Column
 */
@Composable
fun MediumCard(modifier: Modifier = Modifier,verticalPadding:Dp = 4.dp,horizontalPadding:Dp = 0.dp, content: @Composable () -> Unit) {
    Card(modifier = modifier, shape = RoundedCornerShape(4.dp), elevation = 8.dp) {
        Box(modifier = Modifier.padding(horizontalPadding,verticalPadding)) {
            Column(modifier = Modifier.fillMaxWidth()) {
                content()
            }
        }
    }
}

@Composable
fun ScrollableLargeCard(modifier: Modifier = Modifier,verticalPadding:Dp = 16.dp,horizontalPadding:Dp = 0.dp, content: @Composable () -> Unit) {
    val state = rememberScrollState()
    Card(modifier = modifier, shape = RoundedCornerShape(16.dp), elevation = 8.dp) {
        Box(modifier = Modifier.padding(horizontalPadding,verticalPadding)) {
            Column(modifier = Modifier.fillMaxWidth().verticalScroll(state,true)) {
                content()
            }
            VerticalScrollbar(
                rememberScrollbarAdapter(state),
                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight()
            )
        }
    }
}