package controller;

import java.util.ArrayList;

public class Database {
    public static Test test;

    public static ArrayList<memberAttandance> arrayList = new ArrayList();
    public static ArrayList<memberAttandance> arrayList1 = new ArrayList();

    static {
        arrayList.add(new memberAttandance("Jan",10));
        arrayList.add(new memberAttandance("Feb",40));
        arrayList.add(new memberAttandance("Mar",15));
        arrayList.add(new memberAttandance("Apr",30));
        arrayList.add(new memberAttandance("May",7));
        arrayList.add(new memberAttandance("Jun",9));
    }

    static {
        arrayList1.add(new memberAttandance("Jan",10));
        arrayList1.add(new memberAttandance("Feb",50));
        arrayList1.add(new memberAttandance("Mar",30));
        arrayList1.add(new memberAttandance("Apr",40));
        arrayList1.add(new memberAttandance("May",1));
        arrayList1.add(new memberAttandance("Jun",7));
    }
}
