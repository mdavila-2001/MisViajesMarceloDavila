package com.mdavila_2001.tripadvisorclonemarcelodavila.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mdavila_2001.tripadvisorclonemarcelodavila.R
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.theme.TripAdvisorCloneMarceloDavilaTheme
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.viewmodels.LoginViewModel


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel(),
    modifier: Modifier,
) {
    var userName by rememberSaveable { mutableStateOf("") }

    val navigate by viewModel.navigateToMain.collectAsState()

    val isDarkTheme = isSystemInDarkTheme()

    LaunchedEffect(navigate) {
        if (navigate) {
            navController.navigate("trips") {
                popUpTo("login") { inclusive = true }
            }
            viewModel.onNavigationDone()
        }
    }

    Scaffold() { paddingValues ->
        LoginView(
            userName = userName,
            onUserNameChanged = { userName = it },
            onLoginClick = { viewModel.onLoginClicked(userName) },
            modifier = modifier.padding(paddingValues),
            isDarkTheme = isDarkTheme
        )
    }
}

@Composable
private fun LoginView(
    userName: String,
    onUserNameChanged: (String) -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier,
    isDarkTheme: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo de la App",
            modifier = Modifier.size(360.dp)
        )

        Text(
            text = "Ingrese su usuario:",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value= userName,
            onValueChange = onUserNameChanged,
            placeholder = { Text("Nombre de Usuario") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onLoginClick,
            enabled = userName.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Iniciar Sesión")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    TripAdvisorCloneMarceloDavilaTheme {
        LoginView(
            userName = "",
            onUserNameChanged = {},
            onLoginClick = {},
            modifier = Modifier,
            isDarkTheme = false
        )
    }
}