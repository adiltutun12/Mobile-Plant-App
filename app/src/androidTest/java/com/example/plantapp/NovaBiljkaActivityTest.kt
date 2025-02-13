package com.example.plantapp

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.plantapp.view.NovaBiljkaActivity
import org.hamcrest.Matcher
import org.hamcrest.Matchers.equalToIgnoringCase
import org.hamcrest.Matchers.hasToString
import org.hamcrest.Matchers.`is`
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.function.Predicate
import java.util.function.Predicate.not

@RunWith(AndroidJUnit4::class)
class NovaBiljkaActivityTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(NovaBiljkaActivity::class.java)

    @Test
    fun testFieldValidation() {
        // Postavljanje scenarija: polja nisu pravilno popunjena
        Espresso.onView(withId(R.id.nazivET)).perform(typeText("Borovnica"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.porodicaET)).perform(typeText("Borovnicae"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.medicinskoUpozorenjeET)).perform(typeText(""), closeSoftKeyboard())

        //Klik na dugme za dodavanje biljke
        Espresso.onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())


        //Očekujemo da se prikaže greška na polju za medicinsko upozorenje
        Espresso.onView(withId(R.id.medicinskoUpozorenjeET))
            .check(matches(hasErrorText("Unesite medicinsko upozorenje")))
    }
    //Test za provjeravanje duzine teksta
    @Test
    fun testNazivFieldValidation_tooShort() {
        Espresso.onView(withId(R.id.nazivET)).perform(typeText("A"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        Espresso.onView(withId(R.id.nazivET)).check(matches(hasErrorText("Dužina teksta mora biti između 2 i 20 znakova")))
    }
    //Test za provjeravanje duzine teksta
    @Test
    fun testNazivFieldValidation_tooLong() {
        Espresso.onView(withId(R.id.nazivET)).perform(clearText(), typeText("Prevelikonazivbiljkekojenestanemoulistu"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        Espresso.onView(withId(R.id.nazivET)).check(matches(hasErrorText("Dužina teksta mora biti između 2 i 20 znakova")))
    }

}
