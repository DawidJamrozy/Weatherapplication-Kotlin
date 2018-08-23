package com.synexoit.weatherapplication.remote.entity.google

import com.fasterxml.jackson.annotation.JsonProperty


class Result(@JsonProperty("address_components")
             val addressComponents: List<AddressComponent>,
             @JsonProperty("formatted_address")
             val formattedAddress: String,
             @JsonProperty("geometry")
             val geometry: Geometry,
             @JsonProperty("place_id")
             val placeId: String,
             @JsonProperty("types")
             val types: List<String>)
