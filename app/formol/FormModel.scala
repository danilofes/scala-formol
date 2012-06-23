package formol

class FormModel(fields: Map[String, FieldModel]) {
  def apply(name: String) = this.fields(name)
}