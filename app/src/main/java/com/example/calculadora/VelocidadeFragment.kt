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

class VelocidadeFragment : Fragment() {

    private lateinit var etValor: EditText
    private lateinit var spinnerDe: Spinner
    private lateinit var spinnerPara: Spinner
    private lateinit var tvResultado: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_velocidade, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etValor = view.findViewById(R.id.et_valor_velocidade)
        spinnerDe = view.findViewById(R.id.spinner_de_velocidade)
        spinnerPara = view.findViewById(R.id.spinner_para_velocidade)
        tvResultado = view.findViewById(R.id.tv_resultado_velocidade)

        val opcoesVel = listOf(
            "Milha por Hora (mph)",
            "Metro por Segundo (m/s)",
            "Quilômetro por hora (km/h)",
            "Nó (kn)"
        )

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opcoesVel)
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

        val valorEmMS = when (unidadeDe) {
            "Metro por Segundo (m/s)" -> valorEntrada
            "Quilômetro por hora (km/h)" -> valorEntrada / 3.6
            "Milha por Hora (mph)" -> valorEntrada * 0.44704
            "Nó (kn)" -> valorEntrada * 0.514444
            else -> valorEntrada
        }

        val valorResultado = when (unidadePara) {
            "Metro por Segundo (m/s)" -> valorEmMS
            "Quilômetro por hora (km/h)" -> valorEmMS * 3.6
            "Milha por Hora (mph)" -> valorEmMS / 0.44704
            "Nó (kn)" -> valorEmMS / 0.514444
            else -> valorEmMS
        }

        val sufixo = unidadePara.substringAfter("(").substringBefore(")")
        val valorFormatado = String.format("%.2f %s", valorResultado, sufixo)
        tvResultado.text = getString(R.string.resultado, valorFormatado)
    }
}