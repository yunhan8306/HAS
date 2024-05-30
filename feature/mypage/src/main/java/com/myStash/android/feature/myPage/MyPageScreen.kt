package com.myStash.android.feature.myPage

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.myStash.android.common.util.CommonActivityResultContract
import com.myStash.android.design_system.animation.enterTransitionStart
import com.myStash.android.design_system.animation.exitTransitionStart
import com.myStash.android.feature.gender.GenderActivity
import com.myStash.android.navigation.MainNavType

fun NavGraphBuilder.myPageScreen() {
    composable(
        route = MainNavType.MY_PAGE.name,
        enterTransition = { enterTransitionStart },
        exitTransition = { exitTransitionStart }
    ) {
        MyPageRoute()
    }
}

@Composable
fun MyPageRoute(

) {

    val activity = LocalContext.current as ComponentActivity
    val itemActivityLauncher = rememberLauncherForActivityResult(
        contract = CommonActivityResultContract(),
        onResult = {}
    )

    MyPageScreen(
        showGenderActivity = {
            val intent = Intent(activity, GenderActivity::class.java)
            itemActivityLauncher.launch(intent)
        }
    )
}

@Composable
fun MyPageScreen(
    showGenderActivity: () -> Unit
) {
   Column(
       modifier = Modifier.fillMaxSize(),
       horizontalAlignment = Alignment.CenterHorizontally
   ) {
       Spacer(modifier = Modifier.weight(1f))
       Box(
           modifier = Modifier
               .size(50.dp)
               .clickable { showGenderActivity.invoke() }
       ) {
           Text(text = "gender")
       }
       Spacer(modifier = Modifier.weight(1f))
   }
}