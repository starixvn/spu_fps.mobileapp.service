package fps.mobile.app

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import fps.mobile.app.ui.screen.dataPackage.DataPackageScreen
import fps.mobile.app.ui.screen.deviceInfo.DeviceInfoScreen
import fps.mobile.app.ui.screen.fingerprint.FingerprintDetailScreen
import fps.mobile.app.ui.screen.fingerprint.fingerprintDetailArgument
import fps.mobile.app.ui.screen.main.MainScreen
import fps.mobile.app.ui.screen.start.StartScreen
import fps.mobile.app.ui.viewModel.LoginViewModel

@Composable
fun FPSDemoApp(
    navController: NavHostController = rememberNavController(),
    loginViewModel: LoginViewModel = viewModel()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = FPSDemoScreen.entries.find { it.route == backStackEntry?.destination?.route} ?: FPSDemoScreen.Start

    Scaffold(
        topBar = {
            AppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = FPSDemoScreen.Start.route,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .imePadding()
        ) {
            composable(route = FPSDemoScreen.Start.route) {
                StartScreen(
                    loginViewModel = loginViewModel,
                    onLoginButtonClicked = {
                        navController.navigate(FPSDemoScreen.DeviceInfo.route)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp))
            }

            composable(route = FPSDemoScreen.DeviceInfo.route) {
                DeviceInfoScreen(
                    loginViewModel = loginViewModel,
                    onNextButtonClicked = { navController.navigate(FPSDemoScreen.Main.route) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                )
            }

            composable(route = FPSDemoScreen.Main.route) {
                MainScreen(
                    onButtonClicked = {
                        navController.navigate(FPSDemoScreen.DataPackage.route)
                    },
                    onFingerprintClick =  { fingerprint ->
                        navController.navigate(FPSDemoScreen.FingerprintDetail.route.replace("{fingerprint}", fingerprint))
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }

            composable(
                route = FPSDemoScreen.FingerprintDetail.route,
                arguments = fingerprintDetailArgument
            ) {
                FingerprintDetailScreen(
                    fingerprint = backStackEntry?.arguments?.getString("fingerprint"),
                    modifier = Modifier.fillMaxHeight(),
                )
            }

            composable(route = FPSDemoScreen.DataPackage.route) {
                DataPackageScreen(
                    loginViewModel = loginViewModel,
                    onBackButtonClick = { navController.popBackStack(FPSDemoScreen.Start.route, false,
                        saveState = false
                    ) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    currentScreen: FPSDemoScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                currentScreen.title,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        }
    )
}