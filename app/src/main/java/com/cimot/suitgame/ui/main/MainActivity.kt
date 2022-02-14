package com.cimot.suitgame.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.cimot.suitgame.R
import com.cimot.suitgame.data.preference.UserPreference
import com.cimot.suitgame.databinding.*
import com.cimot.suitgame.enum.SuitOptions
import com.cimot.suitgame.ui.menu.MenuGameActivity
import com.cimot.suitgame.usecase.Case
import com.cimot.suitgame.usecase.SuitUseCaseImpl
import java.util.concurrent.ThreadLocalRandom

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.simpleName

    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingResultDialog: ResultDialogBinding

    private var player: SuitOptions? = null
    private var com: SuitOptions? = null
    private var player2: SuitOptions? = null

    private var playMode: Int = PLAY_MODE_COMPUTER

    private lateinit var case: Case


    // flag
    private var isGameFinished: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        getIntentExtras()
        bindViews()
        setNamePlayer()
        setFirstView()


    }

    private fun setFirstView() {
        if (playMode == PLAY_MODE_PLAYER) {
            binding.ivBgMain.setImageResource(R.drawable.ic_bg_pvp2)
            binding.ivEnemy.setImageResource(R.drawable.ic_player)
            binding.tvEnemy.text = getString(R.string.text_player2)
            binding.ivEnemy.rotationY = 180f
            setOnClicklistener()
        } else {
            setOnClicklistener()
        }
    }

    private fun setNamePlayer() {
        binding.tvPlayer.text = String.format(
            getString(
                R.string.text_player,
                UserPreference(this).name?.trim()
            )
        )
    }

    private fun getIntentExtras() {
        playMode = intent.getIntExtra(ARG_EXTRAPLAY_MODE, PLAY_MODE_COMPUTER)
    }

    private fun bindViews() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        bindingResultDialog = ResultDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    private fun startGamePlayer2() {


        Toast.makeText(this, "Player 2 Turn", Toast.LENGTH_SHORT).show()
        binding.ivRock.setOnClickListener {
            binding.ivEnemy.setImageResource(R.drawable.ic_rock)
            binding.ivEnemy.animate().apply {
                duration = 400
                rotationYBy(180f)
            }.start()
            player2 = SuitOptions.ROCK
            Log.d(TAG, "startGamePlayer2: Player2 choosed rock")
            binding.ivPlayer.visibility = View.VISIBLE
            binding.ivPlayer.rotationY = 180f
            binding.ivPlayer.animate().apply {
                duration = 400
                rotationYBy(360f)
            }.start()
            binding.ivRock.setBackgroundResource(R.drawable.image_bg_click)
            if (player == SuitOptions.SCISSOR) {
                binding.ivScissor.setBackgroundResource(R.drawable.image_bg_click)
            } else if (player == SuitOptions.ROCK) {
                binding.ivRock.setBackgroundResource(R.drawable.image_bg_click2)
            } else {
                binding.ivPaper.setBackgroundResource(R.drawable.image_bg_click)
            }
            calcResultPlayer2()
        }
        binding.ivPaper.setOnClickListener {
            binding.ivEnemy.setImageResource(R.drawable.ic_paper)
            binding.ivEnemy.animate().apply {
                duration = 400
                rotationYBy(180f)
            }.start()
            player2 = SuitOptions.PAPER
            Log.d(TAG, "startGamePlayer2: Player2 choosed paper")
            binding.ivPlayer.visibility = View.VISIBLE
            binding.ivPlayer.rotationY = 180f
            binding.ivPlayer.animate().apply {
                duration = 400
                rotationYBy(360f)
            }.start()
            binding.ivPaper.setBackgroundResource(R.drawable.image_bg_click)
            if (player == SuitOptions.SCISSOR) {
                binding.ivScissor.setBackgroundResource(R.drawable.image_bg_click)
            } else if (player == SuitOptions.ROCK) {
                binding.ivRock.setBackgroundResource(R.drawable.image_bg_click)
            } else {
                binding.ivPaper.setBackgroundResource(R.drawable.image_bg_click2)
            }
            calcResultPlayer2()
        }
        binding.ivScissor.setOnClickListener {
            binding.ivEnemy.setImageResource(R.drawable.ic_scissor)
            binding.ivEnemy.animate().apply {
                duration = 400
                rotationYBy(180f)
            }.start()
            player2 = SuitOptions.SCISSOR
            binding.ivScissor.setBackgroundResource(R.drawable.image_bg_click)
            if (player == SuitOptions.SCISSOR) {
                binding.ivScissor.setBackgroundResource(R.drawable.image_bg_click2)
            } else if (player == SuitOptions.ROCK) {
                binding.ivRock.setBackgroundResource(R.drawable.image_bg_click)
            } else {
                binding.ivPaper.setBackgroundResource(R.drawable.image_bg_click)
            }
            Log.d(TAG, "startGamePlayer2: Player2 choosed scissor")
            binding.ivPlayer.visibility = View.VISIBLE
            binding.ivPlayer.rotationY = 180f
            binding.ivPlayer.animate().apply {
                duration = 400
                rotationYBy(360f)
            }.start()
            calcResultPlayer2()
        }


    }

    private fun calcResultPlayer2() {
        case = SuitUseCaseImpl()
        if (player != null && player2 != null) {
            when (case.decideWinner(player, player2)) {
                SuitUseCaseImpl.PLAYER_WIN -> {
                    Log.d(TAG, "calcResultPlayer2: Player1 Won")
                    showDialog()
                    bindingResultDialog.tvTitleDialog.text = String.format(
                        getString(
                            R.string.text_result_won, UserPreference(this).name
                        )
                    )
                }
                SuitUseCaseImpl.PLAYER2_WIN -> {
                    Log.d(TAG, "calcResultPlayer2: Player2 Won")
                    showDialog()
                    bindingResultDialog.tvTitleDialog.text = getString(R.string.text_player2_won)
                }
                SuitUseCaseImpl.TIE -> {
                    Log.d(TAG, "calcResultPlayer2: Tie")
                    showDialog()
                    bindingResultDialog.ivEmotDialog.setImageResource(R.drawable.ic_tie)
                    bindingResultDialog.tvTitleDialog.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun setOnClicklistener() {
        if (isGameFinished) {
            resetGame()
        } else {
            if (playMode == PLAY_MODE_COMPUTER) {
                binding.ivRock.setOnClickListener {
                    binding.ivPlayer.setImageResource(R.drawable.ic_rock)
                    binding.ivPlayer.rotationY = 180f
                    binding.ivPlayer.animate().apply {
                        duration = 400
                        rotationYBy(360f)
                    }.start()
                    player = SuitOptions.ROCK
                    Log.d(TAG, "setOnClicklistener: Player1 Choosed Rock")
                    startGameCom()


                }

                binding.ivPaper.setOnClickListener {
                    binding.ivPlayer.setImageResource(R.drawable.ic_paper)
                    binding.ivPlayer.rotationY = 180f
                    binding.ivPlayer.animate().apply {
                        duration = 400
                        rotationYBy(360f)
                    }.start()
                    player = SuitOptions.PAPER
                    Log.d(TAG, "setOnClicklistener: Player1 Choosed Paper")
                    startGameCom()

                }
                binding.ivScissor.setOnClickListener {
                    binding.ivPlayer.setImageResource(R.drawable.ic_scissor)
                    binding.ivPlayer.rotationY = 180f
                    binding.ivPlayer.animate().apply {
                        duration = 400
                        rotationYBy(360f)
                    }.start()
                    player = SuitOptions.SCISSOR
                    Log.d(TAG, "setOnClicklistener: Player1 Choosed Scissor")
                    startGameCom()
                }

            } else {
                binding.ivRock.setOnClickListener {
                    binding.ivPlayer.setImageResource(R.drawable.ic_rock)
                    binding.ivPlayer.visibility = View.INVISIBLE
                    player = SuitOptions.ROCK
                    Log.d(TAG, "setOnClicklistener: Player1 Choosed Rock")
                    startGamePlayer2()


                }

                binding.ivPaper.setOnClickListener {
                    binding.ivPlayer.setImageResource(R.drawable.ic_paper)
                    binding.ivPlayer.visibility = View.INVISIBLE
                    player = SuitOptions.PAPER
                    Log.d(TAG, "setOnClicklistener: Player1 Choosed Paper")
                    startGamePlayer2()

                }
                binding.ivScissor.setOnClickListener {
                    binding.ivPlayer.setImageResource(R.drawable.ic_scissor)
                    binding.ivPlayer.visibility = View.INVISIBLE
                    player = SuitOptions.SCISSOR
                    Log.d(TAG, "setOnClicklistener: Player1 Choosed Scissor")
                    startGamePlayer2()
                }

            }


        }

    }

    private fun resetGame() {
        Log.d(TAG, "resetGame: $isGameFinished")
        isGameFinished = false
        player = null
        com = null
        player2 = null
        binding.ivPlayer.animate().apply {
            duration = 300
            rotationYBy(360f)
        }.withEndAction {
            binding.ivPlayer.animate().apply {
                duration = 1000
                rotationYBy(360f)
            }.start()

        }
        binding.ivEnemy.animate().apply {
            duration = 300
            rotationYBy(360f)
        }.withEndAction {
            binding.ivEnemy.animate().apply {
                duration = 1000
                rotationYBy(360f)
            }.start()

        }
        bindViews()
        setNamePlayer()
        setFirstView()
        Log.d(TAG, "resetGame: $isGameFinished")
    }

    private fun startGameCom() {
        val random = ThreadLocalRandom.current().nextInt(3)


        if (random == 0) {
            binding.ivEnemy.setImageResource(R.drawable.ic_rock)
            binding.ivEnemy.animate().apply {
                duration = 400
                rotationYBy(360f)
            }.start()
            binding.ivRock.setBackgroundResource(R.drawable.image_bg_click)
            com = SuitOptions.ROCK
            if (player == SuitOptions.SCISSOR) {
                binding.ivScissor.setBackgroundResource(R.drawable.image_bg_click)
            } else if (player == SuitOptions.ROCK) {
                binding.ivRock.setBackgroundResource(R.drawable.image_bg_click2)
            } else {
                binding.ivPaper.setBackgroundResource(R.drawable.image_bg_click)
            }
            Log.d(TAG, "startGameCom: Com Choosed Rock")
            Toast.makeText(this, "Com Choosed Rock", Toast.LENGTH_SHORT).show()
            calcResultCom()
        } else if (random == 1) {
            binding.ivEnemy.setImageResource(R.drawable.ic_paper)
            binding.ivEnemy.animate().apply {
                duration = 400
                rotationYBy(360f)
            }.start()
            binding.ivPaper.setBackgroundResource(R.drawable.image_bg_click)
            com = SuitOptions.PAPER
            if (player == SuitOptions.SCISSOR) {
                binding.ivScissor.setBackgroundResource(R.drawable.image_bg_click)
            } else if (player == SuitOptions.ROCK) {
                binding.ivRock.setBackgroundResource(R.drawable.image_bg_click)
            } else {
                binding.ivPaper.setBackgroundResource(R.drawable.image_bg_click2)
            }
            Log.d(TAG, "startGameCom: Com Choosed Paper")
            Toast.makeText(this, "Com Choosed Paper", Toast.LENGTH_SHORT).show()
            calcResultCom()
        } else if (random == 2) {
            binding.ivEnemy.setImageResource(R.drawable.ic_scissor)
            binding.ivEnemy.animate().apply {
                duration = 400
                rotationYBy(360f)
            }.start()
            binding.ivScissor.setBackgroundResource(R.drawable.image_bg_click)
            com = SuitOptions.SCISSOR
            if (player == SuitOptions.SCISSOR) {
                binding.ivScissor.setBackgroundResource(R.drawable.image_bg_click2)
            } else if (player == SuitOptions.ROCK) {
                binding.ivRock.setBackgroundResource(R.drawable.image_bg_click)
            } else {
                binding.ivPaper.setBackgroundResource(R.drawable.image_bg_click)
            }
            Log.d(TAG, "startGameCom: Com Choosed Scissor")
            Toast.makeText(this, "Com Choosed Scissor", Toast.LENGTH_SHORT).show()
            calcResultCom()
        }
        isGameFinished = true
    }


    private fun calcResultCom() {
        case = SuitUseCaseImpl()
        if (player != null && com != null) {
            when (case.decideWinner(player, com)) {
                SuitUseCaseImpl.PLAYER_WIN -> {
                    Log.d(TAG, "calcResult: Player Won")
                    showDialog()
                    bindingResultDialog.tvTitleDialog.text = String.format(
                        getString(
                            R.string.text_result_won, UserPreference(this).name
                        )
                    )

                }
                SuitUseCaseImpl.PLAYER2_WIN -> {
                    Log.d(TAG, "calcResult: Player Lose")
                    showDialog()
                    bindingResultDialog.tvTitleDialog.text = String.format(
                        getString(
                            R.string.text_result_lose, UserPreference(this).name
                        )
                    )
                    bindingResultDialog.ivEmotDialog.setImageResource(R.drawable.ic_lose)
                }
                SuitUseCaseImpl.TIE -> {
                    Log.d(TAG, "calcResult: Tie")
                    showDialog()
                    bindingResultDialog.ivEmotDialog.setImageResource(R.drawable.ic_tie)
                    bindingResultDialog.tvTitleDialog.visibility = View.INVISIBLE

                }
            }
        }

    }

    private fun showDialog() {
        val dialog = AlertDialog.Builder(this).apply { setView(bindingResultDialog.root) }
            .create()
        isGameFinished = true
        dialog.window?.setWindowAnimations(R.style.AnimationsForDialog)
        bindingResultDialog.ivEmotDialog.animate().apply {
            duration = 1400
            rotationYBy(360f)
        }.start()

        dialog.show()
        dialog.setCancelable(false)
        bindingResultDialog.ivRefresh.setOnClickListener {
            bindingResultDialog.ivRefresh.setBackgroundResource(R.drawable.image_bg_click)
            dialog.dismiss()
            resetGame()
        }
        bindingResultDialog.ivHome.setOnClickListener {
            bindingResultDialog.ivHome.setBackgroundResource(R.drawable.image_bg_click)
            val intent = Intent(this, MenuGameActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }


    companion object {
        const val ARG_EXTRAPLAY_MODE = "ARG_EXTRA_PLAY_MODE"
        const val PLAY_MODE_COMPUTER = 0
        const val PLAY_MODE_PLAYER = 1

        fun startThisActivity(context: Context?, playMode: Int) {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(ARG_EXTRAPLAY_MODE, playMode)
            context?.startActivity(intent)
        }
    }

}