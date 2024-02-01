package viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.io.File
import java.lang.StringBuilder

object MainViewModel {
    // 功能
    const val FUNC_MAIN = "main"
    const val FUNC_SETTING = "device"
    const val FUNC_INFO = "info"
    const val FUNC_FLASH = "flash"
    const val FUNC_INSTALL = "install"

    private val scope = CoroutineScope(Dispatchers.Main)

    // 拖拽文件列表
    private val mFileList = MutableStateFlow<List<File>>(emptyList())
    val fileList = mFileList.asStateFlow()

    // 当前选中功能
    private val mCurFunc = MutableStateFlow(FUNC_INSTALL)
    val curFunc = mCurFunc.asStateFlow()

    // 命令日志
    private val mSnack = MutableStateFlow<String>("")
    val snack = mSnack.asStateFlow()

    // log
    private val mLog = MutableStateFlow<String>("")
    val log = mLog.asStateFlow()

    /**
     * 更新文件列表
     */
    fun updateFiles(files: List<File>) {
        scope.launch {
            mFileList.emit(files)
        }
    }

    /**
     * 设置当前选中功能
     * @param func viewmodel.MainViewModel.FUNC_
     */
    fun setCurFunc(func: String) {
        scope.launch {
            mCurFunc.emit(func)
        }
    }

    /**
     * 更新snack
     */
    fun setSnack(log: String) {
        scope.launch {
            mSnack.emit(log)
        }
    }

    // 复制到剪切板
    fun copyText(text: String) {
        scope.launch(Dispatchers.IO) {
            mSnack.emit("已复制到剪切板")
            val clip = Toolkit.getDefaultToolkit().systemClipboard
            val tText = StringSelection(text)
            clip.setContents(tText, null)
        }
    }

    // 追加log
    fun appendLog(log: String){
        scope.launch {
            val sb = StringBuilder()
            sb.append(mLog.value)
            sb.append("\n")
            sb.append(log)
            mLog.emit(sb.toString())
        }
    }
}
