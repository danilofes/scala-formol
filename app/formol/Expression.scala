package formol

import scala.collection.immutable.Set
import scala.collection.immutable.HashSet

abstract class Expression {
  def dependencies: Set[String] = HashSet()
  def evaluate[R](evaluator: Evaluator[R]): R
}

trait Evaluator[R] {
  def resolveField(fieldName: String): R
  def booleanLiteral(value: Boolean): R
  def stringLiteral(value: String): R
  def notEmpty(value: R): R
}

abstract class BooleanExpression extends Expression

object Boolean {
  object True extends BooleanExpression {
    override def evaluate[R](evaluator: Evaluator[R]) = {
      evaluator.booleanLiteral(true)
    }
  }
}

class NotEmpty(operand: StringExpression) extends BooleanExpression {
  override def dependencies = operand.dependencies
  override def evaluate[R](evaluator: Evaluator[R]) = {
    val reduced = operand.evaluate(evaluator)
        evaluator.notEmpty(reduced)
  }
}

abstract class StringExpression extends Expression {
  def isNotEmpty = new NotEmpty(this)
}

class FieldValue(fieldName: String) extends StringExpression {
  override def dependencies = HashSet(fieldName)
  override def evaluate[R](evaluator: Evaluator[R]) = {
    evaluator.resolveField(fieldName)
  }
}

class StringLiteral(value: String) extends StringExpression {
  override def evaluate[R](evaluator: Evaluator[R]) = {
    evaluator.stringLiteral(value)
  }
}
