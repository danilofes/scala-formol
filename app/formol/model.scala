package formol

object model {
  def form(fields: FieldModel*) = {
    val entries = fields.map ((field) => Tuple2(field.name, field))
    new FormModel(Map(entries: _*))
  }
  object text {
    def field(name: String) = TextFieldModel(name)
  }
  object password {
    def field(name: String) = PasswordFieldModel(name)
  }
  object select {
    def field(name: String) = SelectFieldModel(name)
  }
  def valueOf(fieldName: String) = new FieldValue(fieldName)
  val value = SelfValue
  implicit def stringLiteral(value: String) = new StringLiteral(value)
  implicit def intLiteral(value: Int) = new IntLiteral(value)
}
