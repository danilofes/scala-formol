package formol

import play.api.templates.Html

object viewHelper {

  def renderField(field: FieldModel) = field match {
    case field: TextFieldModel => formol.html.textField(field)
    case field: SelectFieldModel => formol.html.selectField(field)
    case _ => Html("<div>TODO</div>")
  }

}