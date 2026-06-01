package com.example.calculadora

import retrofit2.Call
import retrofit2.http.GET

interface AwesomeApiService {
    @GET("last/USD-BRL,EUR-BRL,BTC-BRL")
    fun buscarCotacoes(): Call<MoedaResult>
}