package com.thuanht.eatez.jsonResponse;

import com.thuanht.eatez.model.Favourite;
import com.thuanht.eatez.model.Pagination;

import java.util.List;

public class FavouriteResponse {
    private boolean status;
    private List<Favourite> data;
    private String message;
    private Pagination pagination;
    public boolean getStatus() {
        return status;
    }

    public List<Favourite> getData() {
        return data;
    }
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setData(List<Favourite> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    @Override
    public String toString() {
        return "PostResponse{" +
                "status=" + status +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", pagination=" + pagination +
                '}';
    }
}
