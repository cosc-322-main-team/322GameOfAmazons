package ubc.cosc322;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayTester {
  Hashtable<Integer, List<Integer>> cache = new Hashtable<>();

  public void testAsListOverloaded(int n) {
    ArrayList<ArrayList<Integer>> tempArray = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      tempArray.add(new ArrayList<>(Arrays.asList(1, 2)));
    }
  }

  public void testAsList(int n) {
    ArrayList<List<Integer>> tempArray = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      tempArray.add(Arrays.asList(1, 2));
    }
  }

  public void testStream(int n) {
    ArrayList<List<Integer>> tempArray = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      tempArray.add(Stream.of(1, 2).collect(Collectors.toList()));
    }
  }

  public void testCache(int n) {
    for (int i = 0; i < 100; i++) {
      cache.put(i, Arrays.asList(1, 2));
    }

    ArrayList<List<Integer>> tempArray = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      tempArray.add(cache.get(0));
    }
  }
}
