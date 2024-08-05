package com.myStash.android.design_system.ui.component.has

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.myStash.android.common.resource.R
import com.myStash.android.common.util.isNotNull
import com.myStash.android.core.model.Has
import com.myStash.android.core.model.Tag
import com.myStash.android.design_system.ui.component.tag.TagHasChipItem
import com.myStash.android.design_system.ui.component.text.HasFontWeight
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.design_system.util.ShimmerLoadingAnimation
import kotlinx.coroutines.delay

@Composable
fun HasMainItem(
    has: Has,
    mode: Boolean,
    selectedNumber: Int?,
    tagList: List<Tag>,
    selectTagList: List<Tag>,
    onSelectHas: (Has) -> Unit,
    onEditHas: (Has) -> Unit,
    onDeleteHas: (Has) -> Unit
) {
    var shortClick by remember { mutableStateOf(false) }

    LaunchedEffect(shortClick) {
        if(shortClick) {
            delay(3000)
            shortClick = false
        }
    }

    Box(
        modifier = Modifier
            .border(
                width = if(selectedNumber.isNotNull()) 2.dp else 1.dp,
                color = if(selectedNumber.isNotNull()) Color(0xFF716DF6) else Color(0xFFE1E1E1),
                shape = RoundedCornerShape(size = 12.dp)
            )
            .clip(shape = RoundedCornerShape(size = 12.dp))
            .combinedClickable(
                onLongClick = { onSelectHas.invoke(has) },
                onClick = { shortClick = !shortClick },
            ),
        contentAlignment = Alignment.Center
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .aspectRatio(158f / 210f)
                .background(color = Color(0x66000000), shape = RoundedCornerShape(size = 12.dp))
                .fillMaxSize()
                .alpha(if (shortClick) 0.4f else 1f),
            loading = { ShimmerLoadingAnimation() },
            error = { ShimmerLoadingAnimation() },
            contentScale = ContentScale.Crop,
            model = has.imagePath, // 엑박 이미지 필요?
            contentDescription = "has image"
        )

        if(shortClick) {
            FlowRow(
                modifier = Modifier.padding(top = 17.dp, bottom = 17.dp, start = 15.dp, end = 15.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                tagList.forEach { tag ->
                    TagHasChipItem(
                        name = tag.name,
                        isSelected = tag.checkSelected(selectTagList)
                    )
                }
            }
        }
    }

    if(mode) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .clickable { onSelectHas.invoke(has) }
                ,
                contentAlignment = Alignment.Center
            ) {
                if(selectedNumber.isNotNull()) {
                    Image(
                        modifier = Modifier.size(18.dp),
                        painter = painterResource(id = R.drawable.btn_select_has),
                        contentDescription = "has select"
                    )
                    HasText(
                        text = selectedNumber.toString(),
                        color = Color.White,
                        fontSize = 12.dp,
                        fontWeight = HasFontWeight.Bold
                    )
                } else {
                    Image(
                        modifier = Modifier.size(18.dp),
                        painter = painterResource(id = R.drawable.btn_no_select_has),
                        contentDescription = "has select"
                    )
                }
            }
        }
    }
}