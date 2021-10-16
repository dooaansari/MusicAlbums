package com.app.musicalbums.base

import com.app.musicalbums.helpers.Store
import javax.inject.Inject

abstract class BaseRepository {
    @Inject lateinit var store: Store

}