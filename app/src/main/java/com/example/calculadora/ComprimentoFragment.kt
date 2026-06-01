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
import androidx.fragment.app.Fragment

class ComprimentoFragment : Fragment() {

    private lateinit var etValor: EditText
    private lateinit var spinnerDe: Spinner
    private lateinit var spinnerPara: Spinner
    private lateinit var tvResultado: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_comprimento, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etValor = view.findViewById(R.id.et_valor_comprimento)
        spinnerDe = view.findViewById(R.id.spinner_de_comprimento)
        spinnerPara = view.findViewById(R.id.spinner_para_comprimento)
        tvResultado = view.findViewById(R.id.tv_resultado_comprimento)

        // Sua lista exata de opções
        val opcoesComp = listOf(
            "Metro (m)", "Decimetro (dm)", "Centímetro (cm)", "Milímetro (mm)",
            "Hectômetro (hm)", "Quilômetro (km)", "Polegada (in)", "Pé (ft)",
            "Jarda (yd)", "Milha (mi)"
        )

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opcoesComp)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerDe.adapter = adapter
        spinnerPara.adapter = adapter

        spinnerPara.setSelection(2)

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
    }

    private fun executarConversao() {
        val textoDigitado = etValor.text.toString()

        if (textoDigitado.isEmpty()) {
            tvResultado.text = getString(R.string.resultado, "0")
            return
        }

        val valorEntrada = textoDigitado.toDoubleOrNull() ?: 0.0
        val unidadeDe = spinnerDe.selectedItem.toString()
        val unidadePara = spinnerPara.selectedItem.toString()

        val valorEmMetros = when (unidadeDe) {
            "Metro (m)" -> valorEntrada
            "Decimetro (dm)" -> valorEntrada * 0.1
            "Centímetro (cm)" -> valorEntrada * 0.01
            "Milímetro (mm)" -> valorEntrada * 0.001
            "Hectômetro (hm)" -> valorEntrada * 100.0
            "Quilômetro (km)" -> valorEntrada * 1000.0
            "Polegada (in)" -> valorEntrada * 0.0254
            "Pé (ft)" -> valorEntrada * 0.3048
            "Jarda (yd)" -> valorEntrada * 0.9144
            "Milha (mi)" -> valorEntrada * 1609.34
            else -> valorEntrada
        }

        var valorResultado = when (unidadePara) {
            "Metro (m)" -> valorEmMetros
            "Decimetro (dm)" -> valorEmMetros / 0.1
            "Centímetro (cm)" -> valorEmMetros / 0.01
            "Milímetro (mm)" -> valorEmMetros / 0.001
            "Hectômetro (hm)" -> valorEmMetros / 100.0
            "Quilômetro (km)" -> valorEmMetros / 1000.0
            "Polegada (in)" -> valorEmMetros / 0.0254
            "Pé (ft)" -> valorEmMetros / 0.3048
            "Jarda (yd)" -> valorEmMetros / 0.9144
            "Milha (mi)" -> valorEmMetros / 1609.34
            else -> valorEmMetros
        }

        val sufixo = unidadePara.substringAfter("(").substringBefore(")")

        val valorFormatado = String.format("%.4f %s", valorResultado, sufixo)

        tvResultado.text = getString(R.string.resultado, valorFormatado)
    }
}