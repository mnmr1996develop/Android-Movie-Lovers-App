package com.michaelrichards.movieloversapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun CustomBasicTextField(
    modifier: Modifier = Modifier,
    value: MutableState<String> = mutableStateOf("Default Text"),
    onValueChange: (String) -> Unit = {value.value = it}

) {


    BasicTextField(
        value = value.value,
        onValueChange = {onValueChange(it)},
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)
            .padding(8.dp)
            .clip(
                RoundedCornerShape(12.dp)
            ),
        minLines = 5,
        maxLines = 10,
        decorationBox = { innerTextField ->
            Box {
                if (value.value.isEmpty()){
                    Text(
                        text = "Right A Review",
                    )
                }
                innerTextField()
            }
        }
    )
}