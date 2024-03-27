package com.ffs.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public abstract class AbstractResponse implements Serializable {

    private static final long serialVersionUID = 4494064209636802405L;

    @JsonIgnore
    private ResultCode<?> resultCode;

    @JsonIgnore
    private List<Object> objects;

    public AbstractResponse() {
        this.resultCode = DefaultResultCode.OK;
    }

    @JsonProperty("code")
    public Object getCode() {
        return this.resultCode.getCode();
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("message")
    public String getMessage() {
        return this.resultCode != null ? this.resultCode.getMessage(this.objects != null ? this.objects.toArray() : null) : null;
    }

    public void addVariable(Object... objects) {
        this.addVariable(Arrays.asList(objects));
    }

    public void addVariable(List<Object> variables) {
        if (this.objects == null) {
            this.objects = new ArrayList();
        }

        this.objects.addAll(variables);
    }
}
