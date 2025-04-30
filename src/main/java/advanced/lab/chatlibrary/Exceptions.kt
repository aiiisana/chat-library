package advanced.lab.chatlibrary

open class ApiException(message: String) : Exception(message)
class UnauthorizedException : ApiException("Unauthorized")
class NotFoundException : ApiException("Not Found")
class ServerException : ApiException("Internal Server Error")