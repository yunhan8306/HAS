package com.has.android.feature.manage.type

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.has.android.common.resource.R
import com.has.android.common.util.isNotNull
import com.has.android.design_system.ui.color.ColorFamilyBlack20AndWhite
import com.has.android.design_system.ui.color.ColorFamilyGray200AndGray600
import com.has.android.design_system.ui.color.ColorFamilyGray350AndGray400
import com.has.android.design_system.ui.color.ColorFamilyLime700AndLime200
import com.has.android.design_system.ui.component.text.HasText
import com.has.android.design_system.ui.component.text.HasTextField
import com.has.android.design_system.ui.theme.clickableNoRipple
import com.has.android.design_system.util.addFocusCleaner
import com.has.android.feature.manage.component.ManageAware
import com.has.android.feature.manage.component.ManageTextField

@Composable
fun ManageTypeScreen(
    state: ManageTypeState,
    scrollState: LazyListState,
    addTypeTextState: TextFieldState,
    onAction: (ManageTypeAction) -> Unit
) {

    val focusManager = LocalFocusManager.current

    val isEdit by remember(state.focusType?.id) {
        derivedStateOf {
            state.focusType.isNotNull()
        }
    }

    ManageAware {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .addFocusCleaner(focusManager),
            state = scrollState
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(top = 12.dp)
                ) {
                    if (!isEdit) {
                        ManageTextField(
                            textState = addTypeTextState,
                            hint = "원하는 카테고리를 등록하세요.",
                            add = {
                                focusManager.clearFocus()
                                onAction.invoke(ManageTypeAction.AddType)
                            }
                        )
                    }
                }
            }
            items(
                items = state.typeTotalList,
                key = { it.id!! }
            ) { type ->

                val isSelected by remember(state.focusType?.id) {
                    derivedStateOf {
                        state.focusType?.id == type.id
                    }
                }
                val typeTextFieldState = rememberTextFieldState(type.name)
                val focusRequester = remember { FocusRequester() }

                LaunchedEffect(isSelected) {
                    if(isSelected) focusRequester.requestFocus()
                }

                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(end = 4.dp)
                            .clickableNoRipple {
                                focusManager.clearFocus()
                                if(!isSelected) onAction.invoke(ManageTypeAction.FocusType(null))
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.width(4.dp))
                        if(isSelected) {
                            HasTextField(
                                modifier = Modifier.weight(1f),
                                textState = typeTextFieldState,
                                focusRequester = focusRequester,
                                hint = type.name,
                                hintColor = ColorFamilyBlack20AndWhite
                            )
                        } else {
                            HasText(
                                modifier = Modifier.weight(1f),
                                text = type.name,
                                color = if(isEdit) ColorFamilyGray350AndGray400 else ColorFamilyBlack20AndWhite
                            )
                        }

                        when {
                            isSelected -> {
                                HasText(
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .clickableNoRipple { onAction.invoke(ManageTypeAction.UpdateType(type.copy(name = typeTextFieldState.text.toString()))) },
                                    text = "Complete",
                                    color = ColorFamilyLime700AndLime200,
                                    fontSize = 13.dp,
                                )
                            }
                            !isEdit -> {
                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clickableNoRipple { onAction.invoke(ManageTypeAction.FocusType(type)) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        modifier = Modifier.size(20.dp),
                                        painter = painterResource(id = R.drawable.img_edit),
                                        contentDescription = "edit",
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clickableNoRipple { onAction.invoke(ManageTypeAction.RemoveType(type)) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        modifier = Modifier.size(20.dp),
                                        painter = painterResource(id = R.drawable.img_remove),
                                        contentDescription = "remove",
                                    )
                                }
                            }
                        }
                        Box(modifier = Modifier.width(8.dp))
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(ColorFamilyGray200AndGray600)
                    )
                }
            }
        }
    }
}