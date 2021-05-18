package com.example.gerald_interview.data.models

data class ApiResponse(val adtag: String, val shows: List<Show>) {
    override fun toString(): String {
        return "RESPONSE: {" +
                "adtag: $adtag ,"+
                "shows: $shows"+
                "}"
    }
}