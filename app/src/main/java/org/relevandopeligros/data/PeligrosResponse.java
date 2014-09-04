package org.relevandopeligros.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Created by mauricio.heredia on 9/3/14.
 */
public class PeligrosResponse implements Serializable {

    @SerializedName("_embedded")
    Info info;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public static class Info implements Serializable{
        public List<Peligro> getPeligros() {
            return peligros;
        }

        public void setPeligros(List<Peligro> peligros) {
            this.peligros = peligros;
        }

        List<Peligro> peligros = Collections.EMPTY_LIST;
    }

}
