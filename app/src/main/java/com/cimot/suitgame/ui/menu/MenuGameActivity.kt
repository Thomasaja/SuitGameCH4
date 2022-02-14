package com.cimot.suitgame.ui.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cimot.suitgame.R
import com.cimot.suitgame.data.preference.UserPreference
import com.cimot.suitgame.databinding.ActivityMainBinding
import com.cimot.suitgame.databinding.ActivityMenuBinding
import com.cimot.suitgame.ui.main.MainActivity

class MenuGameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle()
        setClicklistener()

    }

    private fun setClicklistener() {
        binding.ivPlayerVsPlayer.setOnClickListener {
            binding.ivPlayerVsPlayer.setBackgroundResource(R.drawable.image_bg_click)
            MainActivity.startThisActivity(this,MainActivity.PLAY_MODE_PLAYER)
        }
        binding.ivPlayerVsComputer.setOnClickListener {
            binding.ivPlayerVsComputer.setBackgroundResource(R.drawable.image_bg_click)
            MainActivity.startThisActivity(this,MainActivity.PLAY_MODE_COMPUTER)
        }

    }

    private fun setTitle() {
        binding.tvFieldName.text = String.format(
            getString(
                R.string.text_title_menu,
                UserPreference(this).name
            )
        )
    }
}