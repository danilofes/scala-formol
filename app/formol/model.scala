package formol

object model {
  def form(fields: FieldModel*) = {
    val entries = fields.map ((field) => Tuple2(field.name, field))
    new FormModel(Map(entries: _*))
  }
  object text {
    def field(name: String) = TextFieldModel(name)
  }
}