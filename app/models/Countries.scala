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
      "Minas Gerais",
      "Rio de Janeiro",
      "São Paulo"
    )
    case "Estados Unidos" => List(
      "Califórnia",
      "Nova Iorque"
    )
    case _ => List.empty
  }
  
}

object Cities {
  
  def list(state: String) = state match {
    case "Minas Gerais" => List("Belo Horizonte", "Ipatinga", "Uberaba")
    case "Rio de Janeiro" => List("Rio de Janeiro", "Petrópolis")
    case "São Paulo" => List("São Caetano", "São Paulo")
    case "Califórnia" => List("Los Angeles", "São Francisco")
    case "Nova Iorque" => List("Nova Iorque")
    case _ => List.empty
  }
  
}