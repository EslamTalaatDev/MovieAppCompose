package com.bmmovtask.di

import com.bmmovtask.utils.TextRecognitionHelper
import com.bmmovtask.utils.TextRecognitionHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface HelperBinds {
    @Binds
    fun provideTextRecognitionHelper(impl: TextRecognitionHelperImpl): TextRecognitionHelper
}