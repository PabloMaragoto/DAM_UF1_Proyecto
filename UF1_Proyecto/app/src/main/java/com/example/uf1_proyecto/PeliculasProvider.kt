package com.example.uf1_proyecto

class PeliculasProvider {
    companion object {
        val peliculasLista = listOf<Pelicula>(
            Pelicula(
                titulo = "Aftersun",
                director = "Charlotte Wells",
                anho = "2022",
                pais = "Reino Unido",
                sinopsis = "Una emotiva historia sobre los recuerdos de una relación entre padre e hija."
            ),
            Pelicula(
                titulo = "Suspiria",
                director = "Dario Argento",
                anho = "1977",
                pais = "Italia",
                sinopsis = "Una bailarina descubre oscuros secretos en una academia de danza de renombre."
            ),
            Pelicula(
                titulo = "La La Land",
                director = "Damien Chazelle",
                anho = "2016",
                pais = "Estados Unidos",
                sinopsis = "Un romance musical que explora los sueños y la realidad en Los Ángeles."
            ),
            Pelicula(
                titulo = "Parasite",
                director = "Bong Joon-ho",
                anho = "2019",
                pais = "Corea del Sur",
                sinopsis = "Una crítica social ingeniosa y oscura sobre dos familias de mundos diferentes."
            ),
            Pelicula(
                titulo = "The Shining",
                director = "Stanley Kubrick",
                anho = "1980",
                pais = "Estados Unidos",
                sinopsis = "Un escritor pierde el control en un hotel aislado mientras lucha contra sus propios demonios."
            ),
            Pelicula(
                titulo = "Inception",
                director = "Christopher Nolan",
                anho = "2010",
                pais = "Estados Unidos",
                sinopsis = "Un ladrón especializado en robar secretos a través de los sueños enfrenta un último desafío."
            ),
            Pelicula(
                titulo = "Interstellar",
                director = "Christopher Nolan",
                anho = "2014",
                pais = "Estados Unidos",
                sinopsis = "Una épica aventura espacial para salvar a la humanidad de la extinción."
            ),
            Pelicula(
                titulo = "The Godfather",
                director = "Francis Ford Coppola",
                anho = "1972",
                pais = "Estados Unidos",
                sinopsis = "La saga de la familia Corleone y su lucha por el poder en el mundo de la mafia."
            )
        )
    }
}