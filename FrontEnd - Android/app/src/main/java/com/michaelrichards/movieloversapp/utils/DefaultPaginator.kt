package com.michaelrichards.movieloversapp.utils

import com.michaelrichards.movieloversapp.dtos.UserDataDTO

class DefaultPaginator<Key, Item>(
    private val initialKey: Key,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextItem: Key) -> Result<List<Item>>,
    private inline val getNextKey: suspend (List<Item>) -> Key,
    private inline val onError: suspend (Throwable?) -> Unit,
    private inline val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit
) : Paginator<Key, Item>{

    private var currentKey: Key = initialKey
    private var isMakingRequest = false

    override suspend fun loadNextItems() {
        if (isMakingRequest){
            return
        }
        isMakingRequest = true
        onLoadUpdated(true)
        val res = onRequest(currentKey)
        isMakingRequest = false

        val items = res.getOrElse {
            onError(it)
            onLoadUpdated(false)
            return
        }

        currentKey = getNextKey(items)
        onSuccess(items, currentKey)
        onLoadUpdated(false)
    }

    override fun reset() {
       currentKey = initialKey
    }

}