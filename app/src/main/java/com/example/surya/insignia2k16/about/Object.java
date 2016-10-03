package com.example.surya.insignia2k16.about;

/**
 * Created by hp on 29-06-2016.
 */

public  class Object {
    public String name;
    public String age;
    public String name1;
    public String age1;
    public String mail;
   public int photoId;

    Object(String name, String age, int photoId) {
        this.name = name;
        this.age = age;
        this.photoId = photoId;

    }
    Object(String name, String age,String mail, int photoId) {
        this.name = name;
        this.age = age;
        this.mail=mail;
        this.photoId = photoId;

    }
    Object(String name, String age) {
        this.name = name;
        this.age = age;
        this.photoId = photoId;

    }
    Object(String name, String age,String name1, String age1, int photoId)
    {
        this.name = name;
        this.age = age;
        this.name1 = name1;
        this.age1 = age1;
        this.photoId = photoId;

    }
}