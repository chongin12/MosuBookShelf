package com.example.mosubookshelf

import androidx.test.core.app.ApplicationProvider
import com.example.mosubookshelf.models.BookDetailDTO
import com.example.mosubookshelf.repository.local.LocalBookRepository
import com.example.mosubookshelf.repository.remote.RemoteBookRepository
import kotlinx.coroutines.*
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}
