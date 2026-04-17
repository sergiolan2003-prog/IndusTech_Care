#include <iostream>
#include <vector>

using namespace std;

int main() {
    vector<int> vec;

    // Usar push_back() para añadir elementos
    vec.push_back(10);
    vec.push_back(20);
    vec.push_back(30);

    // Mostrar elementos después de añadir
    cout << "Elementos después de push_back(): ";
    for (int num : vec) {
        cout << num << " ";
    }
    cout << endl;

    return 0;
}
