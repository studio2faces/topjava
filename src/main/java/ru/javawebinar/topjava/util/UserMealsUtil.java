package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        Map<LocalDate, Integer> dailyCalories = new HashMap<>();

        for (UserMeal meal : meals) {
            if (dailyCalories.containsKey(meal.getDate())) {
                int kcal = dailyCalories.get(meal.getDate());
                kcal = kcal + meal.getCalories();
                dailyCalories.put(meal.getDate(), kcal);
            } else dailyCalories.put(meal.getDate(), meal.getCalories());
        }

        for (UserMeal meal : meals) {
            if (isSatisfiesTheTimeCondition(meal.getTime(), startTime, endTime)) {

                int kcalEaten = dailyCalories.get(meal.getDate());
                if (kcalEaten > caloriesPerDay) {
                    userMealWithExcessList.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), true));
                } else {
                    userMealWithExcessList.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), false));
                }
            }
        }
        return userMealWithExcessList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams

        Map<LocalDate, Integer> dailyCalories = meals.stream()
                .collect(Collectors.toMap(
                        UserMeal::getDate,
                        UserMeal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(userMeal -> isSatisfiesTheTimeCondition(userMeal.getTime(), startTime, endTime))
                .map(userMeal -> new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
                        dailyCalories.get(userMeal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static boolean isSatisfiesTheTimeCondition(LocalTime mealsTime, LocalTime startTime, LocalTime endTime) {
        return mealsTime.equals(startTime) || mealsTime.isAfter(startTime) && mealsTime.isBefore(endTime);
    }
}