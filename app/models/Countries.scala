package models

object Countries {
  
  val list = List(
    "Argentina",
    "Brasil",
    "Paraguai"
  )
  
}

object States {
  
  def list(country: String) = country match {
    case "Brasil" => List(
      "MG",
      "RJ",
      "RS",
      "SP"
    )
    case _ => List()
  }
  
}