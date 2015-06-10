package io.github.trianglecube36.betterchunks;

public class Method {
    
    public String owner;
    public String name;
    public String desc;
    
    public Method(String owner, String name, String desc) {
        this.owner = owner;
        this.name = name;
        this.desc = desc;
    }

    public boolean equals(Object o){
        if(o instanceof Method){
            Method m = (Method)o;
            return m.owner.equals(owner) && m.name.equals(name) && m.desc.equals(desc);
        }
        return false;
    }
    
    public int hashCode(){
        return owner.hashCode() ^ name.hashCode() ^ desc.hashCode();
    }
    
    public String toString(){
        return owner + '.' + name + ' ' + desc;
    }
}
