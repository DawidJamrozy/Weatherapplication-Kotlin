package com.synexoit.weatherapplication.data.exceptions

/**
 * Created by dawidjamrozy on 14.05.2018.
 */
sealed class Failure : Throwable() {
    class NetworkConnection : Failure()
    class ServerError : Failure()
    class CityAlreadyInDatabaseException() : Failure()
    class NoLocationAvailable : Failure()
    class UnknownAppError(override val message: String? = null) : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}


