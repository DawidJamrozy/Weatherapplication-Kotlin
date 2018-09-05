package com.synexoit.weatherapplication.utils

import java.util.concurrent.ThreadLocalRandom

class DataFactory {
    companion object Factory {

        fun randomInt() = ThreadLocalRandom.current().nextInt(0, 1000 + 1)

        fun randomDouble() = randomInt().toDouble()

        fun randomLong() = randomInt().toLong()

        fun randomUuid() = java.util.UUID.randomUUID().toString()

    }
}