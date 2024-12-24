package drivers;

import com.google.gson.Gson;

import java.util.List;

class Item {
    public int id;
    public String address;
    public String person;
    public long date;
    public int read;
    public int status;
    public int type;
    public String subject;
    public String body;
    public String serviceCenter;

}

class Response {
    public List<Item> items;
    public int total;

    // Getters and setters (можно сгенерировать или добавить вручную)
}


