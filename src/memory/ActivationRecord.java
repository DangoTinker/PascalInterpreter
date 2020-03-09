package memory;

import symbols.Symbol;

import java.util.HashMap;

public class ActivationRecord {

    private String name;
    private String type;
    private int nesting_level;

    private HashMap<String, Double> members;


    public ActivationRecord(String name, String type, int nesting_level) {
        this.name = name;
        this.type = type;
        this.nesting_level = nesting_level;
        members=new HashMap<String, Double>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNesting_level() {
        return nesting_level;
    }

    public void setNesting_level(int nesting_level) {
        this.nesting_level = nesting_level;
    }

    public HashMap<String, Double> getMembers() {
        return members;
    }

    public void setMembers(HashMap<String, Double> members) {
        this.members = members;
    }
}
