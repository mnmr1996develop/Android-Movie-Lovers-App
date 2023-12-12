package com.michaelrichards.movieloversapp.screens.SearchScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaelrichards.movieloversapp.dtos.UserProfileDTO
import com.michaelrichards.movieloversapp.model.Description
import com.michaelrichards.movieloversapp.repositories.interfaces.MovieRepository
import com.michaelrichards.movieloversapp.repositories.interfaces.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SearchViewModel"

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val userRepository: UserRepository
) : ViewModel() {


    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _users = MutableStateFlow(listOf<UserProfileDTO>())
    val users = searchText.debounce { 500L }
        .onEach { _isSearching.update { true } }
        .combine(_users) { _, users ->
            users
        }.onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _users.value
        )


    private val _movies = MutableStateFlow(listOf<Description>())

    @OptIn(FlowPreview::class)
    val movies = searchText
        .debounce(500L)
        .onEach { _isSearching.update { true } }
        .combine(_movies) { text, movies ->
            if (text.isBlank()) {
                movies
            } else {
                movies
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _movies.value
        )

    fun searchMovie(searchString: String) {
        viewModelScope.launch {
            if (searchString.isEmpty()) return@launch
            val moviesRes = movieRepository.searchMovieByName(searchString).getOrNull()
            if (moviesRes != null) {
                _movies.update { moviesRes.description }
            }
        }
    }

    fun searchUser(searchString: String) {
        viewModelScope.launch {
            if (searchString.isEmpty()) return@launch
            val userRes = userRepository.searchUser(userName = searchString).getOrNull()
            if (userRes != null) {
                _users.update { userRes }
            }
        }
    }


    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun follow(username: String) {
        viewModelScope.launch {
            userRepository.follow(username)
        }
    }

    fun unfollow(username: String) {
        viewModelScope.launch {
            userRepository.unfollow(username)
        }
    }
}

