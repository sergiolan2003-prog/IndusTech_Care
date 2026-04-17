#include <iostream>
#include <vector>


using namespace std;

vector <int> mergesort (vector<int>,vector<int>);   //esto lo arregla en orden
vector <int> merge (int inicio,int final,vector<int>arr){ //esto lo divide :)
 
        if (inicio == final){
            vector <int> datobase;
            datobase.push_back (arr[final]);
            return datobase;

        }


        int mitad = (inicio + final) /2;
        vector <int> izq = merge (inicio,mitad,arr);
        vector <int> der = merge (mitad + 1, final,arr);
        return mergesort (izq,der);
}