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
  def equals(v1: R, v2: R): R
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

class Equals(operand1: StringExpression, operand2: StringExpression) extends BooleanExpression {
  override def dependencies = operand1.dependencies ++ operand2.dependencies
  override def evaluate[R](evaluator: Evaluator[R]) = {
    val reduced1 = operand1.evaluate(evaluator)
    val reduced2 = operand2.evaluate(evaluator)
    evaluator.equals(reduced1, reduced2)
  }
}

abstract class StringExpression extends Expression {
  def isNotEmpty = new NotEmpty(this)
  def isEquals(other: StringExpression) = new Equals(this, other)
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
