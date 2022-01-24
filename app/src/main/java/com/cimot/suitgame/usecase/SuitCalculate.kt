package com.cimot.suitgame.usecase

import com.cimot.suitgame.enum.SuitOptions

class SuitCalculate : Case {
    companion object{
        const val TIE = 0
        const val PLAYER_WIN = 1
        const val COM_WIN = 2
    }

    override fun decideWinner(player: SuitOptions?, com: SuitOptions?): Int {
        return if (player == com){
            TIE
        }else if (player == SuitOptions.ROCK){
            if (com == SuitOptions.PAPER){
                COM_WIN
            }else{
                PLAYER_WIN
            }
        }else if (player == SuitOptions.PAPER){
            if (com == SuitOptions.SCISSOR){
                COM_WIN
            }else {
                PLAYER_WIN
            }
        }else{
            if (com == SuitOptions.ROCK){
                COM_WIN
            }else{
                PLAYER_WIN
            }
        }
    }
}