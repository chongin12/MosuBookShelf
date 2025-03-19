package com.example.mosubookshelf

import com.example.mosubookshelf.bookDetail.BookDetailUseCase
import com.example.mosubookshelf.bookDetail.DefaultBookDetailUseCase
import com.example.mosubookshelf.newbooks.DefaultNewBooksUseCase
import com.example.mosubookshelf.newbooks.NewBooksUseCase
import com.example.mosubookshelf.repository.BookRepository
import com.example.mosubookshelf.repository.RemoteBookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object BookModule {
    @Provides
    fun provideBookRepository(): BookRepository {
        return RemoteBookRepository()
    }

    @Provides
    fun provideBookDetailUseCase(
        repository: BookRepository
    ): BookDetailUseCase {
        return DefaultBookDetailUseCase(repository)
    }

    @Provides
    fun provideNewBooksUseCase(
        repository: BookRepository
    ): NewBooksUseCase {
        return DefaultNewBooksUseCase(repository)
    }
}