package com.example.tracecovid.data

data class ProcessedNationalData(
//    Initialize value to 0 to ensure the object is not null
    var activeCases: Int = 0,
    var newCases: Int = 0,
    var newRecovered: Int = 0,
    var criticalCases: Int = 0,
    var totalDeath: Int = 0,
    var totalConfirmed: Int = 0,
    var totalRecovered: Int = 0,
    var lastUpdatedAtApify: String = ""
)
