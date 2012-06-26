package models

object Countries {
  
  val list = List(
    "Brasil",
    "Estados Unidos"
  )
  
}

object States {
  
  def list(country: String) = country match {
    case "Brasil" => List(
      "Espírito Santo",
      "Minas Gerais",
      "Rio de Janeiro",
      "São Paulo"
    )
    case "Estados Unidos" => List(
      "California",
      "Nova Iorque"
    )
  }
  
}