package com.example.notesapp.addNote.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.addNote.domain.AddNoteUseCase
import com.example.notesapp.addNote.domain.DeleteNoteUseCase
import com.example.notesapp.addNote.domain.GetNoteUseCase
import com.example.notesapp.data.domain.models.NoteModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val _addNoteUseCase: AddNoteUseCase,
    private val _deleteNoteUseCase: DeleteNoteUseCase,
    private val _getNoteUseCase: GetNoteUseCase

): ViewModel() {

    private val TAG = "AddNoteViewModel"


//    private val repository: NotesRepository = NotesRepository.getInstance()

    private var _noteId: Int = -1

    private var _title: MutableStateFlow<String> = MutableStateFlow("")
    val title = _title.asStateFlow()

    private var _description: MutableStateFlow<String> = MutableStateFlow("")
    val description = _description.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    private val _showConfirmationDialog = MutableStateFlow<Boolean>(false)
    val showConfirmationDialog = _showConfirmationDialog.asStateFlow()

    val _scope = viewModelScope

    init {
        val noteId = savedStateHandle.
                get<Int>("id") ?: -1
        _noteId = noteId
        _scope.launch(Dispatchers.IO) {
            if (noteId != -1) {
                val note = _getNoteUseCase.execute(noteId)
                _title.value = note.title
                _description.value = note.description
            }
        }


    }

    fun titleOnValueChange(value: String){
        Log.d(TAG,"titleOnValueChange: title= ${title.value} ")
        _title.value = value
    }

    fun descriptionOnValueChange(value: String){
        Log.d(TAG,"titleOnValueChange: description= ${description.value} ")
        _description.value = value
    }

    fun backIconOnClick() = viewModelScope.launch(Dispatchers.IO){

        val noteModel = NoteModel(
            id = _noteId ,
            title= _title.value,
            description = _description.value
        )
        if (noteModel.title.isBlank() && noteModel.description.isBlank()) {
            // Delete the note if it is empty
            _deleteNoteUseCase.execute(noteModel.id)

            // Navigate to back
            viewModelScope.launch(Dispatchers.Main) {
                _event.emit(Event.NavigateBAck)
            }
            return@launch
        }


        // save notes
        _addNoteUseCase.execute(noteModel)

        // navigate back
        viewModelScope.launch(Dispatchers.Main) {
            _event.emit(Event.NavigateBAck)

        }
    }

    fun hideConfirmationDialog() {
        _showConfirmationDialog.value = false
    }

    fun showConfirmationDialog() {
        _showConfirmationDialog.value = true
    }

    fun deleteNote() = viewModelScope.launch(Dispatchers.IO){
        val itemId = _noteId
        _deleteNoteUseCase.execute(itemId)
//        repository.delete(itemId)
        hideConfirmationDialog()
        viewModelScope.launch(Dispatchers.Main) {
            _event.emit(Event.NavigateBAck)

        }
    }

    sealed class Event{
        data object NavigateBAck: Event()
    }
}