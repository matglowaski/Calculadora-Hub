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

        view.findViewById<Button>(R.id.btn_virgula).setOnClickListener { digitouVirgula() }
        view.findViewById<Button>(R.id.btn_apagar).setOnClickListener { digitouApagar() }
        view.findViewById<Button>(R.id.btn_parentesesL).setOnClickListener { digitouParenteses("(") }
        view.findViewById<Button>(R.id.btn_parentesesR).setOnClickListener { digitouParenteses(")") }
        view.findViewById<Button>(R.id.btn_limparsessao).setOnClickListener { limparSessao() }
        view.findViewById<Button>(R.id.btn_igual).setOnClickListener { calcularResultado() }
    }

    private fun digitouNumero(digito: String) {
        if (tvResultado.text == "Erro") {
            tvResultado.text = "0"
        }

        if (novoNumero) {
            tvResultado.text = digito
            novoNumero = false
        } else {
            if (tvResultado.text == "0") {
                tvResultado.text = digito
            } else {
                tvResultado.append(digito)
            }
        }
    }

    private fun digitouVirgula() {
        if (tvResultado.text == "Erro") return

        if (novoNumero) {
            tvResultado.text = "0,"
            novoNumero = false
        } else {
            // evita colocar mais de uma vírgula no mesmo número
            if (!tvResultado.text.contains(",")) {
                tvResultado.append(",")
            }
        }
    }
    private fun limparSessao() { //apagar tudo
        primeiroNumero = 0.0
        operadorAtual = ""
        tvExpressao.text = ""
        tvResultado.text = "0"
        novoNumero = true
    }

    private fun digitouApagar() {  //apagar char
        if (tvResultado.text == "Erro") {
            tvResultado.text = "0"
            return
        }

        val textoAtual = tvResultado.text.toString()

        if (textoAtual.length > 1) {
            // remove o último caractere
            tvResultado.text = textoAtual.substring(0, textoAtual.length - 1)
        } else {
            // se só tiver 1 caractere, volta a ser 0
            tvResultado.text = "0"
            novoNumero = true
        }
    }

    private fun digitouParenteses(parenteses: String) {
        if (tvResultado.text == "Erro") return

        if (novoNumero || tvResultado.text == "0") {
            tvResultado.text = parenteses
            novoNumero = false
        } else {
            tvResultado.append(parenteses)
        }
    }

    private fun digitouOperador(operador: String) {
        if (tvResultado.text == "Erro") return

        val textoAtual = tvResultado.text.toString()

        if (tvExpressao.text.isEmpty() && textoAtual != "0") {
            tvExpressao.text = "$textoAtual $operador "
        } else {
            tvExpressao.append("$textoAtual $operador ")
        }

        novoNumero = true
    }

    private fun calcularResultado() {
        val expressaoCompleta = (tvExpressao.text.toString() + tvResultado.text.toString())
            .replace(",", ".")
            .replace("x", "*")
            .replace("÷", "/")

        if (expressaoCompleta.isEmpty()) return

        try {
            val expression = net.objecthunter.exp4j.ExpressionBuilder(expressaoCompleta).build()
            val resultado = expression.evaluate()

            tvExpressao.text = ""
            val resultadoFormatado = resultado.toString().replace(".", ",")

            if (resultadoFormatado.endsWith(",0")) {
                tvResultado.text = resultadoFormatado.substring(0, resultadoFormatado.length - 2)
            } else {
                tvResultado.text = resultadoFormatado
            }

        } catch (e: Exception) {
            tvResultado.text = "Erro"
        }

        novoNumero = true
    }
}