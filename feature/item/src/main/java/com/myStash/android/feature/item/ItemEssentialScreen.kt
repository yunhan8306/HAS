package com.myStash.android.feature.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.Type
import com.myStash.android.core.model.testManTypeTotalList
import com.myStash.android.core.model.testTagList
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.SearchText
import com.myStash.android.design_system.ui.TagDeleteChipItem
import com.myStash.android.design_system.ui.type.TypeChipItem
import com.myStash.android.design_system.util.ShimmerLoadingAnimation

@Composable
fun ItemEssentialScreen(
    imageUri: String?,
    tagInputState: TextFieldState,
    selectedType: Type?,
    typeTotalList: List<Type>,
    selectType: (Type) -> Unit,
    selectedTagList: List<Tag>,
    selectTag: (Tag) -> Unit,
    search: () -> Unit,
    saveItem: () -> Unit,
    showGalleryActivity: () -> Unit,
    onBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ItemHeader(
            onClick = onBack
        )
        Box(
            modifier = Modifier.fillMaxWidth().height(40.dp).background(Color.Red),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "탭 선택 공간")
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 12.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { showGalleryActivity.invoke() }
            ) {
                SubcomposeAsyncImage(
                    model = imageUri,
                    contentDescription = "gallery image",
                    modifier = Modifier.aspectRatio(1f),
                    contentScale = ContentScale.Crop,
                    loading = { ShimmerLoadingAnimation() },
                    error = { ShimmerLoadingAnimation() }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "카테고리")
            Spacer(modifier = Modifier.height(20.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                typeTotalList.forEach { type ->
                    TypeChipItem(
                        name = type.name,
                        isSelected = type.name == selectedType?.name,
                        onClick = { selectType.invoke(type) }
                    )
                }
            }

            Text(text = "태그")
            Spacer(modifier = Modifier.height(20.dp))

            SearchText(
                modifier = Modifier.fillMaxWidth(),
                textState = tagInputState,
                hint = "브랜드, 분위기, 계절, 컬러",
                onClick = search
            )
            Spacer(modifier = Modifier.height(20.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                selectedTagList.forEach { tag ->
                    TagDeleteChipItem(
                        name = tag.name,
                        onClick = { selectTag.invoke(tag) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(50.dp)
                    .background(Color.Gray)
                    .clickable { saveItem.invoke() }
            ) {
                Text(text = "save")
            }
        }
    }
}

@DevicePreviews
@Composable
fun ItemEssentialScreenPreview() {
    ItemEssentialScreen(
        imageUri = null,
        tagInputState = TextFieldState(),
        selectedType = Type(id = 1, name = "상의"),
        typeTotalList = testManTypeTotalList,
        selectType = {},
        selectedTagList = testTagList,
        selectTag = {},
        search = {},
        showGalleryActivity = {},
        saveItem = {},
        onBack = {}
    )
}