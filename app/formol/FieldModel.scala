package formol

import play.api.mvc.Call

abstract class FieldModel(val name: String) {
  var label: String = name
  def label(label: String): this.type = {
    this.label = label
    this
  }
  
  var enablingCondition: BooleanExpression = null
  def enabledWhen(condition: BooleanExpression): this.type = {
    this.enablingCondition = condition
    this
  }

  var availabilityCondition: BooleanExpression = null
  def availableWhen(condition: BooleanExpression): this.type = {
    this.availabilityCondition = condition
    this
  }

  var validations: List[Tuple2[BooleanExpression, String]] = List()
  def invalidWhen(validation: Tuple2[BooleanExpression, String]): this.type = {
    validations = validation :: validations
    this
  }

  var inputValue: String = ""
  var message: String = ""
  var valid: Boolean = true
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
  
  var dynamicOptions: OptionsLoader = null
  def options(routeFn: String => Call, arg: FieldValue): this.type = {
    val url = routeFn("_0_").toString().replaceFirst("_0_", "{0}")
    this.dynamicOptions = OptionsLoader(url, arg)
    this
  }
}

case class OptionsLoader(url: String, args: FieldValue*)
