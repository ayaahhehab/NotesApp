package com.example.notesapp.addNote.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notesapp.R
import com.example.notesapp.addNote.presentation.components.ConfirmationDialog
import com.example.notesapp.ui.theme.NotesAppTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddNote(
    viewModel: AddNoteViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    val title = viewModel.title.collectAsState()
    val description = viewModel.description.collectAsState()
    val showConfirmationDialog = viewModel.showConfirmationDialog.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest { event ->
            when(event){
                is AddNoteViewModel.Event.NavigateBAck -> navigateBack()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                )
                .padding(
                    start = 25.dp,
                    top = 40.dp,
                    bottom = 20.dp,
                    end = 25.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ){
//            Icon(
//                modifier = Modifier
//                    .size(20.dp)
//                    .clickable {
//                        viewModel.backIconOnClick()
//                    },
//                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
//                contentDescription = null,
//                tint = Color.White
//            )
            Text(
                modifier = Modifier
                    .clickable {
                        viewModel.backIconOnClick()
                   },
                text = "Save",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Icon(
                modifier = Modifier
                    .size(20.dp)
                        .clickable {
                            viewModel.showConfirmationDialog()
                        },
                painter = painterResource(id = R.drawable.baseline_auto_delete_24),
                contentDescription = null,
                tint = Color.White
            )
        }
        TextField(
            value = title.value,
            onValueChange = {viewModel.titleOnValueChange(it)},
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Enter The Title")
            },
            textStyle = LocalTextStyle.current.copy(
                fontSize = 20.sp
            )
        )
        TextField(
            value = description.value,
            onValueChange = {viewModel.descriptionOnValueChange(it)},
            modifier = Modifier.fillMaxSize(),
            placeholder = {
                Text(text = "Enter The Description")
            }
        )

    }
    if (showConfirmationDialog.value){
        ConfirmationDialog(
            dismissButton = {viewModel.hideConfirmationDialog()},
            confirmButton = {viewModel.deleteNote()},
            )
    }
}

@Preview
@Composable
private fun AddNotePreview() {
    NotesAppTheme {
        Surface {
            AddNote(){
                }

        }
    }
}