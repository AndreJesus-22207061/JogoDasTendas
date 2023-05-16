import java.io.File

val dataInvalida = "Data invalida"
val menorIdade = "Menor de idade nao pode jogar"


fun criaMenu(): String {
    val menu1 = "\nBem vindo ao jogo das tendas\n\n1 - Novo jogo\n0 - Sair\n"
    return menu1
}

fun validaTamanhoMapa(numLinhas: Int, numColunas: Int): Boolean {
    if (numLinhas == 6 && numColunas == 5) {
        return true
    } else if (numLinhas == 6 && numColunas == 6) {
        return true
    } else if (numLinhas == 8 && numColunas == 8) {
        return true
    } else if (numLinhas == 8 && numColunas == 10) {
        return true
    } else if (numLinhas == 10 && numColunas == 8) {
        return true
    } else if (numLinhas == 10 && numColunas == 10) {
        return true
    }
    return false
}

fun validaDataNascimento(data: String?): String? {
    //------------------------------------------Validação dos Espaços--------------------------------------------------//

    if (data == null) {
        return dataInvalida
    }
    if (data.length != 10) {
        return dataInvalida
    }
    if (data[2] != '-') {
        return dataInvalida
    }
    if (data[5] != '-') {
        return dataInvalida
        //---------------------------------------Conversão de Strings para inteiros-----------------------------------------//
    } else {
        val diaString = data[0] + data[1].toString()
        val dia = diaString.toInt()
        val anoString = data[6] + data[7].toString() + data[8].toString() + data[9].toString()
        val ano = anoString.toInt()
        val mesString = data[3] + data[4].toString()
        val mes = mesString.toInt()
        //-------------------------------------------Validação Ano------------------------------------------------------//
        if (ano < 1990 || ano > 2022) {
            return dataInvalida
        }
        //-------------------------------------------Validação Mes------------------------------------------------------//
        if (mes > 12 || mes < 1) {
            return dataInvalida
        }
        //-------------------------------------------Validação Mes de Fevereiro-----------------------------------------//
        if (mesString == "02") {
            if ((ano % 4 == 0 && ano % 100 != 0) || ano % 400 == 0) {
                if (dia > 29) {
                    return dataInvalida
                }
            } else if (dia > 28) {
                return dataInvalida
            }
        }
        //-----------------------------Validação meses com 31 dias e meses com 30 dias----------------------------------//
        if (mesString == "04" || mesString == "06" || mesString == "09" || mesString == "11") {
            if (dia > 30) {
                return dataInvalida
            }
        } else if (dia > 31) {
            return dataInvalida
        }
        //---------------------------------Validação se é maior de idade------------------------------------------------//

        if (ano == 2004) {
            if (mesString == "11" || mesString == "12") {
                return menorIdade
            }
        }
        if (ano > 2004) {
            return menorIdade
        }
    }
    return null
}

fun criaLegendaHorizontal(numColunas: Int): String {
    var nmrColuna = numColunas
    val caracter = 'A'
    var codigoAscii = caracter.code
    var legenda = "A"
    while (nmrColuna > 1) {
        codigoAscii = (codigoAscii + 1)
        legenda = legenda + " | ${codigoAscii.toChar()}"
        nmrColuna--
    }
    return legenda
}

fun processaCoordenadas(coordenadasStr: String?, numLines: Int, numColumns: Int): Pair<Int, Int>? {
    if (coordenadasStr == null) {
        return null
    }

    if (coordenadasStr.length >= 3) {
        if (coordenadasStr[1] == ',' && coordenadasStr.length == 3) {
            val linhaString = coordenadasStr[0].toString()
            val linha = linhaString.toInt()
            val coluna = coordenadasStr[2]
            val colunaAscii = coluna.code
            val letraColuna = 'A'
            val letraAscii = letraColuna.code
            val colunas = letraAscii + (numColumns - 1)
            val coordLinhas = linha - 1
            val coordColunas = colunaAscii - letraAscii
            val pares = Pair(coordLinhas, coordColunas)


            if (linha <= numLines) {
                if (colunaAscii <= colunas && colunaAscii >= letraAscii) {
                    return pares
                } else {
                    return null
                }
            } else {
                return null
            }
        } else if (coordenadasStr[2] == ',' && coordenadasStr.length == 4) {
            val linhaString = coordenadasStr[0].toString() + coordenadasStr[1].toString()
            val linha = linhaString.toInt()
            val coluna = coordenadasStr[3]
            val colunaAscii = coluna.code
            val letraColuna = 'A'
            val letraAscii = letraColuna.code
            val colunas = letraAscii + (numColumns - 1)
            val coordLinhas = linha - 1
            val coordColunas = colunaAscii - letraAscii
            val pares = Pair(coordLinhas, coordColunas)


            if (linha <= numLines) {
                if (colunaAscii <= colunas && colunaAscii >= letraAscii) {
                    return pares
                } else {
                    return null
                }
            } else {
                return null
            }

        } else {
            return null
        }

    } else {
        return null
    }
}

