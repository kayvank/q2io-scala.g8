package model

sealed trait Ex

final case class KclException(message: String)
    extends Exception(message) with Ex

final case class ParsingException(message: String)
    extends Exception(message) with Ex

final case class NonApplicationException(message: String)
    extends Exception(message) with Ex
