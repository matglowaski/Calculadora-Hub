package com.example.calculadora

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton

class ConversoresFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_conversores, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageButton>(R.id.newton).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MassaFragment())
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<ImageButton>(R.id.gelado).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, TemperaturaFragment())
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<ImageButton>(R.id.mcqueen).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, VelocidadeFragment())
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<ImageButton>(R.id.longcat).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ComprimentoFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}