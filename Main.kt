package tictactoe

fun isNumeric(toCheck: String): Boolean {
    return toCheck.all { char -> char.isDigit() }
}
fun printMap(positionList: MutableList<MutableList<Char>>)  {
    println("---------")
    println("| ${positionList[0].slice(0..2).joinToString(" ")} |")
    println("| ${positionList[1].slice(0..2).joinToString(" ")} |")
    println("| ${positionList[2].slice(0..2).joinToString(" ")} |")
    println("---------")
}

fun result(positionList: MutableList<MutableList<Char>>):String {
    val stringArray = mutableListOf<String>()

    for(list in positionList) {
        for(elem in list) {
            stringArray.add(elem.toString())
        }
    }

    val frequencies = stringArray.groupingBy { it }.eachCount()

    val frequenciesX = frequencies["X"] ?: 0
    val frequenciesO = frequencies["O"] ?: 0
    var result = "begin"
    var xWin = "begin"
    var oWin = "begin"

    if ("_" !in stringArray) {
        result = "Draw"
    }

    for(i in 1..7 step 3) {
        if(stringArray[i] == "X" && stringArray[i - 1] == "X" && stringArray[i + 1] == "X") {
            result = "X wins"
            xWin = "X wins"
        }
        if(stringArray[i] == "O" && stringArray[i - 1] == "O" && stringArray[i + 1] == "O") {
            result = "O wins"
            oWin = "O wins"
        }
    }
    for(i in 3..5) {
        if(stringArray[i] == "X" && stringArray[i - 3] == "X" && stringArray[i + 3] == "X") {
            result = "X wins"
            xWin = "X wins"
        }
        if(stringArray[i] == "O" && stringArray[i - 3] == "O" && stringArray[i + 3] == "O") {
            result = "O wins"
            oWin = "O wins"
        }
    }
    if(stringArray[0] == "X" && stringArray[4] == "X" && stringArray[8] == "X" ||
        stringArray[2] == "X" && stringArray[4] == "X" && stringArray[6] == "X" ) {
        result = "X wins"
        xWin = "X wins"
        return(result)
    }
    if(stringArray[0] == "O" && stringArray[4] == "O" && stringArray[8] == "O" ||
        stringArray[2] == "0" && stringArray[4] == "O" && stringArray[6] == "O" ) {
        result = "O wins"
        oWin = "O wins"
        return(result)
    }
    if ("_" in stringArray && oWin != "O wins" && xWin != "X wins") {
        result = "Game not finished"
    }
    val diff = frequenciesX - frequenciesO
    val positiveNumber = if(diff < 0) diff * -1 else diff

    if ((positiveNumber >= 2) ||
        (xWin == "X wins" && oWin == "O wins")) {
        result = "Impossible"
    }
    return result
}

fun isValidMap(positionList: MutableList<MutableList<Char>>, ligne: String, colonne: String):Boolean {
    if (!isNumeric(ligne) || !isNumeric(colonne)) {
        println("You should enter numbers!")
        return false
    }
    var x = ligne.toInt()
    var y = colonne.toInt()

    if (x !in 1..3 || y !in 1..3) {
        println("Coordinates should be from 1 to 3!")
        return false
    }

    x -= 1
    y -= 1

    if (positionList[x][y] != '_') {
        println("This cell is occupied! Choose another one!")
        return false
    }
    return true
}

fun main() {
    val str = "_________"
    val c = str.toCharArray()
    val player1 = 'X'; val player2 = 'O';
    var indexPLayer = 0

    val positionList = mutableListOf(
        mutableListOf(c[0], c[1], c[2]),
        mutableListOf(c[3], c[4], c[5]),
        mutableListOf(c[6], c[7], c[8])
    )
    while(true) {
        while (true) {
            val inputs = readln()
            val (ligne, colonne) = inputs.split(" ").map { it.toString() }
            if (isValidMap(positionList, ligne, colonne)) {
                val x = ligne.toInt() - 1
                val y = colonne.toInt() - 1

                positionList[x][y] =  if(indexPLayer % 2 == 0) player1 else player2
                indexPLayer++
                printMap(positionList)
                break // Terminer après un coup valide
            }
        }
        if(result(positionList) == "X wins") {
            println(result(positionList))
            break
        } else if(result(positionList) == "O wins") {
            println(result(positionList))
            break
        } else if(result(positionList) == "Impossible") {
            println(result(positionList))
            break
        } else if(result(positionList) == "Draw") {
            println(result(positionList))
            break
        }
    }
}