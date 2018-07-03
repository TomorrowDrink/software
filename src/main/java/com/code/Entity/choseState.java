package com.code.Entity;

public class choseState {
    private String choseState;
    private int flag;

    public String getChoseState() {
        return choseState;
    }

    public void setChoseState(String choseState) {
        this.choseState = choseState;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public String toString(){
        return "choseState{"+
                "choseState'"+choseState+'\''+
                "flag'"+flag+'\''+
                '}';
    }
}
