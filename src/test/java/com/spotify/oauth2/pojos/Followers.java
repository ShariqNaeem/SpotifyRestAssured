package com.spotify.oauth2.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Followers {
    private Object href;
    private Integer total;

    public Object getHref() {
        return href;
    }

    public void setHref(Object href) {
        this.href = href;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
