package com.example.tracecovid.global

import android.app.Application
import com.example.tracecovid.data.ProcessedNationalData

public class GlobalClass: Application() {
    companion object {
        var response = ArrayList<ProcessedNationalData>()
    }
}