package com.myStash.feature.item

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.myStash.common.compose.activityViewModel
import com.myStash.core.model.Item
import kotlinx.coroutines.flow.SharedFlow
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ItemRoute(
    viewModel: ItemViewModel = hiltViewModel(),
    initItem: Item?,
    addImageSharedFlow: SharedFlow<String?>,
    showGalleryActivity: () -> Unit,
    finishActivity: () -> Unit
) {

    val state by viewModel.collectAsState()
    val tagInputState = viewModel.addTagState
    val selectedPhoto by addImageSharedFlow.collectAsStateWithLifecycle(initialValue = null)

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            ItemSideEffect.Finish -> finishActivity.invoke()
            else -> Unit
        }
    }

    LaunchedEffect(Unit) {
        viewModel.initFetch(initItem)
    }

    LaunchedEffect(selectedPhoto) {
        selectedPhoto?.let { viewModel.addImage(it) }
    }

    ItemScreen(
        state = state,
        tagInputState = tagInputState,
        saveItem = viewModel::saveItem,
        showGalleryActivity = showGalleryActivity
    )
}