package com.myStash.android.feature.myPage.ui

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.navigation.animation.composable
import com.myStach.android.feature.contact.ContactActivity
import com.myStash.android.common.resource.R
import com.myStash.android.common.util.CommonActivityResultContract
import com.myStash.android.common.util.constants.PermissionConstants
import com.myStash.android.design_system.animation.slideIn
import com.myStash.android.design_system.ui.color.ColorFamilyBlack20AndWhite
import com.myStash.android.design_system.ui.component.SpacerLineBox
import com.myStash.android.design_system.ui.component.header.HasLogoHeader
import com.myStash.android.design_system.ui.component.text.HasFontWeight
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.design_system.ui.component.text.HasTextField
import com.myStash.android.design_system.ui.theme.clickableNoRipple
import com.myStash.android.design_system.util.ShimmerLoadingAnimation
import com.myStash.android.design_system.util.addFocusCleaner
import com.myStash.android.design_system.util.rememberPermissionLauncher
import com.myStash.android.feature.gallery.GalleryActivity
import com.myStash.android.common.util.constants.GalleryConstants
import com.myStash.android.feature.manage.ManageActivity
import com.myStash.android.feature.myPage.ui.component.MyPageItem
import com.myStash.android.feature.myPage.MyPageViewModel
import com.myStash.android.feature.myPage.ui.component.ProfileNoneImage
import com.myStash.android.feature.webview.WebView
import com.myStash.android.common.util.constants.WebViewConstants
import com.myStash.android.navigation.MainNavType
import org.orbitmvi.orbit.compose.collectAsState

fun NavGraphBuilder.myPageScreen() {
    composable(
        route = MainNavType.MY.name
    ) {
        MyPageRoute()
    }
}

