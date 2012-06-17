package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import views._

import models._

object SignUp extends Controller {
  
  /**
   * Sign Up Form definition.
   *
   * Once defined it handle automatically, ,
   * validation, submission, errors, redisplaying, ...
   */
  val signupForm: Form[User] = Form(
    
    // Define a mapping that will handle User values
    mapping(
      "username" -> text(minLength = 6),
      
      "password" -> tuple(
        "main" -> text(minLength = 6),
        "confirm" -> text
      ).verifying(
        "As senhas não batem", passwords => passwords._1 == passwords._2
      ),
      
      "country" -> nonEmptyText,
      "state" -> nonEmptyText,
      "city" -> nonEmptyText,
      
      "accept" -> checked("You must accept the conditions")
      
    )
    // A assinatura do formulário não é identica ao da classe User, portanto é necessário custom binding/unbinding
    {
      // Mapeamento formulário -> User
      (username, passwords, country, state, city, _) => User(username, passwords._1, country, state, city) 
    }
    {
      // Mapeamento User -> formulário
      user => Some(user.username, (user.password, ""), user.country, user.state, user.city, false)
    }.verifying(
      // Add an additional constraint: The username must not be taken (you could do an SQL request here)
      "This username is not available",
      user => !Seq("admin", "guest").contains(user.username)
    )
  )
  
  /**
   * Display an empty form.
   */
  def form = Action {
    Ok(html.signup.form(signupForm));
  }
  
  /**
   * Handle form submission.
   */
  def submit = Action { implicit request =>
    signupForm.bindFromRequest.fold(
      // Form has errors, redisplay it
      errors => BadRequest(html.signup.form(errors)),
      
      // We got a valid User value, display the summary
      user => Ok(html.signup.summary(user))
    )
  }
  
}