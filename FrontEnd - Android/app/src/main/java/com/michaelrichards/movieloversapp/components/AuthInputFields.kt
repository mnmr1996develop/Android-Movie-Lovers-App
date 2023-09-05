package com.michaelrichards.movieloversapp.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.michaelrichards.movieloversapp.ui.theme.accentColor

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AuthInputFields(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    label: String,
    enabled: Boolean = true,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    trailingIcon: @Composable () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false

) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it.trim() },
        modifier = modifier
            .fillMaxWidth(),
        label = { Text(text = label) },
        keyboardActions = onAction,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.White,
            focusedLabelColor = Color.White,
            errorBorderColor = Color.Red,
            focusedBorderColor = accentColor,
            unfocusedBorderColor = accentColor,
            unfocusedLabelColor = Color.White
        ),
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        enabled = enabled,
        singleLine = isSingleLine,
        isError = isError
    )
}