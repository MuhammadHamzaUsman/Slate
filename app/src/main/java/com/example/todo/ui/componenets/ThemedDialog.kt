package com.example.todo.ui.componenets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.todo.R
import com.example.todo.ui.theme.AppColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemedDialog(
    message: String,
    onYes: () -> Unit,
    onNo: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(
                width = 2.dp,
                color = AppColor.Outline
            ),
            colors = CardDefaults.cardColors(
                containerColor = AppColor.Surface,
                contentColor = AppColor.OnSurfaceAndBackground
            ),
            modifier = modifier
                .width(300.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    painter = painterResource(R.drawable.warning_icon),
                    contentDescription = message,
                    modifier = Modifier.height(51.dp)
                )

                Text(
                    text = message,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(54.dp)
                ) {
                    ThemedLabel(
                        text = "No",
                        color = AppColor.Outline,
                        onClick = onNo
                    )

                    ThemedLabel(
                        text = "Yes",
                        color = AppColor.Error,
                        onClick = onYes
                    )
                }
            }
        }
    }
}

@Composable
private fun ThemedLabel(
    text: String,
    color: Color,
    onClick: () -> Unit,
){
    Label(
        text = text,
        textColor = AppColor.OnSurfaceAndBackground,
        fontSize = 14.sp,
        backgroundColor = color,
        contentPadding = PaddingValues(vertical = 4.dp, horizontal = 8.dp),
        cornerRadius = 6.dp,
        modifier = Modifier
            .size(
                width = 50.dp,
                height = 30.dp
            )
            .clickable(onClick = onClick)
    )
}

@Preview
@Composable
private fun ThemedDialogPreview() {
    ThemedDialog(
        message = "Do you want to delete tasks",
        onYes = { },
        onNo = { },
        onDismiss = { },
    )
}