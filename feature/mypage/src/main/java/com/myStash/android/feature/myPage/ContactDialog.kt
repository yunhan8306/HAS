package com.myStash.android.feature.myPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.myStash.android.common.resource.R
import com.myStash.android.design_system.ui.component.content.ContentText
import com.myStash.android.design_system.ui.component.content.ContentTextField
import com.myStash.android.design_system.ui.component.header.HasHeader
import com.myStash.android.design_system.ui.component.text.HasFontWeight
import com.myStash.android.design_system.ui.component.text.HasText

@Composable
fun ContactDialog(
    state: ContactDialogState,
    contentTextState: TextFieldState,
    onAction: (ContactAction) -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        val scrollState = rememberScrollState()
        var dropDownExpanded by remember { mutableStateOf(false) }
        val dropDownScrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize()
        ) {
            HasHeader(
                text = "Contact Us",
                onBack = onDismiss
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                HasText(
                    modifier = Modifier.padding(top = 24.dp),
                    text = "문의 항목",
                    fontSize = 14.dp,
                    fontWeight = HasFontWeight.Bold
                )

                ExposedDropdownMenuBox(
                    expanded = dropDownExpanded,
                    onExpandedChange = { dropDownExpanded = !dropDownExpanded },
                ) {
                    Column {
                        ContentText(
                            text = state.selectedType ?: "항목 선택",
                            textColor = if(state.selectedType != null) Color(0xFF202020) else Color(0xFFA4A4A4),
                            borderColor = if(dropDownExpanded) Color(0xFF202020) else Color(0xFFE1E1E1),
                            onClick = {},
                            endContent = {
                                Image(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(id = R.drawable.btn_down),
                                    contentDescription = "btn down"
                                )
                            }
                        )
                        Box(modifier = Modifier.padding(top = 7.dp))
                        ExposedDropdownMenu(
                            modifier = Modifier
                                .exposedDropdownSize()
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
                                        .background(if (isSelected) Color(0xFFFCFFE7) else Color.White),
                                    content = {
                                        HasText(
                                            text = type,
                                            color = if(isSelected) Color(0xFF8A9918) else Color(0xFF202020),
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

                HasText(
                    modifier = Modifier.padding(top = 24.dp),
                    text = "문의 내용",
                    fontSize = 32.dp,
                    fontWeight = HasFontWeight.Bold
                )

                ContentTextField(
                    textState = contentTextState,
                    hint = "내용 입력",
                    delete = {}
                )

            }
        }
    }
}

data class ContactDialogState(
    val typeList: List<String> = emptyList(),
    var selectedType: String? = null,
    var selectedImages: List<String> = emptyList(),
    var content: String = "",
) {
    companion object {

    }
}

sealed interface ContactAction {
    data class SelectType(val type: String) : ContactAction
    data class ChangeContent(val content: String) : ContactAction
    data class ChangeImages(val images: List<String>) : ContactAction

}