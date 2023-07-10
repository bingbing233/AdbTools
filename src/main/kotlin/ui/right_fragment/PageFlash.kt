package ui.right_fragment

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import strings.titleFlash

@Composable
fun PageFlash(){
    Box(modifier = Modifier.fillMaxSize()){
        Text(titleFlash, modifier = Modifier.align(Alignment.Center))
    }
}