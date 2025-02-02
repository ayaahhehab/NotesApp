package com.example.notesapp.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notesapp.R
import com.example.notesapp.Routes
import com.example.notesapp.ui.theme.NotesAppTheme
import kotlinx.coroutines.flow.collectLatest
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateNext:(String) -> Unit,
) {
    val notes = viewModel.notesList
//    LaunchedEffect(key1 = newNote) {
//        if(newNote.isNullOrEmpty()) return@LaunchedEffect
//        val newNoteObj = Gson().fromJson(newNote,NoteModel::class.java)
//        viewModel.saveNote(newNoteObj)
//    }
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is HomeViewModel.HomeEvent.NavigateNext -> navigateNext(event.route)
            }
        }
    }
    Scaffold(
        topBar = {
            Row(
                modifier= Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    .padding(
                        start = 25.dp,
                        top = 40.dp,
                        bottom = 20.dp,
                    )
            ) {
                Text(
                    text = "Notes App",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
//                    viewModel.addNewNote()
                    val route = Routes.ADD_NOTE + "/-1"
                    navigateNext(route)
                },
                containerColor = MaterialTheme.colorScheme.tertiary
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(
                    color = Color.LightGray
                ),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(vertical = 14.dp)
        ) {

            items(notes.size) { index ->
                val note = notes[index]

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            viewModel.listItemOnClick(note.id)
                        }
                        .padding(horizontal = 16.dp, vertical = 14.dp,)

                ) {
                    Text(
                        text = note.title,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = note.description,
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun HomeScreenPreview() {
    NotesAppTheme {
        Surface(
            color = Color.Gray,
//            modifier = Modifier.fillMaxSize()
            ){
            HomeScreen(){

            }
        }
    }
}