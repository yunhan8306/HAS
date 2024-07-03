package com.myStash.android.feature.item

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.myStash.android.design_system.ui.component.header.HasHeader

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun SearchBottomSheet(
    state: TextFieldState,
    deleteInput: () -> Unit,
    onDismissRequest: () -> Unit,
) {
//    ModalBottomSheet(
//        containerColor = Color.White,
//        contentColor = Color.White,
//        dragHandle = null,
//        onDismissRequest = onDismissRequest
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(top = 24.dp)
//        ) {
//            Column(
//                modifier = Modifier.padding(horizontal = 16.dp)
//            ) {
//                ItemSelectRow {
//                    BasicTextField2(
//                    modifier = Modifier
//                        .border(
//                            width = 1.dp,
//                            color = Color(0xFF202020),
//                            shape = RoundedCornerShape(size = 10.dp)
//                        )
//                        .width(328.dp)
//                        .height(44.dp)
//                        .background(
//                            color = Color(0xFFFFFFFF),
//                            shape = RoundedCornerShape(size = 10.dp)
//                        ),
//                        state = state
//                    )
//                    Spacer(modifier = Modifier.weight(1f))
//                    Box(modifier = Modifier
//                        .size(24.dp)
//                        .clickable { deleteInput.invoke() })
//                }
//            }
//        }
//    }

//    val photoBottomSheetState =
//        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
//
//    Box(
//        modifier = Modifier.fillMaxSize()
//    ) {
//
//        ModalBottomSheetLayout(
//            modifier = Modifier
//                .shadow(
//                    elevation = 10.dp,
//                    spotColor = Color(0x14000000),
//                    ambientColor = Color(0x14000000)
//                )
//                .width(360.dp)
//                .height(684.dp),
//            sheetState = photoBottomSheetState,
//            sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
//            sheetGesturesEnabled = false,
//            sheetContent = {
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(top = 24.dp)
//                ) {
//                    Column(
//                        modifier = Modifier.padding(horizontal = 16.dp)
//                    ) {
//                        ItemSelectRow {
//                            BasicTextField2(
//                                //                    modifier = Modifier
//                                //                        .border(
//                                //                            width = 1.dp,
//                                //                            color = Color(0xFF202020),
//                                //                            shape = RoundedCornerShape(size = 10.dp)
//                                //                        )
//                                //                        .width(328.dp)
//                                //                        .height(44.dp)
//                                //                        .background(
//                                //                            color = Color(0xFFFFFFFF),
//                                //                            shape = RoundedCornerShape(size = 10.dp)
//                                //                        ),
//                                state = state
//                            )
//                            Spacer(modifier = Modifier.weight(1f))
//                            Box(modifier = Modifier
//                                .size(24.dp)
//                                .clickable { deleteInput.invoke() })
//                        }
//                    }
//                }
//            }
//        ) {
//
//            Box(
//                modifier = Modifier.size(100.dp).background(Color.Blue)
//            ) {
//
//            }
//        }
//    }


//    ScaffoldBottomSheet() {
//
//    }


    val state2 = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Expanded,
        animationSpec = tween(3000),
        confirmValueChange = {
            Log.d("qwe123", "$it")
            if(it == ModalBottomSheetValue.Hidden) onDismissRequest.invoke()
            true
        },
        skipHalfExpanded = false
    )

    LaunchedEffect(state2) {
        Log.d("qwe123", "state2 - $state2")
        Log.d("qwe123", "state2 - ${state2.currentValue}")
        Log.d("qwe123", "state2 - ${state2.isVisible}")
    }

    ModalBottomSheetLayout(
        sheetState = state2,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetContent = {

            Column(
                modifier = Modifier
                    .height(500.dp)
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                LazyColumn {
                    items(50) {
                        Text("Item $it")
                    }
                }
            }
        }
    ) {
        HasHeader(
            text = "태그 등록",
            onBack = onDismissRequest
        )
    }
    BackHandler {
        onDismissRequest.invoke()
    }
}