@Composable
fun MyPageRoute(
    viewModel: MyPageViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    val activity = LocalContext.current as ComponentActivity
    val state by viewModel.collectAsState()
    var menuType by remember { mutableStateOf<MyPageMenuType?>(null) }

    var webViewUrl by remember { mutableStateOf("") }
    var isShowPermissionRequestConfirm by remember { mutableStateOf(false) }

    val contactActivityLauncher = rememberLauncherForActivityResult(
        contract = CommonActivityResultContract(),
        onResult = { menuType = null }
    )

    val manageActivityLauncher = rememberLauncherForActivityResult(
        contract = CommonActivityResultContract(),
        onResult = { menuType = null }
    )

    val galleryActivityLauncher = rememberLauncherForActivityResult(
        contract = CommonActivityResultContract(),
        onResult = { intent ->
            intent?.getStringExtra(GalleryConstants.SINGLE)?.let { viewModel.onAction(
                MyPageScreenAction.EditProfileUri(it)
            ) }
        }
    )

    val requestPermissionLauncher = rememberPermissionLauncher(
        activity = activity,
        scope = scope,
        grant = {
            val intent = Intent(activity, GalleryActivity::class.java).apply {
                putExtra(GalleryConstants.TYPE, GalleryConstants.SINGLE)
                putExtra(GalleryConstants.AGO_IMAGE_URI_ARRAY, arrayOf(state.profile))
            }
            galleryActivityLauncher.launch(intent)
            activity.slideIn()
        },
        denied = {
            isShowPermissionRequestConfirm = true
        }
    )

    MyPageScreen(
        nickNameState = viewModel.nickNameState,
        state = state,
        onAction = { action ->
            when(action) {
                is MyPageScreenAction.ShowWebView -> {
                    webViewUrl = action.url
                    menuType = MyPageMenuType.WebView(action.url)
                }
                is MyPageScreenAction.ShowContact -> {
                    menuType = MyPageMenuType.Contact
                }
                is MyPageScreenAction.ShowManage -> {
                    menuType = MyPageMenuType.Manage
                }
                is MyPageScreenAction.ShowGalleryProfile -> {
                    requestPermissionLauncher.launch(PermissionConstants.GALLERY_PERMISSIONS)
                }
                else -> viewModel.onAction(action)
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
        is MyPageMenuType.Manage -> {
            val intent = Intent(activity, ManageActivity::class.java)
            manageActivityLauncher.launch(intent)
            activity.slideIn()
        }
        else -> Unit
    }
}

@Composable
fun MyPageScreen(
    nickNameState: TextFieldState,
    state: MyPageScreenState,
    onAction: (MyPageScreenAction) -> Unit
) {

    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .addFocusCleaner(focusManager)
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
            Box(
                contentAlignment = Alignment.BottomEnd
            ) {
                state.profile?.let { profileUri ->
                    SubcomposeAsyncImage(
                        model = profileUri,
                        modifier = Modifier
                            .clip(shape = CircleShape)
                            .size(100.dp)
                            .aspectRatio(1f),
                        loading = { ShimmerLoadingAnimation() },
                        error = { ProfileNoneImage() },
                        contentScale = ContentScale.Crop,
                        contentDescription = "profile image"
                    )
                } ?: run {
                    ProfileNoneImage()
                }
                Box(
                    modifier = Modifier.clickableNoRipple { onAction.invoke(MyPageScreenAction.ShowGalleryProfile) },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.btn_my_profile_edit),
                        contentDescription = "profile edit"
                    )
                    Image(
                        painter = painterResource(id = R.drawable.btn_my_profile_edit_camera),
                        contentDescription = "profile edit camera"
                    )
                }
            }
            Box(
                modifier = Modifier.padding(top = 16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    HasTextField(
                        textState = nickNameState,
                        focusRequester = focusRequester,
                        fontSize = 18.dp,
                        fontWeight = HasFontWeight.Bold,
                        textAlign = TextAlign.Center,
                        hint = state.nickName,
                        hintColor = ColorFamilyBlack20AndWhite,
                    )
                }
                Image(
                    painter = painterResource(id = if(isSystemInDarkTheme()) R.drawable.btn_my_nick_edit_pencil_dark else R.drawable.btn_my_nick_edit_pencil_light),
                    contentDescription = "profile edit nick"
                )
            }
        }
        SpacerLineBox()
        Column(
            modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
        ) {
            MyPageItem(
                text = "Manage Categories / Tags",
                onClick = { onAction.invoke(MyPageScreenAction.ShowManage) }
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
        /** dark mode */
//        Box(
//            modifier = Modifier.padding(horizontal = 16.dp)
//        ) {
//            Row(
//                modifier = Modifier
//                    .border(
//                        width = 1.dp,
//                        color = Color(0xFFBFD320),
//                        shape = RoundedCornerShape(size = 10.dp)
//                    )
//                    .clip(shape = RoundedCornerShape(size = 10.dp))
//                    .background(color = MaterialTheme.colors.surface)
//                    .fillMaxWidth()
//                    .height(55.dp)
//                    .padding(start = 16.dp, end = 12.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Image(
//                    modifier = Modifier.size(20.dp),
//                    painter = painterResource(id = com.myStash.android.common.resource.R.drawable.img_dark_mode),
//                    contentDescription = ""
//                )
//                HasText(
//                    modifier = Modifier
//                        .padding(start = 6.dp)
//                        .weight(1f),
//                    text = "Dark Mode",
//                    fontSize = 16.dp
//                )
//            }
//        }
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(50.dp))

        state.version?.let { version ->
            HasText(
                text = "Version $version",
                fontSize = 14.dp,
                color = Color(0xFF707070),
                fontWeight = HasFontWeight.Bold
            )
        }
        HasText(
            modifier = Modifier.padding(top = 6.dp),
            text = "This is the latest version",
            fontSize = 13.dp,
            color = Color(0xFF909090),
        )
        HasText(
            modifier = Modifier
                .padding(top = 33.dp, bottom = 36.dp)
                .clickableNoRipple { onAction.invoke(MyPageScreenAction.ShowWebView(WebViewConstants.URL_TERMS)) },
            text = "Terms of Service",
            fontSize = 14.dp,
            color = Color(0xFF707070),
            textDecoration = TextDecoration.Underline,
        )
    }
}