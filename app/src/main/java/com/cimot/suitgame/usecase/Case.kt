package com.cimot.suitgame.usecase

import com.cimot.suitgame.enum.SuitOptions

interface Case {
    fun decideWinner(player : SuitOptions?, com : SuitOptions?) : Int
}