import java.util.HashMap;

public class PermutationsGeneratingClass {

	public static HashMap<Integer, Long> generate(int size) {
		HashMap<Integer, Long> histogramMap = new HashMap<Integer, Long>();
		int[] elementsSet = new int[size];
		for (int i = 0; i < size; i++) {
			elementsSet[i] = i;
		}
		perm(histogramMap, elementsSet, elementsSet.length-1);
		return histogramMap;
	}

	public static void perm(HashMap<Integer, Long> map,int[] elementsSet, int k) {
		if (k == 0) {
			//Main.printTab(elementsSet);
			int comp=quicksort(elementsSet, 0, elementsSet.length-1);
			if(map.containsKey(comp)){
				map.put(comp, map.get(comp)+1);
				return;
			}
			map.put(comp, 1L);
			return;
		}
		for (int i = 0; i <= k; i++) {
			swap(elementsSet, i, k);
			perm(map, elementsSet, k-1);
			swap(elementsSet, i, k);
		}
	}

	public static int quicksort(int[] permutation, int lowerBound, int upperBound) {
		int comparisons = 0;
		if (lowerBound >= upperBound) {
			return comparisons;
		}
		int i = lowerBound - 1;
		int j = upperBound;
		int v = permutation[upperBound];
		while (true) {
			while (permutation[++i] < v)
				comparisons++;
			comparisons++;
			while (v < permutation[--j]) {
				comparisons += 2;
				if (j == lowerBound) {
					comparisons--;
					break;
				}
			}
			comparisons += 2;
			if (i >= j)
				break;
			swap(permutation, i, j);
		}
		swap(permutation, i, upperBound);
		comparisons += quicksort(permutation, lowerBound, i - 1);
		comparisons += quicksort(permutation, i + 1, upperBound);
		return comparisons;
	}

	private static void swap(int[] permutation, int i, int j) {
		int t;
		t = permutation[i];
		permutation[i] = permutation[j];
		permutation[j] = t;
	}

}
