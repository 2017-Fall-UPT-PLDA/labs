package plda.json

/**
  * These objects are here so that users of the [[plda.json]] package
  * can easily import convenient definitions while making it look
  * as if they are importing from a package
  *
  * These objects could have also gone into the package object [[plda.json]],
  * from a client perspective it would have been the same, but there
  * are semantic differences between the approaches.
  */
object syntax extends JsonSyntax

object encoders extends DefaultJsonEncoders
