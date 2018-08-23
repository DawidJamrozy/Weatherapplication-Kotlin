package com.synexoit.weatherapplication.remote.entity.google

import com.fasterxml.jackson.annotation.JsonProperty

class Geometry(val bounds: Bounds,
               val location: Location,
               @JsonProperty("location_type")
               val locationType: String,
               val viewport: Viewport)