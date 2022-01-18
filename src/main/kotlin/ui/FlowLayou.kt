package ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout

/**
 * @param horizontalSpace 子项水平间隔
 * @param verticalSpace 子项垂直间隔
 */
@Composable
fun FlowLayout(modifier: Modifier = Modifier, horizontalSpace: Int = 0,verticalSpace:Int=0, content: @Composable () -> Unit) {
    Layout(modifier = modifier, content = content) { measurables, constraints -> //measurables是需要测量的子项列表  constraints是来自父布局的测量约束
        val placeables = measurables.map { measurable->
           val placeable =  measurable.measure(constraints)
            placeable
        }
        var xPosition = 0
        var yPosition = 0
        layout(constraints.maxWidth,constraints.maxHeight){
            placeables.forEach {
                if(xPosition + it.width + horizontalSpace> constraints.maxWidth){
                    //一行已经放不下，需要换行
                    yPosition+=it.height+verticalSpace
                    xPosition = 0
                }
                it.placeRelative(x = xPosition,y = yPosition)
                xPosition += it.width+horizontalSpace
            }
        }
    }
}


