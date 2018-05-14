package com.synexoit.weatherapp.data.exceptions

/**
 * Created by dawidjamrozy on 14.05.2018.
 */
sealed class Failure {
    class NetworkConnection : Failure()
    class ServerError : Failure()
    class CityAlreadyInDatabaseException : Failure()
    class UnknownAppError(private val message: String? = null): Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}


