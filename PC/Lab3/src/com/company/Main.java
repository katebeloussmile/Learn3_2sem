package com.company;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class Main {
    public static void main(String args[]) {
        //переменные размера для прогона
        int size = 5;
        int maxValue = 10;
        //генерация массивов
        int[] array = new Random().ints(size,1,maxValue).toArray();
        long[] array_long1 = new Random().longs(size, 1, maxValue).toArray();
        long[] array_long2 = new Random().longs(size, 1, maxValue).toArray();
        System.out.println(Arrays.toString(array));
        System.out.println(Arrays.toString(array_long1));
        System.out.println(Arrays.toString(array_long2));
        int checksum = controlSum(array);
        System.out.println("Контрольная сума: " + checksum);
        MinMax<Long> minmax = minmax(array_long1);
        System.out.println("Минимальное значение в массиве: " + minmax.Min.Element + ". Индекс минимального элемента: " + minmax.Min.Index);
        System.out.println("Максимальное значение в массиве: " + minmax.Max.Element + ". Индекс максимального элемента: " + minmax.Max.Index);
        System.out.println(modemedian(array).toString());
        System.out.println("Sum of elements in array is: " + arraySum(array));
        System.out.println(arrayMultiply(array_long1, array_long1).toString());
    }

    //контрольная сумма на массиве int
    public static int controlSum(int[] array){
        AtomicInteger controlSum = new AtomicInteger();
        IntStream.of(array).parallel().forEach(Element -> {
            int previous, next;
            do{
                previous = controlSum.get();
                //prev XOR this
                next = previous ^ Element;
            }while(!controlSum.compareAndSet(previous, next));
        });
        return controlSum.get();
    }

    //минимальное максимальное значение на массиве long
    public static MinMax<Long> minmax(long[] array){
        int size = array.length;
        Item<Long> min = new Item<>(array[0], 0);
        Item<Long> max = new Item<>(array[0], 0);

        AtomicReference<Item<Long>> atomicMin = new AtomicReference<>(min);
        AtomicReference<Item<Long>> atomicMax = new AtomicReference<>(max);

        IntStream.range(0, size).parallel().forEach(index -> {
            if(atomicMin.get().Element > array[index]){
                Item<Long> previous;
                Item<Long> updated;
                do{
                    previous = atomicMin.get();
                    updated = new Item<Long>(array[index], index);
                    if(previous.Element < updated.Element){
                        break;
                    }
                }while(!atomicMin.weakCompareAndSet(previous, updated));
            }
            if(atomicMax.get().Element < array[index]){
                Item<Long> previous;
                Item<Long> updated;
                do{
                    previous = atomicMax.get();
                    updated = new Item<Long>(array[index], index);
                    if(previous.Element > updated.Element){
                        break;
                    }
                }while(!atomicMax.weakCompareAndSet(previous, updated));
            }
        });

        return new MinMax<Long>(atomicMin.get(), atomicMax.get());
    }

    public static AtomicLongArray arrayMultiply(long[] arr1, long[] arr2){
        if(arr1.length != arr1.length)
            return null;
        AtomicLongArray multiplicate = new AtomicLongArray(new long[arr1.length]);
        IntStream.range(0, arr1.length).parallel().forEach(i -> multiplicate.set(i, arr1[i] * arr2[i]));
        return multiplicate;
    }

    public static Stats<Integer> modemedian(int[] array){
        int[] cloned = new int[array.length];
        System.arraycopy(array, 0, cloned, 0, array.length);
        Arrays.parallelSort(cloned);
        Stats<Integer> statMetrics = new Stats<>(array[0], 0);
        int median, median_index = array.length / 2;
        if(array.length % 2 == 1){
            median = cloned[array.length / 2];
        }else {
            median = (cloned[array.length / 2] + cloned[array.length / 2 + 1]) / 2;
        }
        median_index = IntStream.range(0, array.length).filter(i -> array[i] == median).findFirst().orElse(-1);
        statMetrics.Median = new Item<Integer>(median, median_index);
        ConcurrentHashMap<Item<Integer>, LongAdder> map = new ConcurrentHashMap<>();
        IntStream.range(0, array.length).parallel().forEach(index -> {
            Item<Integer> element = new Item<Integer>(array[index], index);
            map.computeIfAbsent(element, key -> new LongAdder()).increment();
        });
        long max_value = Collections.max(map.entrySet(), Comparator.comparingLong(e ->
                e.getValue().longValue())).getValue().longValue();
        ArrayList<Item<Integer>> modes = new ArrayList<>();
        map.entrySet().stream().parallel().forEach(keyValuePair -> {
            if(keyValuePair.getValue().longValue() == max_value){
                modes.add(keyValuePair.getKey());
            }
        });
        statMetrics.Modes = new ArrayList(modes);
        return statMetrics;
    }

    public static int arraySum(int[] input){
        AtomicInteger parallelSum = new AtomicInteger();
        IntStream.of(input).parallel().forEach(x -> {
            int previous, updated;
            do{
                previous = parallelSum.get();
                updated = previous + x;
            }while(!parallelSum.compareAndSet(previous, updated));
        });
        return parallelSum.get();
    }
}