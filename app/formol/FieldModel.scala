package formol

abstract class FieldModel(val name: String) {
  var label: String = name
  def label(label: String): this.type = {
    this.label = label
    this
  }
  
  var enablingCondition: BooleanExpression = Boolean.True 
  def enabledWhen(condition: BooleanExpression): this.type = {
    this.enablingCondition = condition
    this
  }

  var availabilityCondition: BooleanExpression = Boolean.True
  def availableWhen(condition: BooleanExpression): this.type = {
    this.availabilityCondition = condition
    this
  }

  var validations: List[Tuple2[BooleanExpression, String]] = List()
  def invalidWhen(validation: Tuple2[BooleanExpression, String]): this.type = {
    validations = validation :: validations
    this
  }

}

case class TextFieldModel(override val name: String) extends FieldModel(name)

case class PasswordFieldModel(override val name: String) extends FieldModel(name)

case class SelectFieldModel(override val name: String) extends FieldModel(name) {
  var options: Seq[String] = Seq()
  def options(options: Seq[String]): this.type = {
    this.options = options
    this
  }

  var emptyOption: String = ""
  def emptyOption(text: String): this.type = {
    this.emptyOption = text
    this
  }
}
