package com.systemcare.systemcare.classes;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by aline on 02/12/2016.
 */

public class Monitoramento {

    @SerializedName("MonitoramentoID")
    Long id;

    @SerializedName("DataHora")
    Date dataHora;

    @SerializedName("Valor")
    Integer valor;

    @SerializedName("PacienteID")
    Long pacienteId;

    public Monitoramento(long pacienteId, int valor) {
        this(0, pacienteId, valor);
    }

    public Monitoramento(long id, long pacienteId, int valor) {
        this.id = id;
        dataHora = new Date();
        this.pacienteId = pacienteId;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }
}
