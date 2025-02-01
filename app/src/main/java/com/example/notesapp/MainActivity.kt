package com.example.notesapp

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notesapp.addNote.presentation.AddNote
import com.example.notesapp.home.presentation.HomeScreen
import com.example.notesapp.ui.theme.NotesAppTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Routes.HOME,
                    ) {
                        composable(Routes.HOME) {
                            HomeScreen(
                                navigateNext = { route ->
                                    navController.navigate(route)
                                }
                            )
                        }
                        composable(
                            route= Routes.ADD_NOTE + "/{id}",
                            arguments = listOf(
                                navArgument("id"){
                                    this.type = NavType.IntType
                                    this.defaultValue = -1
                                }
                            )

                            ) {
                            AddNote(
                                navigateBack = {
                                    navController.popBackStack()
                                    Log.d(TAG, "navigateBack: $it ")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
