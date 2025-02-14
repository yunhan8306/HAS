package com.has.android.feature.item.feed

import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.has.android.core.model.Has
import com.has.android.core.model.Tag
import com.has.android.core.model.Type
import com.has.android.core.model.getUnSelectType
import com.has.android.design_system.ui.color.ColorFamilyGray300AndGray400
import com.has.android.design_system.ui.component.text.HasText
import com.has.android.design_system.ui.component.type.TypeChipItem
import com.has.android.design_system.util.ShimmerLoadingAnimation

@Composable
fun AddStyleHasItem(
    has: Has,
    typeTotalList: List<Type>,
    tagTotalList: List<Tag>,
) {
    val hasType by remember { derivedStateOf { typeTotalList.firstOrNull { it.id == has.type }?.name ?: getUnSelectType().name } }
    val hasTagList by remember { derivedStateOf { tagTotalList.filter { has.tags.contains(it.id) } } }
    val pairTagText by remember(hasTagList) { derivedStateOf { getHasTagText(hasTagList) } }

    Box(
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(74.dp)
        ) {

            SubcomposeAsyncImage(
                modifier = Modifier
                    .aspectRatio(1f)
                    .border(
                        width = 1.dp,
                        color = ColorFamilyGray300AndGray400,
                        shape = RoundedCornerShape(size = 6.dp)
                    )
                    .clip(RoundedCornerShape(size = 6.dp))
                    .size(74.dp),
                loading = { ShimmerLoadingAnimation() },
                error = { ShimmerLoadingAnimation() },
                contentScale = ContentScale.Crop,
                model = has.imagePath,
                contentDescription = "add feed has image"
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(start = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Column {
                    TypeChipItem(
                        name = hasType
                    )
                    Column(
                        modifier = Modifier.horizontalScroll(rememberScrollState())
                    ) {
                        HasText(
                            modifier = Modifier
                                .padding(top = 6.dp)
                                .fillMaxWidth(),
                            text = pairTagText.first,
                            fontSize = 14.dp,
                            maxLines = 1
                        )
                        HasText(
                            modifier = Modifier
                                .padding(top = 2.dp)
                                .fillMaxWidth(),
                            text = pairTagText.second,
                            fontSize = 14.dp,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}

fun getHasTagText(tagList: List<Tag>): Pair<String, String> {
    var firstTagText = ""
    var secondTagText = ""

    tagList.forEach {
        if(firstTagText.length <= secondTagText.length) {
            firstTagText += ("#" + it.name + "  ")
        } else {
            secondTagText += ("#" + it.name + "  ")
        }
    }

    return Pair(firstTagText, secondTagText)
}