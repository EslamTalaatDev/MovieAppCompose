package com.bmmovtask.data.model.movie

import com.bmmovtask.data.model.Part

data class MovieCollection(
    val name: String,
    val parts: List<Part>
)
