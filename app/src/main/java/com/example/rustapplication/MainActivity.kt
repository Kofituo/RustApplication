package com.example.rustapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.rustapplication.lib.Inputs
import com.example.rustapplication.lib.RustLog
import com.example.rustapplication.ui.theme.RustApplicationTheme

class MainActivity : ComponentActivity() {

    companion object {
        init {
            System.loadLibrary("rust_lib")
            RustLog.initialiseLogging()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RustApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Homepage()
                }
            }
        }
    }
}

@Composable
fun Homepage() {
    var firstInput by remember { mutableStateOf("") }
    var secondInput by remember { mutableStateOf("") }
    var addition by remember { mutableStateOf("Add") }
    var subtraction by remember { mutableStateOf("Subtract") }
    var multiplication by remember { mutableStateOf("Multiply") }
    var showError by remember { mutableStateOf(false) } // Indicate error if the wrong input is received

    Column {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = firstInput,
                    onValueChange = { firstInput = it },
                    label = { Text("Enter any number") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number // accept only numbers
                    ),
                    isError = showError
                )
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = secondInput,
                    onValueChange = { secondInput = it },
                    label = { Text("Enter any number") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number // accept only numbers
                    ),
                    isError = showError
                )
            }
            Spacer(modifier = Modifier.size(14.dp))
            Button(onClick = {
                val first = firstInput.toLongOrNull()
                val second = secondInput.toLongOrNull()
                if (first == null || second == null) {
                    showError = true
                    return@Button
                }
                val inputs = Inputs(first, second)
                addition = "${inputs.addition()}"
                subtraction = "${inputs.subtraction()}"
                multiplication = "${inputs.multiplication()}"
                showError = false
            }) {
                Text(text = "=")
            }
            Spacer(modifier = Modifier.size(14.dp))
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = addition,
                    onValueChange = { },
                    label = { Text("Addition") },
                    readOnly = true
                )
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = subtraction,
                    onValueChange = { },
                    label = { Text("Subtraction") },
                    readOnly = true
                )
            }
            OutlinedTextField(
                value = multiplication,
                onValueChange = { },
                label = { Text("Multiplication") },
                readOnly = true
            )
        }
    }
}