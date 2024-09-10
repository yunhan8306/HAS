package com.myStash.android

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
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.myStash.android.common.util.CommonActivityResultContract
import com.myStash.android.core.model.Has
import com.myStash.android.core.model.StyleScreenModel
import com.myStash.android.design_system.animation.slideIn
import com.myStash.android.design_system.ui.component.modal.StyleBottomModal
import com.myStash.android.feature.essential.HasScreenAction
import com.myStash.android.feature.essential.HasViewModel
import com.myStash.android.feature.item.ItemActivity
import com.myStash.android.feature.item.ItemConstants
import com.myStash.android.feature.item.item.ItemTab
import com.myStash.android.feature.style.StyleScreenAction
import com.myStash.android.feature.style.StyleViewModel
import com.myStash.android.navigation.MainNavType
import com.myStash.android.navigation.navigate
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
        when(currentRoute) {
            MainNavType.HAS.name -> {
                MainHasBottomModal(
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
            }
            MainNavType.STYLE.name -> {
                StyleBottomModal(
                    selectedStyle = selectedStyle,
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
            else -> Unit
        }
    }
}