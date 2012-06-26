package formol

import scala.collection.immutable.HashSet
import play.api.libs.json.Json
import play.api.templates.Html
import scala.collection.mutable.StringBuilder

object jsHelper {
  
  def dependencies(field: FieldModel, expr: Expression) = {
    val builder = new DependencyBuilder(field)
    expr.evaluate(builder)
  }
  
  def jsExpression(field: FieldModel, expr: Expression) = {
    val builder = new JsExpressionBuilder(field)
    
    if (field.isInstanceOf[SelectFieldModel]) {
      field.asInstanceOf[SelectFieldModel].dynamicOptions
    }
    
    Html(expr.evaluate(builder))
  }
  
  def jsOptionsUrlExpression(field: FieldModel) = {
    val sb = new StringBuilder()
    if (field.isInstanceOf[SelectFieldModel]) {
      val dynamicOption = field.asInstanceOf[SelectFieldModel].dynamicOptions
      if (dynamicOption != null) {
        
        sb.append("var loadFn = function() {$('#" + field.name + "').load(encodeURI(jQuery.validator.format(\"")
        sb.append(dynamicOption.url)
        sb.append("\"")
        dynamicOption.args.map { arg =>
          sb.append(", ")
          sb.append(String.format("$('#%s').attr('value')", arg.fieldName))
        }
        sb.append(")))};\n")

        dynamicOption.args.map { arg =>
          sb.append("$('#" + arg.fieldName + "').change(loadFn);\n")
          sb.append("loadFn();")
        }
      }
      Html(sb.toString())
    }
    
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
