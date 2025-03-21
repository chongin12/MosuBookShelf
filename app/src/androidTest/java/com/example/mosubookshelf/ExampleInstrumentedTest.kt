package com.example.mosubookshelf

import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mosubookshelf.models.BookDetailDTO
import com.example.mosubookshelf.repository.local.LocalBookRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runTest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.mosubookshelf", appContext.packageName)
    }
}

class LocalBookRepositoryTest {
    val localBookRepository = LocalBookRepository(InstrumentationRegistry.getInstrumentation().targetContext)

    @Before
    fun insertDBData() {
        val sample = BookDetailDTO.sample1
        localBookRepository.insertBook(sample)
    }

    @Test
    fun getDBData() {
        val sample1_isbn = BookDetailDTO.sample1.isbn13!!

        runTest {
            val bookDetail = localBookRepository.getBookDetail(isbn13 = sample1_isbn)
            println("bookDetail : $bookDetail")
            assert(bookDetail.isSuccess)
            bookDetail.onSuccess {
                assert(it.isbn13 == sample1_isbn)
            }.onFailure {
                assert(false)
            }
        }
    }
}