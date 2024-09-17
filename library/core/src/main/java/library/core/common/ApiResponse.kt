package library.core.common

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right

typealias ApiResponse <T> = Either<Throwable, T>

fun <T> toLeft(error: Throwable): ApiResponse<T> = Left(error)

fun <T> toRight(t: T): ApiResponse<T> = Right(t)
