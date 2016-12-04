package com.systemcare.systemcare.api;

import com.systemcare.systemcare.classes.Monitoramento;
import com.systemcare.systemcare.classes.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by aline on 02/12/2016.
 */

public interface ApiInterface {

    @POST("api/MonitoramentosAPI/")
    Call<Monitoramento> postMonitoramento(@Body Monitoramento monitoramento);

    @POST("api/PacintesAPI")
    Call<Usuario> postPacientes(@Body Usuario usuario);
}
