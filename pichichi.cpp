#include <iostream>  
#include <vector>   
#include <fstream>   
#include <sstream>   
using namespace std; 


int contarFila(const vector<vector<int>>& sudoku, int fila, int valorcito) {
    int conteo = 0;  
    for (int col = 0; col < 9; col++) {  // Iteramos sobre todas las columnas de la fila
        if (sudoku[fila][col] == valorcito) 
        conteo++;  // se incrementa solo si el valor es igual al valor buscado y pues conteo ++ :v
    }
    return conteo;  // Devolvemos el total de que encontramos en la fila
}


int contarColumna(const vector<vector<int>>& sudoku, int col, int valorcito) {
    int conteo = 0; 
    for (int fila = 0; fila < 9; fila++) {  // lo mismo de arriba pero alrevez XD
        if (sudoku[fila][col] == valorcito) 
        conteo++;  // lo mismo de arriba PERO EN COLUMNA AAAAAA
    }
    return conteo;  //lo mismo que arriba pero ya en columna
}

// verificar si en la sduhmatriz aprece mas de un numero en las submatrizes
bool verificarSubmatriz(const vector<vector<int>>& sudoku, int fila, int col, int valorcito) {
    for (int i = fila; i < fila + 3; i++) {  // Recorremos las 3 filas de la submatriz
        for (int j = col; j < col + 3; j++) {  // Recorremos las 3 columnas de la submatriz si le quitamos el + no recorrera la submatrices completas
            if (sudoku[i][j] == valorcito) 
            
            return true;  
            // Si encontramos el valor retornamos  porque se repite en la submatriz
        }
    }
    return false;  // Si no encontramos el valor restorna a false CUIDADO NO CONFUNDIR CON EL DE ARRIBA >:(
}


bool esValido(const vector<vector<int>>& sudoku) {
    for (int fila = 0; fila < 9; fila++) {   // filas porque  9? porque estamos iterando sobre una matriz 9x9
        for (int col = 0; col < 9; col++) { // columnanas y como es 9x9 ojo
            int valorcito = sudoku[fila][col];  // porque valor = a sudoku [fila][col] estamos obteniendo el valor de la celda actual
            if (valorcito == 0) continue;  // lo salta el for no quiere a los 0 los odia los detesta :(
            // Si el valor es 0  se ignora pa que evaluar una celda vacia?????



            //aca simplemente se verifica que no  se repitan numeros en las submatrices
            if (contarFila(sudoku, fila, valorcito) > 1 ||  //cuenta cuántas veces aparece el valor en la fila osea en el sudoku oe
                                                         //Si el valor aparece más de una vez en esa fila, significa que se repite y pues ya no es valido

                contarColumna(sudoku, col, valorcito) > 1 ||  // lo mismo aca si el valor es 1 pues eso nos quiere decir que se repite por lo tanto
                                                          // no es valido ya que se repite lo mismo de arriba

                verificarSubmatriz(sudoku, (fila / 3) * 3, (col / 3) * 3, valorcito)) {// recorre las 3 filas y 3 columnas de la submatriz 3x3 
                    //al verificar estamos usando fuerza bruta  y como sabemos que usamos fuerza bruta? 
                    // Simplemente se recorre todas las celdas posibles ya sea fila columna o submatriz 
                    // eso es fuerza bruta como decia en la presnetacion implica probar cada opcion disponible


                    // fila /3 cuántas submatrices 3x3 están por encima de la fila 
                    // col / 3 cuántas submatrices 3x3 están a la izquierda de la columna 
                // aver lee esto si te enredas ya sabes que sos muy nervioso y pues solo haslo si la cagas no hay que pensar negativo juanzi:)
                    // esta vaina e seria ome

                return false;  
                //si se repite retorna false ya que eso hace que el sudoku no sea valido
            }
        }
    }
    return true; 
    //si pasa todo pues es valido :)
}

// matriz :v lee el sudoku 
vector<vector<int>> leerSudoku(const string& archivo) { 
    ifstream entrada(archivo);  
    vector<vector<int>> sudoku(9, vector<int>(9));  // Creamos una matriz de 9x9 para que te preguntaras pequeño pues pa almacenar daaaa
    for (int i = 0; i < 9; i++) {  // Iteramos sobre cada fila lo mismo aca 
        for (int j = 0; j < 9; j++) {  // Iteramos sobre cada columna y pues tenemos que iterar sobre 9 el ejercicio decia 9 yo pongo nueve jeje
            entrada >> sudoku[i][j];   // ya que si son 9  pues el sudoku debe contener  9 filas y nueve columnas algo normal de un sudoku
        }
    }
    return sudoku;  // Devolvemos la matriz cargada ya que ya la almacenamos en el sudoku [i] [j] ve por eso el return
}

//2 cosas aca ya estan los 2  sudokus el 1 y el 2 que son matrizes tambien esta el const string que es pa que el valor no cambie en la ejecucion
//dato curiosisimo ya que estamos guardando los resultados pues debemos volver a leer los sudokus si señor me encanta el for como todos los dias for
void guardarResultados(const string& archivoSalida,  vector<vector<int>>& sudoku1, vector<vector<int>>& sudoku2) {
    ofstream salida(archivoSalida);  
    salida << "nombresito:juan simon patino\n";  

    salida << "sudokito 1:\n";  
    for (int i = 0; i < 9; i++) {   // filas 1 sodukito
        for (int j = 0; j < 9; j++) {  // columnas 1 sudoku
            salida << sudoku1[i][j] << "\t";  // se escribe cada numero de la fila 
        }
        salida << "\n";  
    }
    salida << "fue valido o no?" << esValido(sudoku1) << "\n\n";  // Escribimos si el Sudoku 1 es válido o no

    salida << "sudokito 2:\n"; 
    for (int i = 0; i < 9; i++) {  // lo mismo de arriba pero en el sudoku 2
        for (int j = 0; j < 9; j++) {  // porque 9? porque estamos recorriendo  sobre cada columna de la fila ya que po
            salida << sudoku2[i][j] << "\t";  //lo mismo de arriba pero en el sudoku 2
        }
        salida << "\n"; 
        
    }
    salida << "fue valido o no? " << esValido(sudoku2) << "\n"; //simple ve que  si el sudoku 2 es valido y bueno como sabemos esto pues 
                                                                // si es valido returna true y sino false 
    salida << "metodos que use fue fuerza bruta que bendicion \n";   // esta arriba yo te muestro ahora si estoy listo 
     // el verdadero ganador de mil batallas >:)
}

int main() {
    vector<vector<int>> sudoku1 = leerSudoku("sudoku_input.txt");  // leido
    vector<vector<int>> sudoku2 = leerSudoku("sudoku_input2.txt");  // mas leido
    guardarResultados("juansimonpatino.txt", sudoku1, sudoku2);  // doble leido o bueno ya se llama la funcion donde ya guardamos todo
                                                                // y salio pa pintura
    return 0;
}
