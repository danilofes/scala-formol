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
      options Countries.list,
    
    select field "state",
    
    select field "city"
  )
  
  def signUp = Action {
    Ok(html.signup2.form(signUpForm));
  }
  
  def submit = Action {
    Ok("TODO")
  }
  
}