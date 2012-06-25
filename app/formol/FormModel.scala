package formol

import play.api.mvc.Request
import play.api.mvc.AnyContent

class FormModel(val fields: Map[String, FieldModel]) {
  def apply(name: String) = this.fields(name)
  
  def fillFromRequest(request: Request[AnyContent]) = {
    val data = request.body.asFormUrlEncoded
    data.get.map { value =>
      val field = this(value._1)
      if (field != null) {
        field.inputValue = value._2(0)
      }
    }
    this.validate()
    this
  }

  def isValid = {
    fields.values.filter(!_.valid).isEmpty
  }
  
  private def validate() {
    fields.values.map { field =>
      field.validations.map { validation =>
        val expression = validation._1
        val message = validation._2
        if (validationHelper.isTrue(this, field, expression)) {
          field.valid = false
          field.message = message
        }
      }
    }
  }
  
}

