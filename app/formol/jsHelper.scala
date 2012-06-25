package formol

import scala.collection.immutable.HashSet

import play.api.libs.json.Json
import play.api.templates.Html

object jsHelper {
  
  def dependencies(field: FieldModel, expr: Expression) = {
    val builder = new DependencyBuilder(field)
    expr.evaluate(builder)
  }
  
  def jsExpression(field: FieldModel, expr: Expression) = {
    val builder = new JsExpressionBuilder(field)
    Html(expr.evaluate(builder))
  }
  
}

private class JsExpressionBuilder(field: FieldModel) extends Evaluator[String] {
  override def resolveField(fieldName: String) = "$('#%s').attr('value')".format(fieldName)
  override def resolveSelf() = "$('#%s').attr('value')".format(field.name)
  override def booleanLiteral(value: Boolean) = Json.toJson(value).toString()
  override def stringLiteral(value: String) = Json.toJson(value).toString()
  override def intLiteral(value: Int) = Json.toJson(value).toString()
  override def notEmpty(expr: String) = "!!(%s)".format(expr)
  override def equals(expr1: String, expr2: String) = "%s == %s".format(expr1, expr2)
  override def notEquals(expr1: String, expr2: String) = "%s != %s".format(expr1, expr2)
  override def length(expr: String) = "String(%s).length".format(expr)
  override def lessThan(expr1: String, expr2: String) = "%s < %s".format(expr1, expr2)
}

private class DependencyBuilder(field: FieldModel) extends Evaluator[Set[String]] {
  override def resolveField(fieldName: String) = HashSet(fieldName)
  override def resolveSelf() = HashSet(field.name)
  override def booleanLiteral(value: Boolean) = Set.empty
  override def stringLiteral(value: String) = Set.empty
  override def intLiteral(value: Int) = Set.empty
  override def notEmpty(e: Set[String]) = e
  override def equals(e1: Set[String], e2: Set[String]) = e1 ++ e2
  override def notEquals(e1: Set[String], e2: Set[String]) = e1 ++ e2
  override def length(e: Set[String]) = e
  override def lessThan(e1: Set[String], e2: Set[String]) = e1 ++ e2
}
