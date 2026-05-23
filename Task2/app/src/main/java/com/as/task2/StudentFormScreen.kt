package com.`as`.task2

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.`as`.task2.ui.theme.*
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentFormScreen() {
    val context = LocalContext.current

    // State ცვლადები
    var nameState by remember { mutableStateOf("") }
    var surnameState by remember { mutableStateOf("") }
    var emailState by remember { mutableStateOf("") }
    var dateState by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("") }
    var isAgreed by remember { mutableStateOf(false) }

    val directions = listOf("Android", "iOS", "Web", "Backend", "AI/ML")

    // Date Picker
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            // სწორი ფორმატი
            val formatted = "%02d/%02d/%04d".format(dayOfMonth, month + 1, year)
            dateState = formatted
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Column {
                Text(
                    text = "Student Form",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "შეიყვანეთ თქვენი მონაცემები",
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.75f),
                    fontFamily = FontFamily.Monospace
                )
            }

            FormLabel("სახელი")
            CustomTextField(
                value = nameState,
                onValueChange = { nameState = it },
                placeholder = "შეიყვანეთ სახელი"
            )

            FormLabel("გვარი")
            CustomTextField(
                value = surnameState,
                onValueChange = { surnameState = it },
                placeholder = "შეიყვანეთ გვარი"
            )

            FormLabel("Email")
            CustomTextField(
                value = emailState,
                onValueChange = { emailState = it },
                placeholder = "შეიყვანეთ email"
            )

            FormLabel("დაბადების თარიღი")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        width = 1.dp,
                        color = Main.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { datePickerDialog.show() }
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (dateState.isEmpty()) "აირჩიეთ თარიღი" else dateState,
                        color = if (dateState.isEmpty()) TextSecondary else TextPrimary,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 15.sp
                    )
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = "Pick date",
                        tint = Main
                    )
                }
            }

            // RadioButtons
            Spacer(modifier = Modifier.height(4.dp))
            FormLabel("აირჩიეთ მიმართულება")
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(CardBg)
                    .padding(16.dp)
                    .selectableGroup(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                directions.forEach { option ->
                    val isSelected = selectedOption == option
                    val bgColor by animateColorAsState(
                        targetValue = if (isSelected) Main.copy(alpha = 0.15f) else Color.Transparent,
                        label = "radioColor"
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(bgColor)
                            .selectable(
                                selected = isSelected,
                                onClick = { selectedOption = option },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = null,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Main,
                                unselectedColor = TextSecondary
                            )
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = option,
                            color = if (isSelected) Main else TextPrimary,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 15.sp
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(CardBg)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ვეთანხმები წესებს",
                    color = TextPrimary,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = isAgreed,
                    onCheckedChange = { isAgreed = it },
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {


                    // ვამოწმებ არის თუ არა ყველა ველი შევსებული და მიმართულება არის თუ არა არჩეული
                    val allFilled = nameState.isNotBlank()
                            && surnameState.isNotBlank()
                            && emailState.isNotBlank()
                            && dateState.isNotBlank()
                    val optionChosen = selectedOption.isNotEmpty()

                    if (!allFilled || !optionChosen || !isAgreed) {
                        Toast.makeText(context, "შეავსეთ ყველა ველი!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "მონაცემები გაიგზავნა!", Toast.LENGTH_SHORT).show()

                        // ვშლი მონაცემებს
                        nameState = ""
                        surnameState = ""
                        emailState = ""
                        dateState = ""
                        selectedOption = ""
                        isAgreed = false
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(14.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = Main,
                            shape = RoundedCornerShape(14.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Submit",
                        color = DarkBg,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun FormLabel(text: String) {
    Text(
        text = text,
        color = Main,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp,
        modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
    )
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = TextSecondary,
                fontFamily = FontFamily.Monospace,
                fontSize = 14.sp
            )
        },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    )
}