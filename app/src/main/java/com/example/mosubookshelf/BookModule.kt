package com.example.mosubookshelf

import android.content.Context
import com.example.mosubookshelf.repository.BookCacheRepository
import com.example.mosubookshelf.useCase.searchBooks.DefaultSearchBooksUseCase
import com.example.mosubookshelf.useCase.searchBooks.SearchBooksUseCase
import com.example.mosubookshelf.useCase.bookDetail.BookDetailUseCase
import com.example.mosubookshelf.useCase.bookDetail.DefaultBookDetailUseCase
import com.example.mosubookshelf.useCase.newBooks.DefaultNewBooksUseCase
import com.example.mosubookshelf.useCase.newBooks.NewBooksUseCase
import com.example.mosubookshelf.repository.BookRepository
import com.example.mosubookshelf.repository.local.LocalBookCacheRepository
import com.example.mosubookshelf.repository.remote.RemoteBookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object BookModule {
    @Provides
    fun provideBookRepository(): BookRepository = RemoteBookRepository()

    @Provides
    fun provideBookCacheRepository(
        @ApplicationContext context: Context,
    ): BookCacheRepository = LocalBookCacheRepository(context)

    @Provides
    fun provideBookDetailUseCase(
        repository: BookRepository,
        cache: BookCacheRepository,
    ): BookDetailUseCase {
        return DefaultBookDetailUseCase(repository, cache)
    }

    @Provides
    fun provideNewBooksUseCase(
        repository: BookRepository
    ): NewBooksUseCase {
        return DefaultNewBooksUseCase(repository)
    }

    @Provides
    fun provideSearchBooksUseCase(
        repository: BookRepository,
        cache: BookCacheRepository,
    ): SearchBooksUseCase {
        return DefaultSearchBooksUseCase(
            repository = repository,
            cacheRepository = cache,
        )
    }
}