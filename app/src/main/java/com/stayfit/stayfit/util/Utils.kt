package com.stayfit.stayfit.util

import android.content.Context
import com.stayfit.stayfit.R
import com.stayfit.stayfit.model.GClass
import com.stayfit.stayfit.model.InnerClass

object Utils {

    var classesList = arrayListOf<GClass>()

    fun createList(context : Context) {
        //Leg day exercises
        val legday1 = InnerClass("Deadlift","5","8","60 secs",null, R.drawable.legday1)
        val legday2 = InnerClass("Leg Press","5","8","60 secs",null, R.drawable.legday2)
        val legday3 = InnerClass("Seated hamstring curl","4","10","30 secs",null, R.drawable.legday3)
        val legday4 = InnerClass("Seated leg extension ","4","10","60 secs",null, R.drawable.legday4)
        val legday5 = InnerClass("Dumbbell lunge","3","8","30 secs",null, R.drawable.legday5)
        val legday6 = InnerClass("Dumbbell squat","3","15","60 secs",null, R.drawable.legday6)
        //Chest day exercises
        val chestday1 = InnerClass("Bench Press","5","5","60 secs",null, R.drawable.chestday1)
        val chestday2 = InnerClass("Incline Bench Press","5","5","60 secs",null, R.drawable.chestday2)
        val chestday3 = InnerClass("Incline Dumbbell Bench Press","4","8","20 secs",null, R.drawable.chestday3)
        val chestday4 = InnerClass("Incline Dumbbell Flye","4","10","20 secs",null, R.drawable.chestday4)
        val chestday5 = InnerClass("Press-up","4","12","60 secs",null, R.drawable.chestday5)
        //Back Workout exercises
        val backWorkout1  = InnerClass("Pull-up","5","5","30 secs",null, R.drawable.backworkout1)
        val backWorkout2 = InnerClass("Hammer-grip pull-up","5","5","60 secs",null, R.drawable.backworkout2)
        val backWorkout3 = InnerClass("Prone dumbbell row ","4","8","30 secs",null, R.drawable.backworkout3)
        val backWorkout4 = InnerClass("Prone dumbbell flye","4","12","60 secs",null, R.drawable.backworkout4)
        val backWorkout5 = InnerClass("Underhand lat pull-down","4","12","60 secs",null, R.drawable.backworkout5)
        val backWorkout6 = InnerClass("Seated row","4","12","60 secs",null, R.drawable.backworkout6)
        //BEST ABS WORKOUT exercises
        val bestAbsWorkout1  = InnerClass("Dumbbell crunch",null,"10","10 secs",null, R.drawable.bestabsworkout1)
        val bestAbsWorkout2 = InnerClass("Tuck and crunch ",null,"15","10 secs",null, R.drawable.bestabsworkout2)
        val bestAbsWorkout3 = InnerClass("Modified V-sit ",null,"15","10 secs",null, R.drawable.bestabsworkout3)
        val bestAbsWorkout4 = InnerClass("Crunch",null,"15","10 secs",null, R.drawable.bestabsworkout4)
        val bestAbsWorkout5 = InnerClass("Hanging leg raise",null,"15","10 secs",null, R.drawable.bestabsworkout5)
        val bestAbsWorkout6 = InnerClass("Hanging knee raise twist",null,"15","10 secs",null, R.drawable.bestabsworkout6)
        val bestAbsWorkout7 = InnerClass("Hanging knee raise",null,"15","10 secs",null, R.drawable.bestabsworkout7)
        val bestAbsWorkout8 = InnerClass("Plank",null,null,null,"max", R.drawable.bestabsworkout8)

        val legdayInnerList = arrayListOf(legday1,legday2,legday3,legday4,legday5,legday6)
        val legday = GClass("LEG DAY",context.resources.getString(R.string.leg_day_desc),legdayInnerList,0)
        val chestdayInnerList = arrayListOf(chestday1,chestday2,chestday3,chestday4,chestday5)
        val chestday = GClass("CHEST DAY",context.resources.getString(R.string.chest_day_desc),chestdayInnerList,1)
        val backWorkOutInnerList = arrayListOf(backWorkout1,backWorkout2,backWorkout3,backWorkout4,backWorkout5,backWorkout6)
        val backWorkOut = GClass("BACK WORKOUT",context.resources.getString(R.string.back_workout_day_desc),backWorkOutInnerList,2)
        val bestAbsWorkoutInnerList = arrayListOf(bestAbsWorkout1,bestAbsWorkout2,bestAbsWorkout3,bestAbsWorkout4,bestAbsWorkout5,bestAbsWorkout6,bestAbsWorkout7,bestAbsWorkout8)
        val bestAbsWorkout = GClass("BEST ABS WORKOUT","",bestAbsWorkoutInnerList,3)
        classesList = arrayListOf(legday,chestday,backWorkOut,bestAbsWorkout)
    }
}