package controllers

import play.api._
import play.api.mvc._
import views._
import formol.model._
import models.Countries

object SignUp2 extends Controller {
  
  val signUpForm = form(
    text field "username"
      label "Login",
      
    text field "password_main"
      label "Senha",

    text field "password_confirm"
      label "Confirme a senha"
      invalidWhen (self isNotEquals valueOf("password_main")) -> "Confirmação não é igual a senha.",
      
    select field "country"
      label "País"
      options Countries.list
      emptyOption "--- Selecione um país ---",
    
    select field "state"
      label "Estado / Província"
      enabledWhen (valueOf("country") isNotEmpty),
    
    select field "city"
      label "Cidade",
    
    select field "type"
      label "Tipo de cadastro"
      options List("Pessoa Física", "Pessoa Jurídica"),
      
    text field "cnpj"
      label "CNPJ"
      availableWhen (valueOf("type") isEquals "Pessoa Jurídica")
  )
  
  def signUp = Action {
    Ok(html.signup2.form(signUpForm));
  }
  
  def submit = Action {
    Ok("TODO")
  }
  
}