package com.synexoit.weatherapplication.remote.entity.google

import com.fasterxml.jackson.annotation.JsonProperty

class AddressComponent(@JsonProperty("long_name")
                       val longName: String,
                       @JsonProperty("short_name")
                       val shortName: String,
                       val types: List<String>)