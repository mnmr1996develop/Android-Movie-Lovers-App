package com.michaelrichards.movieloversapp.screens.FollowingScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaelrichards.movieloversapp.dtos.UserDataDTO
import com.michaelrichards.movieloversapp.repositories.interfaces.UserRepository
import com.michaelrichards.movieloversapp.utils.DefaultPaginator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var followingList: List<UserDataDTO> = mutableListOf()
    var followersList: List<UserDataDTO> = mutableListOf()


    var followersState by mutableStateOf(ScreenState())

    
    private val followersPaginator = DefaultPaginator(
        initialKey = followersState.page,
        onLoadUpdated = {
            followersState = followersState.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            userRepository.getUserFollowers(nextPage)
        },
        getNextKey = {
            followersState.page + 1
        },
        onError = {
            followersState = followersState.copy(error = it?.localizedMessage)
        },
        onSuccess = { items, newKey ->
            followersState = followersState.copy(
                items = followersState.items + items,
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )

    var followingState by mutableStateOf(ScreenState())


    private val followingPaginator = DefaultPaginator(
        initialKey = followingState.page,
        onLoadUpdated = {
            followingState = followingState.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            userRepository.getUserFollowing(nextPage)
        },
        getNextKey = {
            followingState.page + 1
        },
        onError = {
            followingState = followingState.copy(error = it?.localizedMessage)
        },
        onSuccess = { items, newKey ->
            followingState = followingState.copy(
                items = followingState.items + items,
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )


    fun loadMoreFollowers() = viewModelScope.launch {
        followersPaginator.loadNextItems()
    }

    fun loadMoreFollowing() = viewModelScope.launch {
        followingPaginator.loadNextItems()
    }


    init {
        loadMoreFollowers()
        loadMoreFollowing()
    }

}

data class ScreenState(
    val isLoading: Boolean = false,
    val items: List<UserDataDTO> = listOf(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0
)