package vn.edu.hcmus._19127514.Dictionary_java;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * vn.edu.hcmus._19127514.Dictionary_java
 * Created by phucthaii1820 - 19127514
 * Date 16/12/2021 - 15:06
 * Description: ...
 */
public class Dictionary implements Serializable {
    Map<String, Definition> Slang;

    public Dictionary(Map<String, Definition> slang) {
        Slang = slang;
    }

    public Dictionary() {
        Slang = new HashMap<>();
    }

    public void addSlang (String k, String[] s) {
        if(Slang.containsKey(k)){
            for(int i = 0; i < s.length; i++)
                Slang.get(k).addDefinition(s[i]);
        }
        else {
            Slang.put(k, new Definition(s));
        }
    }

    public void addSlang (String k, String s) {
        if (Slang.containsKey(k)) {
            Slang.get(k).addDefinition(s);
        } else {
            Slang.put(k, new Definition(s));
        }
    }

    public void showDictionary () {
        for (Map.Entry<String, Definition> entry : Slang.entrySet()) {
            for(int i = 0; i < entry.getValue().getData().length; i++) {
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue().getData()[i]);
            }
        }
    }

    public String[] search (String k) {
        return Slang.get(k).getData();
    }

    public Dictionary searchByDefinition(String k) {
        Dictionary temp = new Dictionary();
        for (Map.Entry<String, Definition> entry : Slang.entrySet()) {
            if(entry.getValue().isExitsInDefinition(k))
                temp.addSlang(entry.getKey(), entry.getValue().getData());
        }
        return temp;
    }

    public static Dictionary importData(String path) {
        Dictionary data = null;
        try {
            data = new Dictionary();
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = br.readLine();
            while (line != null) {
                String []arrString = line.split("`");
                if(arrString.length == 2)
                    data.addSlang(arrString[0], arrString[1]);
                else {
                    data.addSlang(arrString[0], "");
                }
                line = br.readLine();
            }
            br.close();
            System.out.println("Import file success.");
        }catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    public static Dictionary connectData(String path) {
        Dictionary data = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("b.dat"));
            data = (Dictionary) objectInputStream.readObject();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return data;
    }

    public void saveData(String path) throws IOException {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(path));
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
