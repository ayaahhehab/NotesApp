package com.example.notesapp.home.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.Routes
import com.example.notesapp.home.domain.GetNotesUseCase
import com.example.notesapp.home.domain.ListenNotesUseCase
import com.example.notesapp.home.domain.NotesEvent
import com.example.notesapp.data.domain.models.NoteModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val listenNotesUseCase: ListenNotesUseCase

): ViewModel() {
    private val TAG = "HomeViewModel"


    val notesList = mutableStateListOf<NoteModel>()
    private val _eventFlow = MutableSharedFlow<HomeEvent>()
    val eventFlow: SharedFlow<HomeEvent> = _eventFlow.asSharedFlow()
    val _scope = viewModelScope //launch background tasks (fetching data).

    // Fetches and listens for changes in notes when the ViewModel is created.
    init {
        _scope.launch (Dispatchers.IO){
            val items = getNotesUseCase.execute()
            delay(500L)
            notesList.addAll(items)
        }
        _scope.launch (Dispatchers.IO){
            listenNotesUseCase.execute().collect{ event ->
                when(event){
                    is NotesEvent.Delete -> {
                        val itemIndex = notesList.indexOfFirst { it.id == event.value }
                        if (itemIndex != -1) {
                            notesList.removeAt(itemIndex)
                        }
                    }

                    is NotesEvent.Insert -> notesList.add(0, event.value)

                    is NotesEvent.Update -> {
                        val itemIndex = notesList.indexOfFirst { it.id == event.value.id }

                        if (itemIndex != -1) {
                            notesList[itemIndex] = event.value
                        }
                    }
                }
            }
        }
    }

    //when clicking on any note it navigate to its data
    fun listItemOnClick(id:Int) = _scope.launch(Dispatchers.Main){
        Log.d(TAG,"listItemOnClick: $id")
        val rout = Routes.ADD_NOTE + "/$id" // Create the route to navigate
        _eventFlow.emit(HomeEvent.NavigateNext(rout))
    }

    fun addNewNote(){
        Log.d(TAG,"addNewNote: ")

    }

//    fun saveNote(value: NoteModel){
//        Log.d(TAG, "saveNote: $value")
//
//        val isNoteEmpty = value.let{
//            it.title.isEmpty() && it.description.isEmpty()
//        }
//
//        if (isNoteEmpty) return
//        if (value.id == -1) {
//            val newId = repository.insert(value)
//            val newValue = value.copy(
//                    id = newId
//            )
//            notesList.add(newValue)
//        }else{
//            repository.update(value)
//            val itemIndex = notesList.indexOfLast { it.id  == value.id }
//            notesList[itemIndex] = value
//        }
//    }
    sealed class HomeEvent{
        data class NavigateNext (val route: String): HomeEvent()
    }
}