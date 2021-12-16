package vn.edu.hcmus._19127514.Dictionary_java;

public class Main {

    public static void main(String[] args) {
        Dictionary d = new Dictionary();
        d.importData("slang.txt");
        //System.out.println(d);
        Dictionary d2 = d.searchByDefinition("hello");
        d2.showDictionary();

    }
}
