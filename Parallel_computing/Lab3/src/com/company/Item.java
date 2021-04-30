package com.company;

class Item<T>{
    public T Element;
    public Integer Index;
    public Item(T element, Integer index){
        Element = element;
        Index = index;
    }

    @Override public boolean equals(Object o){
        if(!(o instanceof Item)){
            return false;
        }
        Item<T> other = (Item<T>)o;
        return other.Element.equals(this.Element);
    }

    @Override public int hashCode(){
        int prefix = 42;
        return Element.hashCode() ^ prefix;
    }

    @Override public String toString(){
        return "(v: " + Element.toString() + ", i: " + Index + ")";
    }
}
