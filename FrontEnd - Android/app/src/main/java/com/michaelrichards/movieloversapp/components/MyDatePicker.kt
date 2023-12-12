package com.michaelrichards.movieloversapp.components

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import com.michaelrichards.movieloversapp.R
import com.michaelrichards.movieloversapp.ui.theme.accentColor
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MyDatePicker(
    modifier: Modifier = Modifier,
    valueState: MutableState<LocalDate>,
    label: String,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    isError: Boolean = false,
    context: Context,
    iconTint: Color = Color.White
) {

    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context, { _, year1, month1, day1 ->
            valueState.value = LocalDate.of(year1, month1+1, day1)
        }, year, month, day
    )
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth().clickable {
                datePickerDialog.show()
            },
        value = valueState.value.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")).toString(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor =  Color.White,
            unfocusedTextColor = Color.White,
            focusedLabelColor = Color.White,
            focusedBorderColor = accentColor,
            unfocusedBorderColor = accentColor,
            unfocusedLabelColor = Color.White,
            disabledBorderColor = accentColor,
            disabledTextColor = Color.White,
            disabledLabelColor = Color.White
        ),
        isError = isError,
        enabled = false,
        readOnly = true,
        singleLine = true,
        onValueChange = {},
        label= { Text(text = label)},
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.calender),
                contentDescription = "Select Date",
                tint = iconTint
                )
        },
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        keyboardActions = keyboardActions
    )
}