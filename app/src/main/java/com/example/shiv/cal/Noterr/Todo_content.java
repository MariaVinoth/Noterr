package com.example.shiv.cal.Noterr;

/**
 * Created by Vinod on 05/11/17.
 */

public class Todo_content {

    public Integer getSeq_no() {
        return Seq_no;
    }

    public void setSeq_no(Integer seq_no) {
        Seq_no = seq_no;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Integer getCompleted() {
        return Completed;
    }

    public void setCompleted(Integer completed) {
        Completed = completed;
    }

    private Integer Seq_no;
    private Integer ID;
    private String Content;
    private Integer Completed;
}
