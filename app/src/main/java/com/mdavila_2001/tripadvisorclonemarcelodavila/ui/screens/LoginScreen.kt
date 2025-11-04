package com.mdavila_2001.tripadvisorclonemarcelodavila.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mdavila_2001.tripadvisorclonemarcelodavila.ui.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel(),
    modifier: Modifier,
) {}

@Composable
private fun LoginView(
    userName: String,
    onUserNameChanged: (String) -> Unit,
    onLoginClick: () -> Unit,
) {}