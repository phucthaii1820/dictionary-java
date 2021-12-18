package vn.edu.hcmus._19127514.Dictionary_java;

import java.io.Serializable;

/**
 * vn.edu.hcmus._19127514.Dictionary_java
 * Created by phucthaii1820 - 19127514
 * Date 16/12/2021 - 21:13
 * Description: ...
 */
public class Definition implements Serializable {
    String [] data;

    public Definition(String data) {
        this.data = new String[1];
        this.data[0] = data;
    }

    public Definition(String[] data) {
        this.data = new String[data.length];
        for (int i = 0; i < data.length; i++)
            this.data[i] = data[i];
    }

    public void overWriteDefinition(String old, String current) {
        for(int i = 0; i < this.data.length; i++) {
            if(this.data[i].equals(old))
                this.data[i] = current;
        }
    }

    public Definition() {
        this.data = null;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public void addDefinition(String v) {
        Definition temp = new Definition();
        if(this.data != null) {
            temp.data = new String[this.data.length + 1];
            for(int i = 0; i < this.data.length; i++)
                temp.data[i] = this.data[i];
            temp.data[this.data.length] = v;
        }
        else {
            temp.data = new String[1];
            temp.data[0] = v;
        }
        setData(temp.data);
    }

    public boolean isExitsInDefinition(String v) {
        if (this.data != null) {
            for(int i = 0; i < this.data.length; i++)
                if (this.data[i].toUpperCase().contains(v.toUpperCase()))
                    return true;
        }
        return false;
    }
}
