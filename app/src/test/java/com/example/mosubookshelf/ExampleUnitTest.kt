package com.example.mosubookshelf

import com.example.mosubookshelf.repository.RemoteBookRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

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

class BooksTest {
    @Test
    fun getNewBooks(): Unit = runBlocking {
        launch {
            val res = RemoteBookRepository().getNewBooks()
            assert(res.size > 0)
        }
    }
}