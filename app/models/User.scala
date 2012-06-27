package models

case class User(
  username: String, 
  password: String,
  country: Option[String],
  state: Option[String],
  city: Option[String],
  accountType: Option[String],
  cnpj: Option[String],
  cpf: Option[String]
)
