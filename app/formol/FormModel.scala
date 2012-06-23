package formol

class FormModel(val fields: Map[String, FieldModel]) {
  def apply(name: String) = this.fields(name)
}