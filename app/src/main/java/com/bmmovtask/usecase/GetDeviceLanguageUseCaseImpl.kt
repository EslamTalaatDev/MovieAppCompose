package com.bmmovtask.domain.usecase

import com.bmmovtask.data.model.DeviceLanguage
import com.bmmovtask.data.repository.config.ConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetDeviceLanguageUseCaseImpl @Inject constructor(
    private val configRepository: ConfigRepository
) {
    operator fun invoke(): Flow<DeviceLanguage> {
        return configRepository.getDeviceLanguage()
    }
}