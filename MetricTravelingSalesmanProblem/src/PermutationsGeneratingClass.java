import java.util.ArrayList;
import java.util.Arrays;

public class PermutationsGeneratingClass {

	public static ArrayList<ArrayList<Integer>> generate(ArrayList<Integer> elements) {
		ArrayList<ArrayList<Integer>> permutations = new ArrayList<ArrayList<Integer>>();
		Integer[] elementsTab =elements.toArray(new Integer[elements.size()]);
		perm(permutations, elementsTab, elements.size()-1);
		return permutations;
	}

	public static void perm(ArrayList<ArrayList<Integer>> permutations,Integer[] elements, int k) {
		if (k == 0) {
			permutations.add(new ArrayList<Integer>(Arrays.asList(elements)));
			return;
		}
		for (int i = 0; i <= k; i++) {
			swap(elements, i, k);
			perm(permutations,elements, k-1);
			swap(elements, i, k);
		}
	}

	private static void swap(Integer[] permutation, int i, int j) {
		int t;
		t = permutation[i];
		permutation[i] = permutation[j];
		permutation[j] = t;
	}

}
