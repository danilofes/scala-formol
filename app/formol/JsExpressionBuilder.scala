package formol

class JsExpressionBuilder extends Evaluator[String] {
  override def resolveField(fieldName: String) = "$('#%s').attr('value')".format(fieldName)
  override def booleanLiteral(value: Boolean) = value.toString()
  override def stringLiteral(value: String) = "'%s'".format(value)
  override def notEmpty(expr: String) = "!!(%s)".format(expr)
}
