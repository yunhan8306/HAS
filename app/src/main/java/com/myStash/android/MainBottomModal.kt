package com.myStash.android

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.myStash.android.core.model.Has
import com.myStash.android.core.model.StyleScreenModel
import com.myStash.android.feature.essential.HasScreenAction
import com.myStash.android.feature.essential.HasViewModel
import com.myStash.android.feature.style.StyleScreenAction
import com.myStash.android.feature.style.StyleViewModel
import com.myStash.android.navigation.MainNavType
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun MainBottomModal(
    navHostController: NavHostController,
    showAddStyleItemActivity: (List<Has>) -> Unit,
    showAddFeedItemActivity: (StyleScreenModel) -> Unit,
    hasViewModel: HasViewModel = hiltViewModel(),
    styleViewModel: StyleViewModel = hiltViewModel()
) {

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val hasSelectedList = hasViewModel.collectAsState().value.selectedHasList
    val selectedStyle = styleViewModel.collectAsState().value.selectedStyle

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        when(currentRoute) {
            MainNavType.HAS.name -> {
                MainHasBottomModel(
                    hasList = hasSelectedList,
                    onDelete = { hasViewModel.onAction(HasScreenAction.SelectHas(it)) },
                    onSelect = { showAddStyleItemActivity.invoke(hasSelectedList) },
                    onCancel = { hasViewModel.onAction(HasScreenAction.ResetSelectHas) },
                )
            }
            MainNavType.STYLE.name -> {
                MainStyleBottomModel(
                    selectedStyle = selectedStyle,
                    onSelect = { selectedStyle?.let { showAddFeedItemActivity.invoke(it) } },
                    onCancel = { styleViewModel.onAction(StyleScreenAction.SelectStyle(selectedStyle)) },
                )
            }
            else -> Unit
        }
    }
}