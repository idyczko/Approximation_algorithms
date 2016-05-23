import java.math.BigInteger;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		int[][] clauses = { { -1, -1, -1, -1, -1 }, { 1, 1, 1, 1, 1 }, { 1, -1, -1, -1, -1 }, { 1, 1, 1, -1, -1 } };
		MaxSatProblem problem = new MaxSatProblem(clauses);
		problem.display();
		boolean[] randomDecision = problem.randomizedAlgorithm();
		int ful = problem.numberOfSatisfiedClauses(randomDecision);

		System.out.println();
		for (boolean bool : randomDecision) {
			System.out.print(bool + " ");
		}
		System.out.println();
		System.out.println(ful);

		boolean[] derandomizedDecision = problem.derandomizedAlgorithm();
		int ful2 = problem.numberOfSatisfiedClauses(derandomizedDecision);

		System.out.println();
		for (boolean bool : derandomizedDecision) {
			System.out.print(bool + " ");
		}
		System.out.println();
		System.out.println(ful2);

		test(100, 2, 5);
	}

	public static void test(int tests, int variables, int clausesNumber) {
		Random random = new Random();
		BigInteger count = BigInteger.ZERO;
		int[][] clauses = new int[clausesNumber][variables];
		for (int i = 0; i < tests; i++) {
			for (int x = 0; x < clausesNumber; x++) {
				for (int y = 0; y < variables; y++) {
					int rand = random.nextInt(3);
					switch (rand) {
					case 0:
						clauses[x][y] = -1;
						break;
					case 1:
						clauses[x][y] = 0;
						break;
					case 2:
						clauses[x][y] = 1;
						break;
					default:
						break;
					}
				}
			}
			MaxSatProblem test = new MaxSatProblem(clauses);
			test.display();
			System.out.println("Random algorithm satisfied clauses:");
			System.out.println(test.numberOfSatisfiedClauses(test.randomizedAlgorithm()));
			System.out.println("Derandomized algorithm satisfied clauses:");
			int derandomizedResult = test.numberOfSatisfiedClauses(test.derandomizedAlgorithm());
			System.out.println(derandomizedResult);
			count = count.add(BigInteger.valueOf(derandomizedResult));
		}
		System.out.println("In " + tests + " tests of problem with " + clausesNumber + " clauses and " + variables
				+ " variables, average " + count.divide(BigInteger.valueOf(tests)) + " fulfilled");
	}

}
