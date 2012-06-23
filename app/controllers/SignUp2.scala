package controllers

import play.api._
import play.api.mvc._

import views._

import formol.model._

object SignUp2 extends Controller {
  
  val signUpForm = form(
    text field "username"
      label "Login"
  )

  def signUp = Action {
    Ok(html.signup2.form(signUpForm));
  }
  
  def submit = Action {
    Ok("TODO")
  }
  
}