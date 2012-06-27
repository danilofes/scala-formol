package formol

object validationHelper {
  def isTrue(form: FormModel, field: FieldModel, expr: BooleanExpression) = {
    val evaluator = new ValidationEvaluator(form, field)
    expr.evaluate(evaluator).asInstanceOf[Boolean]
  }
}

private class ValidationEvaluator(form: FormModel, field: FieldModel) extends Evaluator[Any] {
  def resolveField(fieldName: String) = form(fieldName).inputValue
  def resolveSelf() = field.inputValue
  def booleanLiteral(value: Boolean) = value
  def stringLiteral(value: String) = value
  def intLiteral(value: Int) = value
  def empty(value: Any) = value.asInstanceOf[String].isEmpty()
  def equals(v1: Any, v2: Any) = this.compare(v1, v2) == 0
  def not(v: Any) = !(v.asInstanceOf[Boolean])
  def length(value: Any) = value.asInstanceOf[String].length()
  def lessThan(v1: Any, v2: Any) = this.compare(v1, v2) < 0
  private def compare(v1: Any, v2: Any) = v1.asInstanceOf[Comparable[Any]].compareTo(v2)
}