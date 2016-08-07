package com.laligadecider.projectii;

public class MyLayout {
    private String sub;
    int attendance;

    public MyLayout() {
    }

    public MyLayout(String sub, int attendance) {
        this.sub = sub;
        this.attendance = attendance;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }
}

