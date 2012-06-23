package formol

abstract class FieldModel(val name: String) {
  var label: String = name
  def label(label: String): this.type = {
    this.label = label
    this
  }
} 

case class TextFieldModel(override val name: String) extends FieldModel(name) {
  
}

case class SelectFieldModel(override val name: String) extends FieldModel(name) {
  var options: Seq[String] = Seq()
  def options(options: Seq[String]): this.type = {
    this.options = options
    this
  }
}
