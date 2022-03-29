package com.example.schoolscheduler.TaskPage;

public class Model {
    private boolean isSelected;
    private String i;
    private String n;
    private String s;
    private String t;
    private String du;
    private String d;
    public String[] getTask() {
        String[] str = {i,n,s,t,du,d};
        return str;
    }
    public void mseti(String n) { this.i = n; }
    public void msetn(String n) { this.n = n; }
    public void msets(String n) {
        this.s = n;
    }
    public void msett(String n) {
        this.t = n;
    }
    public void msetdu(String n) {
        this.du = n;
    }
    public void msetd(String n) {
        this.d = n;
    }
    public boolean getSelected() {
        return isSelected;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}