fun leContadoresDoFicheiro(numLines: Int, numColumns: Int, verticais: Boolean): Array<Int?> {
    val linha: String
    val partes: Array<String?>
    val linhas = File("${numLines}x${numColumns}.txt").readLines()

    if (verticais == true) {
        val contadores: Array<Int?> = arrayOfNulls(numColumns)
        linha = linhas[0]
        partes = linha.split(",").toTypedArray()
        for (i in 0..numColumns - 1) {
            contadores[i] = partes[i]?.toIntOrNull()
            if (contadores[i] == 0) {
                contadores[i] = null
            }
        }
        return contadores

    } else {
        val contadores: Array<Int?> = arrayOfNulls(numLines)
        linha = linhas[1]
        partes = linha.split(",").toTypedArray()
        for (i in 0..numLines - 1) {
            contadores[i] = partes[i]?.toIntOrNull()
            if (contadores[i] == 0) {
                contadores[i] = null
            }
        }
        return contadores
    }
}

fun criaLegendaContadoresHorizontal(contadoresVerticais: Array<Int?>): String {

    val contador = contadoresVerticais
    var legenda: String = ""
    if (contador[0] != null && contador[0] != 0) {
        legenda = "$legenda" + "${contador[0]} "
    } else {
        legenda = "$legenda  "
    }

    for (i in 1 until contador.size - 1) {
        if (contador[i] != 0 && contador[i] != null) {
            legenda = "$legenda " + " ${contador[i]} "
        } else {
            legenda = "$legenda    "
        }
    }
    if (contador[contador.size - 1] != null && contador[contador.size - 1] != 0) {
        legenda = "$legenda  " + "${contador[contador.size - 1]}"
    }
    return legenda
}

fun leTerrenoDoFicheiro(numLines: Int, numColumns: Int): Array<Array<String?>> {
    val posicaoArvore = File("${numLines}x${numColumns}.txt").readLines()
    val terreno = Array(numLines) { Array<String?>(numColumns) { null } }
    var numero = 2
    var linha: Int?
    var coluna: Int?
    var partes: Array<String?>

    do {
        partes = posicaoArvore[numero].split(",").toTypedArray()
        linha = partes[0]?.toIntOrNull()
        coluna = partes[1]?.toIntOrNull()
        for (i in 0..numLines - 1) {
            for (j in 0..numColumns - 1) {
                if (linha == i && coluna == j) {
                    terreno[i][j] = "A"
                }
            }
        }
        numero++
    } while (numero < posicaoArvore.size)
    return terreno
}

fun criaTerreno(
    terreno: Array<Array<String?>>, contadoresVerticais: Array<Int?>?, contadoresHorizontais: Array<Int?>?,
    mostraLegendaHorizontal: Boolean = true, mostraLegendaVertical: Boolean = true
): String {
    var tabuleiro: String = ""
    var legendaContadoresHorizontal: String = ""
    val numColunas = terreno[0].size
    val numLinhas = terreno.size
    val legendaHorizontal = criaLegendaHorizontal(terreno[0].size)
    if (contadoresVerticais != null) {
        legendaContadoresHorizontal = criaLegendaContadoresHorizontal(contadoresVerticais)
    }
    if (contadoresVerticais != null) {
        tabuleiro = tabuleiro + "       $legendaContadoresHorizontal"
    }
    if (mostraLegendaHorizontal) {
        if (contadoresVerticais != null) {
            tabuleiro = tabuleiro + "\n"
        }
        tabuleiro = tabuleiro + "     | $legendaHorizontal"
    }
    for (i in 0 until numLinhas) {
        if (tabuleiro != "") {
            tabuleiro = tabuleiro + "\n"
        }
        if (contadoresHorizontais != null) {
            if (contadoresHorizontais[i] == 0 || contadoresHorizontais[i] == null) {
                tabuleiro = tabuleiro + "  "
            } else {
                tabuleiro = tabuleiro + contadoresHorizontais[i] + " "
            }
        } else {
            tabuleiro = tabuleiro + "  "
        }
        if (mostraLegendaVertical) {
            tabuleiro = tabuleiro + " ${i + 1} "
        } else {
            tabuleiro = tabuleiro + "   "
        }
        for (j in 0 until numColunas) {
            if (j == numColunas - 1) {
                if (terreno[i][j] == "A") {
                    tabuleiro = tabuleiro + "| \u25B3"
                } else if (terreno[i][j] == "T") {
                    tabuleiro = tabuleiro + "| T"
                } else {
                    tabuleiro = tabuleiro + "|  "
                }
            } else if (terreno[i][j] == "A") {
                tabuleiro = tabuleiro + "| \u25B3 "
            } else if (terreno[i][j] == "T") {
                tabuleiro = tabuleiro + "| T "
            } else {
                tabuleiro = tabuleiro + "|   "
            }
        }
    }
    return tabuleiro
}
fun temArvoreAdjacente(terreno: Array<Array<String?>>, coords: Pair<Int, Int>): Boolean {
    val linha = coords.first
    val coluna = coords.second
    if (linha != terreno.size - 1) {
        if (terreno[linha + 1][coluna] == "A") {
            return true
        }
    } else if (linha != 0) {
        if (terreno[linha - 1][coluna] == "A") {
            return true
        }
    } else if (coluna != terreno[0].size - 1) {
        if (terreno[linha][coluna + 1] == "A") {
            return true
        }
    } else if (coluna != 0) {
        if (terreno[linha][coluna - 1] == "A") {
            return true
        }
    }
    return false
}

