package com.yawtseb.bestway.model

import com.google.gson.annotations.SerializedName

data class TicketVo(
        @SerializedName("ticket_5000")
        var ticket5000: String,

        @SerializedName("ticket_4000")
        var ticket4000: String,

        @SerializedName("ticket_3500")
        var ticket3500: String,

        @SerializedName("ticket_2000")
        var ticket2000: String,
)