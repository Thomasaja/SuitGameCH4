package com.cimot.suitgame.usecase

import com.cimot.suitgame.enum.SuitOptions

class SuitUseCaseImpl : Case {
    companion object{
        const val TIE = 0
        const val PLAYER_WIN = 1
        const val PLAYER2_WIN = 2
    }

    override fun decideWinner(player: SuitOptions?, player2: SuitOptions?): Int {
        return if (player == player2){
            TIE
        }else if (player == SuitOptions.ROCK){
            if (player2 == SuitOptions.PAPER){
                PLAYER2_WIN
            }else{
                PLAYER_WIN
            }
        }else if (player == SuitOptions.PAPER){
            if (player2 == SuitOptions.SCISSOR){
                PLAYER2_WIN
            }else {
                PLAYER_WIN
            }
        }else{
            if (player2 == SuitOptions.ROCK){
                PLAYER2_WIN
            }else{
                PLAYER_WIN
            }
        }
    }
}