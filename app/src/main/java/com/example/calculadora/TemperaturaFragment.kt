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

class TemperaturaFragment : Fragment() {

    private lateinit var etValor: EditText
    private lateinit var spinnerDe: Spinner
    private lateinit var spinnerPara: Spinner
    private lateinit var tvResultado: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_temperatura, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etValor = view.findViewById(R.id.et_valor_temperatura)
        spinnerDe = view.findViewById(R.id.spinner_de_temperatura)
        spinnerPara = view.findViewById(R.id.spinner_para_temperatura)
        tvResultado = view.findViewById(R.id.tv_resultado_temperatura)

        val opcoesTemp = listOf("Celsius (°C)", "Fahrenheit (°F)", "Kelvin (K)")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opcoesTemp)
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

        if (unidadeDe == "Celsius (°C)" && unidadePara == "Fahrenheit (°F)") {
            valorResultado = (valorEntrada * 1.8) + 32
            sufixo = " °F"
        }
        else if (unidadeDe == "Celsius (°C)" && unidadePara == "Kelvin (K)") {
            valorResultado = valorEntrada + 273.15
            sufixo = " K"
        }

        else if (unidadeDe == "Fahrenheit (°F)" && unidadePara == "Celsius (°C)") {
            valorResultado = (valorEntrada - 32) / 1.8
            sufixo = " °C"
        }
        else if (unidadeDe == "Fahrenheit (°F)" && unidadePara == "Kelvin (K)") {
            valorResultado = ((valorEntrada - 32) * 5/9) + 273.15
            sufixo = " K"
        }

        else if (unidadeDe == "Kelvin (K)" && unidadePara == "Celsius (°C)") {
            valorResultado = valorEntrada - 273.15
            sufixo = " °C"
        }
        else if (unidadeDe == "Kelvin (K)" && unidadePara == "Fahrenheit (°F)") {
            valorResultado = ((valorEntrada - 273.15) * 1.8) + 32
            sufixo = " °F"
        }

        else if (unidadeDe == unidadePara) {
            valorResultado = valorEntrada
            sufixo = ""
        }

        val valorFormatado = String.format("%.2f%s", valorResultado, sufixo)
        tvResultado.text = getString(R.string.resultado, valorFormatado)
    }
}