package com.cimot.suitgame.ui.form

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cimot.suitgame.data.preference.UserPreference
import com.cimot.suitgame.databinding.FragmentFormBinding
import com.cimot.suitgame.ui.menu.MenuGameActivity
import com.google.android.material.snackbar.Snackbar


class FormFragment : Fragment() {

    private lateinit var binding: FragmentFormBinding
    private lateinit var userPreference:UserPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefilledCurrentName()
    }

    private fun prefilledCurrentName() {
        context?.let {
            userPreference= UserPreference(it)
            binding.etName.setText(userPreference.name.orEmpty())
        }
    }
    private fun isFormFilled(): Boolean {
        val name = binding.etName.text.toString()
        var isFormValid = true

        if (name.isEmpty()) {
            isFormValid = false

            Snackbar.make(binding.root, "Please input your Name,", Snackbar.LENGTH_SHORT).show()
        }
        return isFormValid
    }
    fun navigateToMenuGame(){
        if (isFormFilled()) {
            userPreference.name = binding.etName.text.toString()
            context?.startActivity(Intent(context,MenuGameActivity::class.java))
        }
    }
}