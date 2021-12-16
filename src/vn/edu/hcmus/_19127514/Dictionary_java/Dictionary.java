package vn.edu.hcmus._19127514.Dictionary_java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * vn.edu.hcmus._19127514.Dictionary_java
 * Created by phucthaii1820 - 19127514
 * Date 16/12/2021 - 15:06
 * Description: ...
 */
public class Dictionary {
    Map<String, String> Slang;

    public Dictionary(Map<String, String> slang) {
        Slang = slang;
    }

    public Dictionary() {
        Slang = new HashMap<>();
    }

    public Map<String, String> getDictionary() {
        return Slang;
    }

    public void setDictionary(Map<String, String> slang) {
        Slang = slang;
    }

    public void addSlang (String k, String s) {
        Slang.put(k, s);
    }

    public void showDictionary () {
        for (Map.Entry<String, String> entry : Slang.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
    }

    public String search (String k) {
        return Slang.get(k);
    }

    public Dictionary searchByDefinition(String k) {
        Dictionary temp = new Dictionary();
        for (Map.Entry<String, String> entry : Slang.entrySet()) {
            String value = entry.getValue().toUpperCase();
            if(value.contains(k.toUpperCase()))
                temp.addSlang(entry.getKey(), entry.getValue());
        }
        return temp;
    }

    public void importData(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = br.readLine();
            while (line != null) {
                String []arrString = line.split("`");
                if(arrString.length == 2)
                    addSlang(arrString[0], arrString[1]);
                else {
                    addSlang(arrString[0], "");
                }
                line = br.readLine();
            }
            br.close();
            System.out.println("Import file success.");
        }catch (Exception e) {
            System.out.println(e);
        }
    }
}
