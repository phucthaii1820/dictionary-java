package vn.edu.hcmus._19127514.Dictionary_java;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * vn.edu.hcmus._19127514.Dictionary_java
 * Created by phucthaii1820 - 19127514
 * Date 16/12/2021 - 15:06
 * Description: ...
 */
public class Dictionary implements Serializable {
    Map<String, Definition> Slang;
    List<String> History;

    public Dictionary(Map<String, Definition> slang, List<String> history) {
        Slang = slang;
        History = history;
    }

    public Dictionary() {
        Slang = new HashMap<>();
        History = new ArrayList<>();
    }

    public Map<String, Definition> getSlang() {
        return Slang;
    }

    public List<String> getHistory() {
        return History;
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

    public void overWrite(String k, String old, String current) {
        Slang.get(k).overWriteDefinition(old, current);
    }

    public void editSlang(String kOld, String kCurrent, String[] value) {
        try {
            Slang.remove(kOld);
            addSlang(kCurrent, value);
        }catch (Exception ex) {
            throw new NullPointerException(ex.getMessage());
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
        this.History.add(k);
        String[] temp = null;
        try {
            temp = Slang.get(k).getData();
        }
        catch (Exception ex) {
            throw new NullPointerException(ex.getMessage());
        }
        return temp;
    }

    public Dictionary searchByDefinition(String k) {
        this.History.add(k);
        Dictionary temp = null;
        try {
            temp = new Dictionary();
            for (Map.Entry<String, Definition> entry : Slang.entrySet()) {
                if(entry.getValue().isExitsInDefinition(k))
                    temp.addSlang(entry.getKey(), entry.getValue().getData());
            }
        }
        catch (Exception ex) {
            throw new NullPointerException(ex.getMessage());
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
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("b.dat"));
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addHistory(String a) {
        this.History.add(a);
    }
}
