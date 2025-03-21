package com.example.mosubookshelf

import com.example.mosubookshelf.useCase.DefaultSearchBooksUseCase
import com.example.mosubookshelf.useCase.SearchBooksUseCase
import com.example.mosubookshelf.useCase.BookDetailUseCase
import com.example.mosubookshelf.useCase.DefaultBookDetailUseCase
import com.example.mosubookshelf.useCase.DefaultNewBooksUseCase
import com.example.mosubookshelf.useCase.NewBooksUseCase
import com.example.mosubookshelf.repository.BookRepository
import com.example.mosubookshelf.repository.remote.RemoteBookRepository
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

    @Provides
    fun provideSearchBooksUseCase(
        repository: BookRepository
    ): SearchBooksUseCase {
        return DefaultSearchBooksUseCase(repository = repository)
    }
}