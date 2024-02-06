package ui.right_fragment

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import colors.colorLink
import strings.titleFlash
import viewmodel.FlashViewModel
import viewmodel.InstallViewModel
import viewmodel.MainViewModel

@Composable
fun PageFlash() {
    val state = rememberScrollState(0)
    Box(modifier = Modifier.fillMaxSize().padding(10.dp)) {
        Column(modifier = Modifier.verticalScroll(state)) {
            QuickCommand()
            Spacer(modifier = Modifier.height(8.dp))
            RootGuide()
        }
        VerticalScrollbar(adapter = rememberScrollbarAdapter(state), modifier = Modifier.align(Alignment.CenterEnd))
    }
}

@Composable
fun QuickCommand() {
    Text(
        "快捷命令（仅支持单设备）",
        style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 18.sp)
    )
    Spacer(modifier = Modifier.height(4.dp))
    Row {
        Button(onClick = { FlashViewModel.reboot() }) {
            Text("重启设备")
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(onClick = { FlashViewModel.rebootRecovery() }) {
            Text("重启到Recovery")
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(onClick = { FlashViewModel.rebootFastBoot() }) {
            Text("重启到fastboot")
        }
    }
}

@Composable
fun RootGuide() {
    Text(
        "Root教程，以小米为例（点击查看Magisk原教程）",
        style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 18.sp),
        modifier = Modifier.clickable {
            FlashViewModel.openUrl("https://magiskcn.com/")
        }
    )
    Spacer(modifier = Modifier.height(6.dp))

    TextWithLink("1、小米解锁Bootloader","（点击查看解锁教程）","https://magiskcn.com/xiaomi-unlock")
    Spacer(modifier = Modifier.height(4.dp))

    TextWithLink("2、下载系统包，一定要下载对应机型的","（点击跳转下载）","https://magiskcn.com/rom")
    Spacer(modifier = Modifier.height(4.dp))

    TextWithLink("3、提取boot.img，如果系统包有boot.img，解压系统包就能看到boot.img","（点击跳转提取教程）","https://magiskcn.com/payload-dumper-go-boot")
    Spacer(modifier = Modifier.height(4.dp))

    Text("4、手机插电脑，文件传输模式，复制 boot.img 到手机 Download 目录")
    Spacer(modifier = Modifier.height(4.dp))

    TextWithLink("5、手机下载Magisk App（点击下载）","（点击跳转提取教程）","https://magiskcn.com/magisk-download")
    Spacer(modifier = Modifier.height(4.dp))

    Text("6、打开 Magisk，1：安装 – 2：选择 boot.img – 3：开始修补文件 – 4：修补成功")
    Spacer(modifier = Modifier.height(2.dp))
    Image(painter = painterResource("img_repair_boot_guide.png"),"")
    Spacer(modifier = Modifier.height(4.dp))

    TextWithLink("接下来的命令需要adb-fastboot套件（用android Studio的也行）","（点击下载,要解压）","https://mrzzoxo.lanzoui.com/b02plghuh")

    Text("7、手机进入Fastboot模式（点击重启）")
    Spacer(modifier = Modifier.height(4.dp))

    Text("8、fastboot flash boot xx/magisk...img，运行结果如下")
    Spacer(modifier = Modifier.height(2.dp))
    Image(painter = painterResource("img_flash_boot_result.png"),"")
    Spacer(modifier = Modifier.height(4.dp))

    Text("9、重启打开Magisk查看root结果")
    Spacer(modifier = Modifier.height(2.dp))
    Image(painter = painterResource("img_root_result.png"),"")
    Spacer(modifier = Modifier.height(4.dp))

}

@Composable
private fun TextWithLink(text: String, linkText: String, url: String) {
    Text(text = buildAnnotatedString {
        withStyle(style = SpanStyle(fontSize = 16.sp, color = Color.Black)) {
            append(text)
        }
        withStyle(style = SpanStyle(fontSize = 16.sp, color = colorLink)) {
            append(linkText)
        }
    }, modifier = Modifier.clickable { FlashViewModel.openUrl(url) })
}