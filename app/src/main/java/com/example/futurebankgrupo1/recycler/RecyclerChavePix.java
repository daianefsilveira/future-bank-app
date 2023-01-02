package com.example.futurebankgrupo1.recycler;

public class RecyclerChavePix {

    private String tipoChave;
    private String chavePix;

    public RecyclerChavePix(String tipoChave, String chavePix) {
        this.tipoChave = tipoChave;
        this.chavePix = chavePix;
    }

    public String getTipoChave() {
        return tipoChave;
    }

    public void setTipoChave(String tipoChave) {
        this.tipoChave = tipoChave;
    }

    public String getChavePix() {
        return chavePix;
    }

    public void setChavePix(String chavePix) {
        this.chavePix = chavePix;
    }
}
