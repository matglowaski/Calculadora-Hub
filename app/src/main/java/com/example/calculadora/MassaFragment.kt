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

class MassaFragment : Fragment() {

    private lateinit var etValor: EditText
    private lateinit var spinnerDe: Spinner
    private lateinit var spinnerPara: Spinner
    private lateinit var tvResultado: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_massa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etValor = view.findViewById(R.id.et_valor_massa)
        spinnerDe = view.findViewById(R.id.spinner_de_massa)
        spinnerPara = view.findViewById(R.id.spinner_para_massa)
        tvResultado = view.findViewById(R.id.tv_resultado_massa)

        val opcoesMassa = listOf("Quilograma (kg)", "Grama (g)", "Onça (oz)", "Libra (lb)")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opcoesMassa)
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
            override fun onNothingSelected(parent: AdapterView<*>?,) {}
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

        var valorResultado = 0.0
        var sufixo = ""

        if (unidadeDe == "Quilograma (kg)" && unidadePara == "Grama (g)") {
            valorResultado = valorEntrada * 1000
            sufixo = " g"
        }
        else if (unidadeDe == "Quilograma (kg)" && unidadePara == "Libra (lb)") {
            valorResultado = valorEntrada * 2.20462
            sufixo = " lb"
        }
        else if (unidadeDe == "Quilograma (kg)" && unidadePara == "Onça (oz)") {
            valorResultado = valorEntrada * 35.274
            sufixo = " oz"
        }

        else if (unidadeDe == "Grama (g)" && unidadePara == "Quilograma (kg)") {
            valorResultado = valorEntrada / 1000
            sufixo = " kg"
        }
        else if (unidadeDe == "Grama (g)" && unidadePara == "Libra (lb)") {
            valorResultado = valorEntrada / 453.592
            sufixo = " lb"
        }
        else if(unidadeDe == "Grama (g)" && unidadePara == "Onça (oz)") {
            valorResultado = valorEntrada /  28.35
            sufixo = " oz"
        }

        else if(unidadeDe == "Libra (lb)" && unidadePara == "Grama (g)") {
            valorResultado = valorEntrada * 453.592
            sufixo = " g"
        }
        else if(unidadeDe == "Libra (lb)" && unidadePara == "Quilograma (kg)") {
            valorResultado = valorEntrada / 2.205
            sufixo = " kg"
        }
        else if(unidadeDe == "Libra (lb)" && unidadePara == "Onça (oz)") {
            valorResultado = valorEntrada * 16
            sufixo = " oz"
        }

        else if(unidadeDe == "Onça (oz)" && unidadePara == "Grama (g)") {
            valorResultado = valorEntrada * 28.35
            sufixo = " g"
        }
        else if(unidadeDe == "Onça (oz)" && unidadePara == "Quilograma (kg)") {
            valorResultado = valorEntrada / 35.274
            sufixo = " kg"
        }
        else if(unidadeDe == "Onça (oz)" && unidadePara == "Libra (lb)") {
            valorResultado = valorEntrada / 16
            sufixo = " lb"
        }

        else if (unidadeDe == unidadePara) {
            valorResultado = valorEntrada
            sufixo = ""
        }
        val valorFormatado = String.format("%.2f %s", valorResultado, sufixo)
        tvResultado.text = getString(R.string.resultado, valorFormatado)
    }
}