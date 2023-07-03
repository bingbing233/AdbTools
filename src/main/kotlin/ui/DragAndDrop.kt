package ui

import java.awt.datatransfer.DataFlavor
import java.awt.dnd.DnDConstants
import java.awt.dnd.DropTarget
import java.awt.dnd.DropTargetDropEvent
import java.io.File

/**
 * 拖拽文件返回路径实现
 */
fun getDropFileTarget(onGetFile: (List<File>) -> Unit): DropTarget {
    return object : DropTarget() {
        override fun drop(event: DropTargetDropEvent) {
            kotlin.runCatching {
                event.acceptDrop(DnDConstants.ACTION_REFERENCE)
                val droppedFiles = event.transferable.getTransferData(DataFlavor.javaFileListFlavor) as List<File>
                droppedFiles.forEach { println("path:${it}") }
                onGetFile(droppedFiles)
                event.dropComplete(true)
            }.onFailure {
                println(it)
            }
        }
    }
}