package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import views._

import models._

object SignUp2 extends Controller {
  
  def form = Action {
    Ok(html.signup2.form());
  }
  
  def submit = Action {
    Ok("TODO")
  }
  
}