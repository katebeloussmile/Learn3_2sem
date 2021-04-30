package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //Генерация массивов
        int size = 40;
        int max_element = 100;
        var array1 = generateArray(1, max_element, size);
        var array2 = generateArray(1, max_element, size);
        System.out.println("Первый массив: \n" + array1.toString());
        System.out.println("Второй массив: \n" + array2.toString());
        //Вызов асинхронных методов
        var array1Future = removeElsAboveHalfMax(array1);
        var array2Future = divisibleByFive(array2);
        var combinedResult = mergeAndSort(array1Future, array2Future);
        //Вывод результатов выполнения
        System.out.println("Первый массив без элементов array1[i]<0.5*max: \n" + array1Future.get().toString());
        System.out.println("Второй массив без элементов не кратных 5: \n" + array2Future.get().toString());
        System.out.println("Комбинированый массив с одинаковыми элементами: \n" + combinedResult.get().toString());
    }

    //функция генерации массива
    private static List<Integer> generateArray(int min, int max, int size){
        var random = new Random();
        return random.ints(size, min, max + 1).boxed().toList();
    }

    //Убираем элементы больше чем половина максимального
    private static CompletableFuture<List<Integer>> removeElsAboveHalfMax
    (List<Integer> array)
    {
        return CompletableFuture.supplyAsync(() -> array).thenApplyAsync((list) -> {
            var max = Collections.max(list);
            return list.stream().filter(x -> x > 0.5f * max).toList();
        });
    }

    //Отавляем только элементы кратные 5
    private static CompletableFuture<List<Integer>> divisibleByFive
    (List<Integer> array){
        return CompletableFuture.supplyAsync(() -> array).thenApplyAsync((list) -> {
            return list.stream().filter(x -> x % 5 == 0).toList();
        });
    }

    //Соединяем массивы, оставляем только общие элементы
    private static CompletableFuture<List<Integer>> mergeAndSort(CompletableFuture<List<Integer>> array1, CompletableFuture<List<Integer>> array2)
    {
        var results = Stream.of(array1, array2).map(CompletableFuture::join).toList();
        var joinedResults = results.stream().flatMap(List::stream).toList();
        return CompletableFuture.supplyAsync(() -> joinedResults).thenApplyAsync((list) -> {
            var filteredResult = new ArrayList<Integer>();
            list.forEach(x -> {
                if(results.stream().allMatch(l -> l.stream().anyMatch(i -> i == x))){
                    filteredResult.add(x);
                }
            });
            Collections.sort(filteredResult);
            return filteredResult;
        });
    }
}
