#include <jni.h>
#include <string>
#include <string.h>
#include <cstdlib>
#include <stdlib.h>
#include <fstream>
#include <iostream>
#include <time.h>
#include <unistd.h>

using namespace std;

class RC4 {
public:
    RC4(char* key);
    void make();
    void init();
    char output();
    double worktime;
    float fileLen;

private:
    unsigned char S[256];
    unsigned int i, j;
    char* key;
    clock_t t1, t2;


};

extern "C"
JNIEXPORT jdouble

JNICALL
Java_com_example_alina_crypto_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */,
        jstring key) {
    RC4 rc4 = RC4((char*)(*env).GetStringUTFChars(key, NULL));
    rc4.make();
    cout<<"c++";
    double time = rc4.worktime;
    return time;

}


void RC4::make() {

    init();
    init();
    char c;
    t1 = clock();


//    ifstream fin("src/assets/main/textcppp.txt",ios::binary);
//    ofstream fout("/data/user/0/com.example.alina.crypto/files/ciphertext2.txt",ios::binary);
//
//    fin.seekg(0, ios::end);
//    int len = fin.tellg();
//    fin.seekg(0, ios::beg);
//
//
//    for (int k = 0; k < 48; k++){
//        fin.read(&c, sizeof(c));
//        char outb = c ^ output();
//        fout.write(&outb, sizeof(outb));
//    }
//    fin.close();
//    fout.close();

    init();

    ifstream fout2("/data/user/0/com.example.alina.crypto/files/ciphertext.txt", ios::binary);
    ofstream fout3("/data/user/0/com.example.alina.crypto/files/decoded2.txt", ios::binary);
    fout2.seekg(0, ios::end);
    fileLen = fout2.tellg();
    fout2.seekg(0, ios::beg);

    for (int k = 0; k < fileLen; k++){
        fout2.read(&c, sizeof(c));
        char outb = c ^ output();
        fout3.write(&outb, sizeof(outb));
    }
    fout2.close();
    fout3.close();

    t2 = clock();

    //worktime = len2;
    worktime = (((float)(t2-t1))/CLOCKS_PER_SEC)*1000;


}

void RC4::init(){
    unsigned char temp;

    for (i = 0; i != 256; ++i)
        S[i] = i;

    for (i = j = 0; i != 256; ++i)
    {
        j = (j + key[i % strlen(key)] + S[i]) % 256;
        temp = S[i];
        S[i] = S[j];
        S[j] = temp;
    }
    i = j = 0;
}
RC4::RC4(char* key)
{
    this->key = key;
}
char RC4::output() {
    unsigned char temp;

    i = (i + 1) % 256;
    j = (j + S[i]) % 256;

    temp = S[j];
    S[j] = S[i];
    S[i] = temp;

    return S[(temp + S[j]) % 256];
}




