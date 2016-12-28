package com.tingbob.nestscrollview;

import java.io.Serializable;

/**
 * Created by tingbob on 2016/12/27.
 */

public class ItemSticky implements Serializable {
    private String text;
    private int type;
    private int groupPos;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getGroupPos() {
        return groupPos;
    }

    public void setGroupPos(int groupPos) {
        this.groupPos = groupPos;
    }
}
