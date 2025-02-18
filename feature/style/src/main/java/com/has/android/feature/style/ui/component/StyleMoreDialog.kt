package com.has.android.feature.style.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.has.android.core.model.StyleScreenModel
import com.has.android.core.model.Tag
import com.has.android.core.model.Type
import com.has.android.design_system.ui.color.ColorFamilyWhiteAndGray600
import com.has.android.design_system.ui.component.text.HasFontWeight
import com.has.android.design_system.ui.component.text.HasText
import com.has.android.feature.item.feed.AddStyleHasItem

@Composable
fun StyleMoreDialog(
    styleScreenModel: StyleScreenModel?,
    typeTotalList: List<Type>,
    tagTotalList: List<Tag>,
    onDismiss: () -> Unit = {}
) {
    styleScreenModel?.let { style ->
        Dialog(
            properties = DialogProperties(usePlatformDefaultWidth = false),
            onDismissRequest = onDismiss
        ) {
            Box(
                modifier = Modifier
                    .shadow(elevation = 6.dp)
                    .width(328.dp)
                    .heightIn(max = 580.dp)
                    .background(color = ColorFamilyWhiteAndGray600, shape = RoundedCornerShape(size = 12.dp))
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn{
                    item { 
                        HasText(
                            modifier = Modifier.padding(top = 20.dp, bottom = 22.dp),
                            text = "Has",
                            fontSize = 18.dp,
                            fontWeight = HasFontWeight.Bold
                        )
                    }
                    items(
                        items = style.hasList
                    ) { has ->
                        AddStyleHasItem(
                            has = has,
                            typeTotalList = typeTotalList,
                            tagTotalList = tagTotalList
                        )
                    }
                    item { Box(modifier = Modifier.height(4.dp)) }
                }
            }
        }
    }
}