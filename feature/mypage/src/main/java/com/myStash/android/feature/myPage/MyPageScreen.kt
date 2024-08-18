package com.myStash.android.feature.myPage

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.myStach.android.feature.contact.ContactActivity
import com.myStash.android.common.util.CommonActivityResultContract
import com.myStash.android.design_system.animation.slideIn
import com.myStash.android.design_system.ui.component.SpacerLineBox
import com.myStash.android.design_system.ui.component.header.HasLogoHeader
import com.myStash.android.design_system.ui.component.text.HasFontWeight
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.design_system.ui.theme.clickableNoRipple
import com.myStash.android.feature.webview.WebView
import com.myStash.android.feature.webview.WebViewConstants
import com.myStash.android.navigation.MainNavType

fun NavGraphBuilder.myPageScreen() {
    composable(
        route = MainNavType.MY_PAGE.name
    ) {
        MyPageRoute()
    }
}

@Composable
fun MyPageRoute(

) {

    val activity = LocalContext.current as ComponentActivity
    var menuType by remember { mutableStateOf<MyPageMenuType?>(null) }

    var webViewUrl by remember { mutableStateOf("") }
    var isShowContact by remember { mutableStateOf(false) }

    val contactActivityLauncher = rememberLauncherForActivityResult(
        contract = CommonActivityResultContract(),
        onResult = { menuType = null }
    )

    MyPageScreen(
        state = MyPageScreenState(nickName = "Test"),
        onAction = { action ->
            when(action) {
                is MyPageScreenAction.ShowWebView -> {
                    webViewUrl = action.url
                    menuType = MyPageMenuType.WebView(action.url)
                }
                is MyPageScreenAction.ShowContact -> {
                    menuType = MyPageMenuType.Contact
                }
                else -> Unit
            }
        }
    )

    when(val type = menuType) {
        is MyPageMenuType.WebView -> {
            WebView(
                url = type.url,
                onDismiss = { menuType = null }
            )
        }
        is MyPageMenuType.Contact -> {
            val intent = Intent(activity, ContactActivity::class.java)
            contactActivityLauncher.launch(intent)
            activity.slideIn()
        }
        else -> Unit
    }
}

@Composable
fun MyPageScreen(
    state: MyPageScreenState,
    onAction: (MyPageScreenAction) -> Unit
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HasLogoHeader()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(214.dp)
                .padding(top = 44.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = com.myStash.android.common.resource.R.drawable.img_mypage_hamong),
                contentDescription = ""
            )
            HasText(
                modifier = Modifier.padding(top = 16.dp),
                text = state.nickName,
                fontSize = 18.dp,
                fontWeight = HasFontWeight.Bold
            )
        }
        SpacerLineBox()
        Column(
            modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
        ) {
            MyPageItem(
                text = "Manage Categories / Tags",
                onClick = {  }
            )
            MyPageItem(
                text = "Notice",
                onClick = { onAction.invoke(MyPageScreenAction.ShowWebView(WebViewConstants.URL_NOTICE)) }
            )
            MyPageItem(
                text = "Contact us",
                onClick = { onAction.invoke(MyPageScreenAction.ShowContact) }
            )
            MyPageItem(
                text = "FAQ",
                onClick = { onAction.invoke(MyPageScreenAction.ShowWebView(WebViewConstants.URL_FAQ)) }
            )
        }

        Box(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .border(width = 1.dp, color = Color(0xFFBFD320), shape = RoundedCornerShape(size = 10.dp))
                    .clip(shape = RoundedCornerShape(size = 10.dp))
                    .background(color = MaterialTheme.colors.surface)
                    .fillMaxWidth()
                    .height(55.dp)
                    .padding(start = 16.dp, end = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = com.myStash.android.common.resource.R.drawable.img_dark_mode),
                    contentDescription = ""
                )
                HasText(
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .weight(1f),
                    text = "Dark Mode",
                    fontSize = 16.dp
                )
            }
        }

        HasText(
            modifier = Modifier.padding(top = 50.dp),
            text = "Version 1.1.001(10)",
            fontSize = 14.dp,
            color = Color(0xFF707070),
            fontWeight = HasFontWeight.Bold
        )
        HasText(
            modifier = Modifier.padding(top = 6.dp),
            text = "This is the latest version",
            fontSize = 13.dp,
            color = Color(0xFF909090),
        )
        HasText(
            modifier = Modifier
                .padding(top = 33.dp)
                .clickableNoRipple { onAction.invoke(MyPageScreenAction.ShowWebView(WebViewConstants.URL_TERMS)) },
            text = "Terms of Service",
            fontSize = 14.dp,
            color = Color(0xFF707070),
            textDecoration = TextDecoration.Underline,
        )
    }
}