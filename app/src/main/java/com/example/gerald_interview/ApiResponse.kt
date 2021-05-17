package com.example.gerald_interview

data class ApiResponse(val adtag: String, val shows: List<Show>) {
    override fun toString(): String {
        return "RESPONSE: {" +
                "adtag: $adtag ,"+
                "shows: $shows"+
                "}"
    }

    fun groupShows(): Map<String, List<Show>>{
        val groupedShows = mutableMapOf<String, MutableList<Show>>()
        for(i in shows){
            if(groupedShows.containsKey(i.category))
                groupedShows[i.category]?.add(i)
            else
                groupedShows[i.category] = mutableListOf(i)
        }
//        groupedShows[shows[0].category] = shows as MutableList<Show>
        return groupedShows
    }
}