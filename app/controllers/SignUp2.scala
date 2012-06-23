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
      label "Confirme a senha",
      
    select field "country"
      emptyOption "--- Selecione um país ---"
      options Countries.list,
    
    select field "state"
      enabledWhen (valueOf("country") isNotEmpty),
    
    select field "city",
    
    select field "type"
      label "Tipo de cadastro"
      options List("PF", "PJ"),
      
    text field "cnpj"
      label "CNPJ"
      availableWhen (valueOf("type") isEquals "PJ")
  )
  
  def signUp = Action {
    Ok(html.signup2.form(signUpForm));
  }
  
  def submit = Action {
    Ok("TODO")
  }
  
}