fun temTendaAdjacente(terreno: Array<Array<String?>>, coords: Pair<Int, Int>): Boolean {
    val linha = coords.first
    val coluna = coords.second
    if (linha != 0) {
        if (terreno[linha - 1][coluna] == "T") {
            return true
        }
        if (coluna != 0) {
            if (terreno[linha - 1][coluna - 1] == "T") {
                return true
            } else if (terreno[linha][coluna - 1] == "T") {
                return true
            } else if (terreno[linha][coluna - 1] == "T") {
                return true
            }
        }
        if (coluna != terreno[0].size - 1) {
            if (terreno[linha - 1][coluna + 1] == "T") {
                return true
            } else if (terreno[linha][coluna + 1] == "T") {
                return true
            } else if (terreno[linha][coluna + 1] == "T") {
                return true
            }
        }
    } else if (linha != terreno.size - 1) {
        if (terreno[linha + 1][coluna] == "T") {
            return true
        }
        if (coluna != 0) {
            if (terreno[linha + 1][coluna - 1] == "T") {
                return true
            } else if (terreno[linha][coluna - 1] == "T") {
                return true
            } else if (terreno[linha][coluna - 1] == "T") {
                return true
            }
        }
        if (coluna != terreno[0].size - 1) {
            if (terreno[linha + 1][coluna + 1] == "T") {
                return true
            } else if (terreno[linha][coluna + 1] == "T") {
                return true
            } else if (terreno[linha][coluna + 1] == "T") {
                return true
            }
        }
    } else {
        if (coluna != 0) {
            if (terreno[linha][coluna - 1] == "T") {
                return true
            }
        }
        if (coluna != terreno[0].size - 1) {
            if (terreno[linha][coluna + 1] == "T") {
                return true
            }
        }
    }
    return false
}

fun contaTendasColuna(terreno: Array<Array<String?>>, coluna: Int): Int {
    var numeroTendas = 0

    if (coluna >= 0 && coluna <= terreno[0].size - 1) {
        for (i in 0..terreno.size - 1) {
            if (terreno[i][coluna] == "T") {
                numeroTendas++
            }
        }
        return numeroTendas
    } else {
        return numeroTendas
    }
}

fun contaTendasLinha(terreno: Array<Array<String?>>, linha: Int): Int {
    var numeroTendas = 0

    if (linha >= 0 && linha <= terreno.size - 1) {
        for (j in 0..terreno[0].size - 1) {
            if (terreno[linha][j] == "T") {
                numeroTendas++
            }
        }
        return numeroTendas
    } else {
        return numeroTendas
    }
}

fun colocaTenda(terreno: Array<Array<String?>>, coords: Pair<Int, Int>): Boolean {
    val coordsLinha = coords.first
    val coordsColuna = coords.second
    if (terreno[coordsLinha][coordsColuna] == null){
        if(temArvoreAdjacente(terreno,coords)){
            if (temTendaAdjacente(terreno,coords)){
                return false
            }else{
                terreno[coordsLinha][coordsColuna] = "T"
                return true
            }
        }else{
            return false
        }
    }else if (terreno[coordsLinha][coordsColuna] == "T"){
        terreno[coordsLinha][coordsColuna] = null
        return true
    }else{
        return false
    }
}

