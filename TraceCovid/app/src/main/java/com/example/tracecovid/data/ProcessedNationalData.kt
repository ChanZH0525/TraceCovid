package com.example.tracecovid.data

data class ProcessedNationalData(
    var activeCases: Int = 0,
    var newCases: Int = 0,
    var newRecovered: Int = 0,
    var criticalCases: Int = 0,
    var totalDeath: Int = 0,
    var totalConfirmed: Int = 0,
    var totalRecovered: Int = 0,
    var lastUpdatedAtApify: String = ""
)
