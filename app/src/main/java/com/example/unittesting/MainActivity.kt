package com.example.unittesting

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Testing()
        }
    }
}

@Composable
fun Testing() {

    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        val context = LocalContext.current

        var firstName by  remember { mutableStateOf ("")}
        var lastName by remember { mutableStateOf("") }
        var isPasswordValid by remember { mutableStateOf(false) }
        var password by remember { mutableStateOf("")}

        Text(text = "$firstName $lastName", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(value = firstName, onValueChange = { firstName = it}, modifier = Modifier.testTag("firstName_tag"), label = {
            Text(text = "First Name")
        } )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(value = lastName, onValueChange = { lastName = it } ,modifier = Modifier.testTag("lastName_tag"), label = {
            Text(text = "Last Name")
        })

        Spacer(modifier = Modifier.height(10.dp))

        PasswordTextField(
            password = password,
            onPasswordChange = { password = it },
            onPasswordValidated = { isValid ->
            isPasswordValid = isValid
        })

        Spacer(modifier = Modifier.height(25.dp))

        Button(onClick = {
            Toast.makeText(context, "$firstName $lastName",Toast.LENGTH_SHORT).show()
            validatePassword(password)
        }) {
            Text(text = "Click",fontSize = 20.sp)
        }
    }

}

@Composable
fun PasswordTextField(
    password: String,
    onPasswordValidated: (Boolean) -> Unit,
    onPasswordChange: (String) -> Unit
) {

    val passwordError by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier.wrapContentSize(),
        value = password,
        onValueChange = { value ->
            onPasswordChange(value)
            validatePassword(value)
        },
        label = { Text("Password") },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        visualTransformation = PasswordVisualTransformation(),
    )

    if (passwordError.isNotEmpty()) {
        Text(
            text = passwordError,
            color = Color.Red,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp, top = 4.dp)
        )
    }
}

private fun validatePassword(passwordText: String): Boolean {
    return if (passwordText.isEmpty()) {
        false
    } else {
        PasswordValidator.isValidPassword(passwordText)
    }
}

object PasswordValidator {
    fun isValidPassword(password:String): Boolean {
        val regexPattern = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+\$).{8,}\$")
        val noInitialSpaces = !password.startsWith(" ")
        val isEmpty = password.isNotEmpty()
        return regexPattern.matches(password) && noInitialSpaces && isEmpty
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Testing()
}