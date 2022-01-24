package com.cimot.suitgame.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.cimot.suitgame.R
import com.cimot.suitgame.databinding.ActivityMainBinding
import com.cimot.suitgame.databinding.LoseDialogBinding
import com.cimot.suitgame.databinding.TieDialogBinding
import com.cimot.suitgame.databinding.WinDialogBinding
import com.cimot.suitgame.enum.SuitOptions
import com.cimot.suitgame.usecase.Case
import com.cimot.suitgame.usecase.SuitCalculate
import java.util.concurrent.ThreadLocalRandom

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.simpleName

    private lateinit var binding: ActivityMainBinding

    private lateinit var bindingDialogBindingWin: WinDialogBinding

    private lateinit var bindingDialogBindingLose: LoseDialogBinding

    private lateinit var bindingDialogBindingTie: TieDialogBinding

    private var player: SuitOptions? = null

    private var com: SuitOptions? = null

    private lateinit var case: Case

    // flag
    private var isGameFinished: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViews()
        setOnClicklistener()


    }

    private fun bindViews() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        bindingDialogBindingWin = WinDialogBinding.inflate(layoutInflater)
        bindingDialogBindingLose = LoseDialogBinding.inflate(layoutInflater)
        bindingDialogBindingTie = TieDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    private fun setOnClicklistener() {
        binding.ivRock.setOnClickListener {
            if (!isGameFinished) {
                binding.ivPlayer.setImageResource(R.drawable.ic_rock)
                binding.ivPlayer.rotationY = 180f
                binding.ivPlayer.animate().apply {
                    duration = 400
                    rotationYBy(360f)
                }.start()
                binding.ivRock.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
                binding.ivScissor.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.purple_500
                    )
                )
                binding.ivPaper.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500))
                player = SuitOptions.ROCK
                Log.d(TAG, "setOnClicklistener: Player Choosed Rock")
                startGame()
            } else {
                resetGame()
            }


        }
        binding.ivPaper.setOnClickListener {
            if (!isGameFinished) {
                binding.ivPlayer.setImageResource(R.drawable.ic_paper)
                binding.ivPlayer.rotationY = 180f
                binding.ivPlayer.animate().apply {
                    duration = 400
                    rotationYBy(360f)
                }.start()
                binding.ivRock.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500))
                binding.ivScissor.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.purple_500
                    )
                )
                binding.ivPaper.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
                player = SuitOptions.PAPER
                Log.d(TAG, "setOnClicklistener: Player Choosed Paper")
                startGame()
            } else {
                resetGame()
            }


        }
        binding.ivScissor.setOnClickListener {

            if (!isGameFinished) {
                binding.ivPlayer.setImageResource(R.drawable.ic_scissor)
                binding.ivPlayer.rotationY = 180f
                binding.ivPlayer.animate().apply {
                    duration = 400
                    rotationYBy(360f)
                }.start()
                binding.ivRock.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500))
                binding.ivScissor.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
                binding.ivPaper.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500))
                player = SuitOptions.SCISSOR
                Log.d(TAG, "setOnClicklistener: Player Choosed Scissor")
                startGame()
            } else {
                resetGame()
            }

        }

    }

    private fun resetGame() {
        Log.d(TAG, "resetGame: $isGameFinished")
        isGameFinished = false
        player = null
        com = null
        binding.ivPlayer.animate().apply {
            duration = 300
            rotationYBy(360f)
        }.withEndAction {
            binding.ivPlayer.animate().apply {
                duration = 1000
                rotationYBy(360f)
            }.start()

        }
        binding.ivCom.animate().apply {
            duration = 300
            rotationYBy(360f)
        }.withEndAction {
            binding.ivCom.animate().apply {
                duration = 1000
                rotationYBy(360f)
            }.start()

        }
        bindViews()
        setOnClicklistener()
        Log.d(TAG, "resetGame: $isGameFinished")
    }

    private fun startGame() {
        var random = ThreadLocalRandom.current().nextInt(3)


        if (random == 0) {
            binding.ivCom.setImageResource(R.drawable.ic_rock)
            binding.ivCom.animate().apply {
                duration = 400
                rotationYBy(360f)
            }.start()
            com = SuitOptions.ROCK
            Log.d(TAG, "startGame: Com Choosed Rock")
            calcResult()
        } else if (random == 1) {
            binding.ivCom.setImageResource(R.drawable.ic_paper)
            binding.ivCom.animate().apply {
                duration = 400
                rotationYBy(360f)
            }.start()
            com = SuitOptions.PAPER
            Log.d(TAG, "startGame: Com Choosed Paper")
            calcResult()
        } else if (random == 2) {
            binding.ivCom.setImageResource(R.drawable.ic_scissor)
            binding.ivCom.animate().apply {
                duration = 400
                rotationYBy(360f)
            }.start()
            com = SuitOptions.SCISSOR
            Log.d(TAG, "startGame: Com Choosed Scissor")
            calcResult()
        }
        isGameFinished = true
    }


    private fun calcResult() {
        case = SuitCalculate()
        if (player != null && com != null) {
            when (case.decideWinner(player, com)) {
                SuitCalculate.PLAYER_WIN -> {
                    Log.d(TAG, "calcResult: Player Won")
                    showWinDialog()
                }
                SuitCalculate.COM_WIN -> {
                    Log.d(TAG, "calcResult: Player Lose")
                    showLoseDialog()
                }
                SuitCalculate.TIE -> {
                    Log.d(TAG, "calcResult: Tie")
                    showTieDialog()
                }
            }
        }

    }

    private fun showTieDialog() {
        val dialog = AlertDialog.Builder(this).apply { setView(bindingDialogBindingTie.root) }
            .create()
        dialog.window?.setWindowAnimations(R.style.AnimationsForDialog)
        bindingDialogBindingTie.ivTie.animate().apply {
            duration = 1400
            rotationYBy(360f)
        }.start()
        dialog.show()
        bindingDialogBindingTie.ivRefresh3.setOnClickListener {
            dialog.dismiss()
            resetGame()

        }
    }

    private fun showLoseDialog() {
        val dialog = AlertDialog.Builder(this).apply { setView(bindingDialogBindingLose.root) }
            .create()

        dialog.window?.setWindowAnimations(R.style.AnimationsForDialog)
        bindingDialogBindingLose.ivLose.animate().apply {
            duration = 1400
            rotationYBy(360f)
        }.start()
        dialog.show()
        bindingDialogBindingLose.ivRefresh2.setOnClickListener {
            dialog.dismiss()
            resetGame()
        }

    }


    private fun showWinDialog() {
        val dialog = AlertDialog.Builder(this).apply { setView(bindingDialogBindingWin.root) }
            .create()
        dialog.window?.setWindowAnimations(R.style.AnimationsForDialog)
        bindingDialogBindingWin.ivWon.animate().apply {
            duration = 1400
            rotationYBy(360f)
        }.start()
        bindingDialogBindingWin.tvWon.text = getString(R.string.text_won)
        dialog.show()
        bindingDialogBindingWin.ivRefresh1.setOnClickListener {
            dialog.dismiss()
            resetGame()

        }


    }

}