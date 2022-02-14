package com.cimot.suitgame.usecase

import com.cimot.suitgame.enum.SuitOptions

interface Case {
    fun decideWinner(player : SuitOptions?, player2 : SuitOptions?) : Int
}