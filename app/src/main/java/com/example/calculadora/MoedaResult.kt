package com.example.calculadora

import com.google.gson.annotations.SerializedName

data class MoedaResult(
    @SerializedName("USDBRL") val usdBrl: MoedaInfo?,
    @SerializedName("EURBRL") val eurBrl: MoedaInfo?,
    @SerializedName("BTCBRL") val btcBrl: MoedaInfo?
)
data class MoedaInfo(
    @SerializedName("bid") val valorCompra: String
)