package formol

import scala.collection.immutable.Set
import scala.collection.immutable.HashSet

trait Expression {
  def evaluate[R](evaluator: Evaluator[R]): R
}

trait Evaluator[R] {
  def resolveField(fieldName: String): R
  def resolveSelf(): R
  def booleanLiteral(value: Boolean): R
  def stringLiteral(value: String): R
  def intLiteral(value: Int): R
  def notEmpty(value: R): R
  def equals(v1: R, v2: R): R
  def notEquals(v1: R, v2: R): R
  def length(value: R): R
  def lessThan(v1: R, v2: R): R
}

trait ComparableExpression extends Expression {
  def isEquals(other: ComparableExpression) = new Equals(this, other)
  def isNotEquals(other: ComparableExpression) = new NotEquals(this, other)
  def isLessThan(other: ComparableExpression) = new LessThan(this, other)
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
  override def evaluate[R](evaluator: Evaluator[R]) = {
    val reduced = operand.evaluate(evaluator)
    evaluator.notEmpty(reduced)
  }
}

class Equals(operand1: ComparableExpression, operand2: ComparableExpression) extends BooleanExpression {
  override def evaluate[R](evaluator: Evaluator[R]) = {
    val reduced1 = operand1.evaluate(evaluator)
    val reduced2 = operand2.evaluate(evaluator)
    evaluator.equals(reduced1, reduced2)
  }
}

class NotEquals(operand1: ComparableExpression, operand2: ComparableExpression) extends BooleanExpression {
  override def evaluate[R](evaluator: Evaluator[R]) = {
    val reduced1 = operand1.evaluate(evaluator)
    val reduced2 = operand2.evaluate(evaluator)
    evaluator.notEquals(reduced1, reduced2)
  }
}

class LessThan(operand1: ComparableExpression, operand2: ComparableExpression) extends BooleanExpression {
  override def evaluate[R](evaluator: Evaluator[R]) = {
    val reduced1 = operand1.evaluate(evaluator)
    val reduced2 = operand2.evaluate(evaluator)
    evaluator.lessThan(reduced1, reduced2)
  }
}

abstract class StringExpression extends ComparableExpression {
  def isNotEmpty = new NotEmpty(this)
  def length = new StringLength(this)
}

class FieldValue(val fieldName: String) extends StringExpression {
  override def evaluate[R](evaluator: Evaluator[R]) = {
    evaluator.resolveField(fieldName)
  }
}

object SelfValue extends StringExpression {
  override def evaluate[R](evaluator: Evaluator[R]) = {
    evaluator.resolveSelf()
  }
}

class StringLiteral(value: String) extends StringExpression {
  override def evaluate[R](evaluator: Evaluator[R]) = {
    evaluator.stringLiteral(value)
  }
}

abstract class IntExpression extends ComparableExpression

class StringLength(expr: StringExpression) extends IntExpression {
  override def evaluate[R](evaluator: Evaluator[R]) = {
    val reduced = expr.evaluate(evaluator)
    evaluator.length(reduced)
  }
}

class IntLiteral(value: Int) extends IntExpression {
  override def evaluate[R](evaluator: Evaluator[R]) = {
    evaluator.intLiteral(value)
  }
}
