package vn.edu.hcmus._19127514.Dictionary_java;

import java.io.*;



public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Dictionary d = Dictionary.connectData("b.dat");
        d.showDictionary();
//        Dictionary dictionary = Dictionary.importData("slang.txt");
//        dictionary.saveData("b.dat");
    }
}
