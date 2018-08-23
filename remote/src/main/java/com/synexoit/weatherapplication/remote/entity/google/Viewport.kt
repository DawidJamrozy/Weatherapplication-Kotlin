package com.synexoit.weatherapplication.remote.entity.google

import com.fasterxml.jackson.annotation.JsonProperty

class Viewport(@JsonProperty("northeast")
               val northeast: Northeast,
               @JsonProperty("southwest")
               val southwest: Southwest)