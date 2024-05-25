package com.bmmovtask.data.model

import com.bmmovtask.utils.formatted
import java.util.Date

data class DateParam(private val date: Date) {
    override fun toString(): String {
        return date.formatted("yyyy-MM-dd")
    }
}
