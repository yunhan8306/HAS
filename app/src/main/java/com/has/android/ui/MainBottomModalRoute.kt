package com.has.android.ui

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.has.android.common.util.CommonActivityResultContract
import com.has.android.common.util.isNotNull
import com.has.android.design_system.animation.slideIn
import com.has.android.design_system.ui.component.modal.StyleBottomModal
import com.has.android.feature.essential.ui.HasScreenAction
import com.has.android.feature.essential.HasViewModel
import com.has.android.feature.item.ItemActivity
import com.has.android.common.util.constants.ItemConstants
import com.has.android.feature.item.item.ItemTab
import com.has.android.feature.style.ui.StyleScreenAction
import com.has.android.feature.style.StyleViewModel
import com.has.android.navigation.MainNavType
import com.has.android.navigation.navigate
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun MainBottomModalRoute(
    navHostController: NavHostController,
    hasViewModel: HasViewModel = hiltViewModel(),
    styleViewModel: StyleViewModel = hiltViewModel()
) {

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val hasSelectedList = hasViewModel.collectAsState().value.selectedHasList
    val selectedStyle = styleViewModel.collectAsState().value.selectedStyle

    val activity = LocalContext.current as ComponentActivity
    val itemActivityLauncher = rememberLauncherForActivityResult(
        contract = CommonActivityResultContract(),
        onResult = { intent ->
            intent?.getStringExtra(ItemConstants.CMD_COMPLETE)?.let { nav ->
                hasViewModel.onAction(HasScreenAction.ResetSelectHas)
                styleViewModel.onAction(StyleScreenAction.ResetSelectStyle)

                val navType = when(nav) {
                    ItemConstants.CMD_HAS -> MainNavType.HAS
                    ItemConstants.CMD_STYLE -> MainNavType.STYLE
                    ItemConstants.CMD_FEED -> MainNavType.FEED
                    else -> MainNavType.HAS
                }

                navHostController.navigate(navType)
            }
        }
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        MainHasBottomModal(
            isShow = currentRoute == MainNavType.HAS.name && hasSelectedList.isNotEmpty(),
            hasList = hasSelectedList,
            onDelete = { hasViewModel.onAction(HasScreenAction.SelectHas(it)) },
            onSelect = {
                val intent = Intent(activity, ItemActivity::class.java).apply {
                    putExtra(ItemConstants.CMD_TAB_NAME, ItemTab.STYLE.name)
                    putExtra(ItemConstants.CMD_STYLE, hasSelectedList.map { it.id }.toTypedArray())
                }
                itemActivityLauncher.launch(intent)
                activity.slideIn()
            },
            onCancel = { hasViewModel.onAction(HasScreenAction.ResetSelectHas) },
        )
        StyleBottomModal(
            isShow = currentRoute == MainNavType.STYLE.name && selectedStyle.isNotNull(),
            onSelect = {
                selectedStyle?.let {
                    val intent = Intent(activity, ItemActivity::class.java).apply {
                        putExtra(ItemConstants.CMD_TAB_NAME, ItemTab.FEED.name)
                        putExtra(ItemConstants.CMD_STYLE_ID, selectedStyle.id)
                    }
                    itemActivityLauncher.launch(intent)
                    activity.slideIn()
                }
            },
            onCancel = { styleViewModel.onAction(StyleScreenAction.ResetSelectStyle) },
        )
    }
}