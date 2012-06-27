package controllers

import play.api._
import play.api.mvc._
import views._
import formol.model._
import models.Countries

object SignUp2 extends Controller {
  
  def signUpForm = form(
    text field "username"
      label "Login",
      
    password field "password_main"
      label "Senha"
      invalidWhen (value.length isLessThan 6) -> "Senha deve ter mínimo de 6 caracteres",

    password field "password_confirm"
      label "Confirme a senha"
      invalidWhen (value isNotEquals valueOf("password_main")) -> "Confirmação não é igual a senha",
      
    select field "country"
      label "País"
      options Countries.list
      emptyOption "--- Selecione o país ---",
    
    select field "state"
      label "Estado / Província"
      enabledWhen (valueOf("country") isNotEmpty)
      options (routes.SignUp.listStates, valueOf("country")),
    
    select field "city"
      label "Cidade"
      enabledWhen (valueOf("state") isNotEmpty)
      options (routes.SignUp.listCities, valueOf("state")),
    
    select field "type"
      label "Tipo de cadastro"
      options List("Pessoa Física", "Pessoa Jurídica"),
      
    text field "cnpj"
      label "CNPJ"
      availableWhen (valueOf("type") isEquals "Pessoa Jurídica"),

    text field "cpf"
      label "CPF"
      availableWhen (valueOf("type") isEquals "Pessoa Física")
  )
  
  def signUp = Action {
    Ok(html.signup2.form(signUpForm))
  }
  
  def submit = Action { request =>
    val submitedForm = signUpForm.fillFromRequest(request)
    if (submitedForm.isValid) {
      Ok(html.signup2.success())
    } else {
      Ok(html.signup2.form(submitedForm))
    }
  }
  
}