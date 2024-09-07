package com.myStach.android.feature.contact

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.myStash.android.common.resource.R
import com.myStash.android.design_system.ui.color.ColorFamilyBlack20AndWhite
import com.myStash.android.design_system.ui.color.ColorFamilyGray500AndGray900
import com.myStash.android.design_system.ui.color.ColorFamilyLime100AndGray550
import com.myStash.android.design_system.ui.color.ColorFamilyLime700AndLime300
import com.myStash.android.design_system.ui.color.ColorFamilyWhiteAndGray600
import com.myStash.android.design_system.ui.color.Purple
import com.myStash.android.design_system.ui.component.button.HasButton
import com.myStash.android.design_system.ui.component.content.ContentText
import com.myStash.android.design_system.ui.component.content.ContentTextField
import com.myStash.android.design_system.ui.component.header.HasHeader
import com.myStash.android.design_system.ui.component.photo.SelectPhotoItem
import com.myStash.android.design_system.ui.component.photo.UnselectPhotoItem
import com.myStash.android.design_system.ui.component.text.HasFontWeight
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.design_system.util.addFocusCleaner
import com.myStash.android.design_system.util.rememberImeState

@Composable
fun ContactScreen(
    state: ContactState,
    contentTextState: TextFieldState,
    emailTextState: TextFieldState,
    onAction: (ContactAction) -> Unit,
) {

    val scrollState = rememberScrollState()
    var dropDownExpanded by remember { mutableStateOf(false) }
    val dropDownScrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val imeState = rememberImeState()

    LaunchedEffect(imeState.value) {
        if(!imeState.value) focusManager.clearFocus()
    }

    LaunchedEffect(state.isCompleted) {
        if(state.isCompleted) scrollState.animateScrollTo(scrollState.maxValue)
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
            .addFocusCleaner(focusManager)
    ) {
        HasHeader(
            modifier = Modifier.background(MaterialTheme.colors.surface),
            text = "Contact Us",
            onBack = { onAction.invoke(ContactAction.Finish) }
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .imePadding()
                .verticalScroll(scrollState)
        ) {
            Row(
                modifier = Modifier.padding(top = 24.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HasText(
                    text = "문의 항목",
                    fontSize = 14.dp,
                    fontWeight = HasFontWeight.Bold
                )
                HasText(
                    text = "*",
                    color = Purple,
                    fontSize = 14.dp
                )
            }

            ExposedDropdownMenuBox(
                expanded = dropDownExpanded,
                onExpandedChange = { dropDownExpanded = !dropDownExpanded },
            ) {
                Column {
                    ContentText(
                        text = state.selectedType ?: "항목 선택",
                        isTextFocus = state.selectedType != null,
                        isBorderFocus = dropDownExpanded,
                        onClick = {},
                        endContent = {
                            Image(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.img_down_gallery),
                                contentDescription = "btn down"
                            )
                        }
                    )
                    Box(modifier = Modifier.padding(top = 7.dp))
                    ExposedDropdownMenu(
                        modifier = Modifier
                            .exposedDropdownSize()
                            .background(ColorFamilyWhiteAndGray600)
                            .height(220.dp)
                            .verticalScroll(dropDownScrollState),
                        expanded = dropDownExpanded,
                        onDismissRequest = { dropDownExpanded = false },
                    ) {
                        state.typeList.forEach { type ->
                            val isSelected by remember {
                                derivedStateOf {
                                    state.selectedType == type
                                }
                            }

                            DropdownMenuItem(
                                modifier = Modifier
                                    .width(1000.dp)
                                    .background(if (isSelected) ColorFamilyLime100AndGray550 else ColorFamilyWhiteAndGray600),
                                content = {
                                    HasText(
                                        text = type,
                                        color = if(isSelected) ColorFamilyLime700AndLime300 else ColorFamilyBlack20AndWhite,
                                        fontWeight = if(isSelected) HasFontWeight.Bold else HasFontWeight.Medium,
                                    )
                                },
                                onClick = {
                                    onAction.invoke(ContactAction.SelectType(type))
                                    dropDownExpanded = false
                                },
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.padding(top = 24.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HasText(
                    text = "문의 내용",
                    fontSize = 14.dp,
                    fontWeight = HasFontWeight.Bold
                )
                HasText(
                    text = "*",
                    color = Purple,
                    fontSize = 14.dp
                )
            }
            ContactContentTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(184.dp),
                textState = contentTextState,
                hint = "내용 입력"
            )
            Row(
                modifier = Modifier.padding(top = 32.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HasText(
                    modifier = Modifier.padding(end = 4.dp),
                    text = "사진 첨부",
                    fontSize = 14.dp,
                    fontWeight = HasFontWeight.Bold
                )
                HasText(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "(선택)",
                    fontSize = 12.dp,
                    color = ColorFamilyGray500AndGray900
                )
            }

            LazyRow {
                if (state.selectedImages.size < 5) {
                    item {
                        UnselectPhotoItem(
                            cnt = state.selectedImages.size,
                            onClick = { onAction.invoke(ContactAction.ShowGalleryActivity) }
                        )
                    }
                }
                items(state.selectedImages) { uri ->
                    Row {
                        SelectPhotoItem(
                            imageUri = uri,
                            onClick = { onAction.invoke(ContactAction.ShowGalleryActivity) }
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.padding(top = 32.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HasText(
                    text = "이메일",
                    fontSize = 14.dp,
                    fontWeight = HasFontWeight.Bold
                )
                HasText(
                    text = "*",
                    color = Purple,
                    fontSize = 14.dp
                )
            }
            ContentTextField(
                textState = emailTextState,
                hint = "회신 받을 이메일",
                delete = { onAction.invoke(ContactAction.DeleteEmail) }
            )
            Box(modifier = Modifier.height(32.dp))

            if(state.isCompleted) {
                HasButton(
                    text = "등록하기",
                    onClick = { onAction.invoke(ContactAction.Confirm) }
                )
                Box(modifier = Modifier.height(16.dp))
            }
        }
    }
}