package com.example.calculadora

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoedasFragment : Fragment() {

    private lateinit var etValor: EditText
    private lateinit var spinnerDe: Spinner
    private lateinit var spinnerPara: Spinner
    private lateinit var tvResultado: TextView
    private lateinit var tvDolar: TextView
    private lateinit var tvEuro: TextView

    private var cotacaoDolar: Double = 5.60
    private var cotacaoEuro: Double = 6.10

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_moedas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etValor = view.findViewById(R.id.et_valor_moeda)
        spinnerDe = view.findViewById(R.id.spinner_de_moeda)
        spinnerPara = view.findViewById(R.id.spinner_para_moeda)
        tvResultado = view.findViewById(R.id.tv_resultado_moeda)
        tvDolar = view.findViewById(R.id.tv_cotacao_dolar)
        tvEuro = view.findViewById(R.id.tv_cotacao_euro)

        val opcoesMoedas = listOf("Real (BRL)", "Dólar (USD)", "Euro (EUR)")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opcoesMoedas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerDe.adapter = adapter
        spinnerPara.adapter = adapter

        spinnerPara.setSelection(1)

        etValor.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                executarConversao()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        val seletorListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                executarConversao()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        spinnerDe.onItemSelectedListener = seletorListener
        spinnerPara.onItemSelectedListener = seletorListener

        chamarApiDaMoeda()
    }

    private fun chamarApiDaMoeda() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://economia.awesomeapi.com.br/json/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(AwesomeApiService::class.java)

        service.buscarCotacoes().enqueue(object : Callback<MoedaResult> {
            override fun onResponse(call: Call<MoedaResult>, response: Response<MoedaResult>) {
                if (response.isSuccessful && response.body() != null) {
                    val dados = response.body()!!

                    cotacaoDolar = dados.usdBrl?.valorCompra?.toDoubleOrNull() ?: cotacaoDolar
                    cotacaoEuro = dados.eurBrl?.valorCompra?.toDoubleOrNull() ?: cotacaoEuro

                    tvDolar.text = String.format("Dólar: R$ %.2f", cotacaoDolar)
                    tvEuro.text = String.format("Euro: R$ %.2f", cotacaoEuro)

                    executarConversao()
                }
            }

            override fun onFailure(call: Call<MoedaResult>, t: Throwable) {
                Toast.makeText(context, "Usando cotações locais offline", Toast.LENGTH_SHORT).show()
                tvDolar.text = String.format("Dólar (off): R$ %.2f", cotacaoDolar)
                tvEuro.text = String.format("Euro (off): R$ %.2f", cotacaoEuro)
            }
        })
    }
    private fun executarConversao() {
        val textoDigitado = etValor.text.toString()

        if (textoDigitado.isEmpty()) {
            tvResultado.text = getString(R.string.resultado, "0")
            return
        }

        val valorEntrada = textoDigitado.toDoubleOrNull() ?: 0.0
        val moedaDe = spinnerDe.selectedItem.toString()
        val moedaPara = spinnerPara.selectedItem.toString()

        val valorEmReais = when (moedaDe) {
            "Real (BRL)" -> valorEntrada
            "Dólar (USD)" -> valorEntrada * cotacaoDolar
            "Euro (EUR)" -> valorEntrada * cotacaoEuro
            else -> valorEntrada
        }

        val valorResultado = when (moedaPara) {
            "Real (BRL)" -> valorEmReais
            "Dólar (USD)" -> valorEmReais / cotacaoDolar
            "Euro (EUR)" -> valorEmReais / cotacaoEuro
            else -> valorEmReais
        }

        val sufixo = moedaPara.substringAfter(" ").replace("(", "").replace(")", "")
        val valorFormatado = String.format("%.2f %s", valorResultado, sufixo)
        tvResultado.text = getString(R.string.resultado, valorFormatado)
    }
}