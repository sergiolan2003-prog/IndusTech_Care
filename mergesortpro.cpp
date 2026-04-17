#include <iostream>
#include <vector>

using namespace std;


vector <int> mergesort (vector<int>,vector<int>);

vector <int> mergesort (vector<int> izq,vector<int> der){
    int i = 0; 
    int j = 0;

    vector <int> resultado;

    while ( i < izq.size() && j < der.size ()){

        if (izq [i] < der [j]){
            resultado.push_back(izq[i]);
            i++;
        }else resultado.push_back(der[j]);
}

        while (i < izq.size()){

            
        }
    } 


    