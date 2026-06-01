package com.example.calculadora

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class CalculadoraFragment : Fragment() {

    private var primeiroNumero = 0.0
    private var operadorAtual = ""
    private var novoNumero = true

    private lateinit var tvExpressao: TextView
    private lateinit var tvResultado: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calculadora, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvExpressao = view.findViewById(R.id.tv_expressao)
        tvResultado = view.findViewById(R.id.tv_resultado)

        view.findViewById<Button>(R.id.btn_0).setOnClickListener { digitouNumero("0") }
        view.findViewById<Button>(R.id.btn_1).setOnClickListener { digitouNumero("1") }
        view.findViewById<Button>(R.id.btn_2).setOnClickListener { digitouNumero("2") }
        view.findViewById<Button>(R.id.btn_3).setOnClickListener { digitouNumero("3") }
        view.findViewById<Button>(R.id.btn_4).setOnClickListener { digitouNumero("4") }
        view.findViewById<Button>(R.id.btn_5).setOnClickListener { digitouNumero("5") }
        view.findViewById<Button>(R.id.btn_6).setOnClickListener { digitouNumero("6") }
        view.findViewById<Button>(R.id.btn_7).setOnClickListener { digitouNumero("7") }
        view.findViewById<Button>(R.id.btn_8).setOnClickListener { digitouNumero("8") }
        view.findViewById<Button>(R.id.btn_9).setOnClickListener { digitouNumero("9") }

        view.findViewById<Button>(R.id.btn_mais).setOnClickListener { digitouOperador("+") }
        view.findViewById<Button>(R.id.btn_menos).setOnClickListener { digitouOperador("-") }
        view.findViewById<Button>(R.id.btn_vezes).setOnClickListener { digitouOperador("x") }
        view.findViewById<Button>(R.id.btn_divisao).setOnClickListener { digitouOperador("÷") }

        view.findViewById<Button>(R.id.btn_igual).setOnClickListener { calcularResultado() }
    }

    private fun digitouNumero(digito: String) {
        if (novoNumero) {
            tvResultado.text = digito
            novoNumero = false
        } else {
            if (digito == "," && tvResultado.text.contains(",")) return
            if (tvResultado.text == "0" && digito != ",") {
                tvResultado.text = digito
            } else {
                tvResultado.append(digito)
            }
        }
    }

    private fun digitouOperador(operador: String) {
        val textoAtual = tvResultado.text.toString().replace(",", ".")
        primeiroNumero = textoAtual.toDoubleOrNull() ?: 0.0
        operadorAtual = operador

        tvExpressao.text = "${tvResultado.text} $operador"
        novoNumero = true
    }

    private fun calcularResultado() {
        val textoAtual = tvResultado.text.toString().replace(",", ".")
        val segundoNumero = textoAtual.toDoubleOrNull() ?: 0.0
        var resultado = 0.0

        when (operadorAtual) {
            "+" -> resultado = primeiroNumero + segundoNumero
            "-" -> resultado = primeiroNumero - segundoNumero
            "x" -> resultado = primeiroNumero * segundoNumero
            "÷" -> {
                if (segundoNumero != 0.0) {
                    resultado = primeiroNumero / segundoNumero
                } else {
                    tvResultado.text = "Erro"
                    novoNumero = true
                    return
                }
            }
        }

        tvExpressao.text = ""
        val resultadoFormatado = resultado.toString().replace(".", ",")

        if (resultadoFormatado.endsWith(",0")) {
            tvResultado.text = resultadoFormatado.substring(0, resultadoFormatado.length - 2)
        } else {
            tvResultado.text = resultadoFormatado
        }

        novoNumero = true
    }
}