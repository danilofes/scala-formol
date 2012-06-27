package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._

import views._

import models._

object SignUp extends Controller {
  
  /**
   * Definição do formulário.
   */
  val signupForm: Form[User] = Form(
    mapping(
      "username" -> text.verifying("Login deve ser preenchido", login => !login.isEmpty()),
      
      "password" -> tuple(
        "main" -> text.verifying("Senha deve ter mínimo de 6 caracteres", password => password.length >= 6),
        "confirm" -> text
      ).verifying("Confirmação não é igual a senha", passwords => passwords._1 == passwords._2),
      
      "country" -> optional(text),
      "state" -> optional(text),
      "city" -> optional(text),
      
      "type" -> optional(text),
      "cnpj" -> optional(text),
      "cpf" -> optional(text)
    )
    {
      (username, passwords, country, state, city, accountType, cnpj, cpf) => User(username, passwords._1, country, state, city, accountType, cnpj, cpf)
    }
    {
      user => Some(user.username, (user.password, ""), user.country, user.state, user.city, user.accountType, user.cnpj, user.cpf)
    }
  )
  
  /**
   * Exibe o formulário vazio.
   */
  def signUp = Action {
    Ok(html.signup.form(signupForm));
  }
  
  /**
   * Submissão do formulário.
   */
  def submit = Action { implicit request =>
    signupForm.bindFromRequest.fold(
      errors => BadRequest(html.signup.form(errors)),
      user => Ok(html.signup.success())
    )
  }
  
  /**
   * Lista os estados dado o país.
   */
  def listStates(country: String) = Action {
    Ok(html.signup.options(States.list(country), "--- Selecione o estado ---"))
  }

  /**
   * Lista as cidades dado o estado.
   */
  def listCities(state: String) = Action {
    Ok(html.signup.options(Cities.list(state), "--- Selecione a cidade ---"))
  }
}