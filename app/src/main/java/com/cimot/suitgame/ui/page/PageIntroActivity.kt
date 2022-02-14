package com.cimot.suitgame.ui.page


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.cimot.suitgame.R
import com.cimot.suitgame.ui.form.FormFragment
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroFragment

class PageIntroActivity : AppIntro2() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        isSkipButtonEnabled = false

        addSlide(
            AppIntroFragment.createInstance(
                title = "Welcome to Suit Game !".uppercase(),
                description = "This is a first project app from CIMOT COMPANY\n We hope you can enjoy this Game".uppercase(),
                imageDrawable = R.drawable.ic_page_intro_suit,
                titleColorRes = R.color.black,
                descriptionColorRes = R.color.black,
                titleTypefaceFontRes = R.font.dinosaur_font,
                descriptionTypefaceFontRes = R.font.dinosaur_font,
                backgroundDrawable = R.drawable.ic_background_page
            )
        )
        addSlide(
            AppIntroFragment.createInstance(
                title = "Game Rules".uppercase(),
                description ="in the game there are 3 options that you can choose :\n - ROCK - PAPER - SCISSOR".uppercase(),
                imageDrawable = R.drawable.ic_page_intro_rules,
                titleColorRes = R.color.black,
                descriptionColorRes =R.color.black,
                titleTypefaceFontRes =R.font.dinosaur_font,
                descriptionTypefaceFontRes =R.font.dinosaur_font,
                backgroundDrawable =R.drawable.ic_background_page
            )
        )
        addSlide(
            AppIntroFragment.createInstance(
                title ="Mode Player vs Player".uppercase(),
                description ="You can play with your friends in this mode".uppercase(),
                imageDrawable =R.drawable.ic_pvp,
                titleColorRes =R.color.black,
                descriptionColorRes =R.color.black,
                titleTypefaceFontRes = R.font.dinosaur_font,
                descriptionTypefaceFontRes = R.font.dinosaur_font,
                backgroundDrawable =R.drawable.ic_background_page

            )
        )
        addSlide(
            AppIntroFragment.createInstance(
                title ="Mode Player vs Computer".uppercase(),
                description ="You can play with computer in this mode".uppercase(),
                imageDrawable =R.drawable.ic_pvc,
                titleColorRes =R.color.black,
                descriptionColorRes =R.color.black,
                titleTypefaceFontRes = R.font.dinosaur_font,
                descriptionTypefaceFontRes =R.font.dinosaur_font,
                backgroundDrawable = R.drawable.ic_background_page
            )
        )


        addSlide(FormFragment())


    }


    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        finish()
    }


    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        if (true) {
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            finish()
            if(currentFragment is FormFragment){
                currentFragment.navigateToMenuGame()
            }


        }
    }

}