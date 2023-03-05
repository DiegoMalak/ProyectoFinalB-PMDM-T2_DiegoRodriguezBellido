package com.dam.proyectofinalb_pmdm_t2_diegorodriguezbellido.retrofitData;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MuseoRes {

    @SerializedName("@context")
    @Expose
    private Context context;
    @SerializedName("@museo")
    @Expose
    private List<Museo> museo;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Museo> getMuseo() {
        return museo;
    }

    public void setMuseo(List<Museo> museo) {
        this.museo = museo;
    }

}
