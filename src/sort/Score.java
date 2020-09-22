/**
 * Author: SafeFoods
 * Revised: April 8, 2020
 * 
 * Description: This module is for calculating the SafeFood score of a 
 * restaurant given their violations and inspections. It uses
 * the score, grades, and points to return a calculated value
 * that represents the overall status of the restaurant.
 */

package sort;

import java.util.ArrayList;

import search.Inspection;
import search.Violation;

/**
 * @brief Calculate the SafeFood score of a restaurant given violations and inspections.
 */
public class Score {

    /**
     * @brief Given a series of restaurant violations and inspections, use their score,
     * points, and grade to return a calculated SafeFoods score.
     * @param v The arraylist of violation of the restaurant.
     * @param i The arraylist inspection of the restaurant.
     * @return The SafeFood calculated score.
     */
    public static int safeFoodScore(ArrayList<Violation> violationList, ArrayList<Inspection> inspectionList) {
        int safeFoodScore = 0;
        
        for(Violation v : violationList) {
            //Violation score
            int vScore = v.getScore();

            //Violation grade
            int vGrade = gradeToScore(v.getGrade());

            //Violation point
            int vPoint = v.getPoints();

            safeFoodScore += (vScore + vGrade + vPoint)/3;
        }

        for(Inspection i : inspectionList) {
            //Inspection score
            int iScore = i.getScore();

            //Inspection grade
            int iGrade = gradeToScore(i.getGrade());

            safeFoodScore += (iScore + iGrade)/2;
        }

        //Scale based on number of violations and inspections
        safeFoodScore /= (violationList.size() + inspectionList.size());

        return safeFoodScore;
    }

    /**
     * @brief Return a score given a letter grade based on the SafeFoods scale.
     * @param grade The letter grade to convert.
     * @return The score based on the letter grade.
     */
    private static int gradeToScore(String grade) {
        if(grade.equals("A")){
            return 100;
        } else if(grade.equals("B")){
            return 80;
        } else if(grade.equals("C")){
            return 60;
        } else {
            return 50;
        }
    }
}