fun terminouJogo(
    terreno: Array<Array<String?>>,
    contadoresVerticais: Array<Int?>,
    contadoresHorizontais: Array<Int?>
): Boolean {
    var numArvores = 0
    var numTendas = 0
    val numTendasLinha = Array<Int> (terreno.size) {0}
    val numTendasColuna = Array<Int> (terreno[0].size) {0}
    for (i in 0 .. terreno.size -1){
        for (j in 0 .. terreno[0].size -1){
            if (terreno[i][j] == "A"){
                numArvores++
            }
            if (terreno[i][j] == "T"){
                numTendas++
                numTendasLinha[i]++
                numTendasColuna[j]++
            }
        }
    }
    for (i in 0 .. terreno.size -1) {
        for (j in 0..terreno[0].size - 1) {
            if (contadoresHorizontais[i] == null){
                contadoresHorizontais[i] = 0
            }
            if (contadoresVerticais[j] == null){
                contadoresVerticais[j] = 0
            }
        }
    }


    if (numArvores == numTendas ){
        if (numTendasLinha.contentEquals(contadoresHorizontais)){
            if (numTendasColuna.contentEquals(contadoresVerticais)){
                return true
            }
        }
    }
    return false
}

fun main() {
    var colunas: Int? = 0
    var linhas: Int? = 0
    var terreno : Array<Array<String?>>
    var contadoresVert :Array<Int?>
    var contadoresHorz :Array<Int?>
    var coordenadasPair : Pair<Int,Int>?


    do {
        var terrenoInvalido = 0
        var menorDeIdade = 0
        var coordenasInvalidas = 0
        println(criaMenu())
        val resposta = readLine()?.toIntOrNull()
        if (resposta == null || resposta != 1 && resposta != 0) {
            println("Opcao invalida")
        } else if (resposta == 0) {
            println("")
        } else if (resposta == 1) {
            do {
                println("Quantas linhas?")
                linhas = readLine()?.toIntOrNull()

                if (linhas == null || linhas <= 0) {
                    println("Resposta invalida")
                }

            } while (linhas == null || linhas <= 0)

            do {
                println("Quantas colunas?")
                colunas = readLine()?.toIntOrNull()

                if (colunas == null || colunas <= 0) {
                    println("Resposta invalida")
                }

            } while (colunas == null || colunas <= 0)

            validaTamanhoMapa(linhas, colunas)
            if (validaTamanhoMapa(linhas, colunas) != true) {
                terrenoInvalido = 1
                println("Terreno invalido")
            }
            if (linhas == 10 && colunas == 10) {
                do {
                    println("Qual a sua data de nascimento? (dd-mm-yyyy)")
                    val data = readLine()
                    validaDataNascimento(data)
                    if (validaDataNascimento(data) == "Menor de idade nao pode jogar") {
                        println(validaDataNascimento(data))
                        menorDeIdade = 1
                    } else if (validaDataNascimento(data) == dataInvalida) {
                        println(validaDataNascimento(data))
                    } else {
                        validaDataNascimento(data)
                    }
                } while (validaDataNascimento(data) == dataInvalida)
            }
            if (terrenoInvalido == 0 && menorDeIdade == 0) {
                terreno = leTerrenoDoFicheiro(linhas,colunas)
                contadoresVert = leContadoresDoFicheiro(linhas,colunas,true)
                contadoresHorz = leContadoresDoFicheiro(linhas,colunas,false)
                println(criaTerreno(terreno,contadoresVert,contadoresHorz))
                do {
                    println("Coordenadas da tenda? (ex: 1,B)")
                    val coordenadas = readLine()
                    if(coordenadas != "sair"){
                        coordenadasPair = processaCoordenadas(coordenadas,linhas,colunas)
                        if (coordenadasPair == null) {
                            println("Coordenadas invalidas")
                        } else{
                            coordenasInvalidas = 1
                            colocaTenda(terreno,coordenadasPair)
                            if (colocaTenda(terreno,coordenadasPair) == false){
                                println("Tenda nao pode ser colocada nestas coordenadas")
                            }
                        }
                    }
                } while (processaCoordenadas(coordenadas, linhas, colunas) == null || coordenadas != "sair")
            }
        }
    } while (resposta != 1 && resposta != 0 || terrenoInvalido == 1 || menorDeIdade == 1 || coordenasInvalidas == 1)
}
