import java.util.Random;

public class MaxSatProblem {

	private int[][] clauses;

	public MaxSatProblem(int[][] clauses) {
		this.clauses = clauses;
	}

	public boolean[] randomizedAlgorithm() {
		Random random = new Random();
		boolean[] decision = new boolean[clauses[0].length];
		for (int i = 0; i < decision.length; i++) {
			decision[i] = random.nextBoolean();
		}
		return decision;
	}
	
	public boolean[] derandomizedAlgorithm(){
		boolean[] decision = new boolean[clauses[0].length];
		for (int i = 0; i < decision.length; i++) {
			if(calculateExpected(decision, i, true)>=calculateExpected(decision,i,false)){
				decision[i]=true;
			}
			else{
				decision[i]=false;
			}
		}
		return decision;
	}

	private int calculateExpected(boolean[] decision, int i, boolean b) {
		decision[i] = b;
		return numberOfSatisfiedClauses(decision,i+1);
	}
	public int numberOfSatisfiedClauses(boolean[] decision) {
		return numberOfSatisfiedClauses(decision, decision.length);
	}

	public int numberOfSatisfiedClauses(boolean[] decision, int consideredLiterals) {
		int count = 0;
		boolean[] fulfillment = fulfilledClauses(decision, consideredLiterals);
		for (boolean ful : fulfillment) {
			if (ful)
				count++;
		}
		return count;
	}
	
	public boolean[] fulfilledClauses(boolean[] decision, int consideredLiterals) {
		boolean[] fulfillment = new boolean[clauses.length];
		outside: for (int i = 0; i < clauses.length; i++) {
			for (int j = 0; j < consideredLiterals; j++) {
				if (!literalStateNegatesFulfillment(clauses[i][j], decision[j])) {
					fulfillment[i] = true;
					continue outside;
				}
			}
			fulfillment[i] = false;
		}
		return fulfillment;
	}

	public void display() {
		for (int[] clause : clauses) {
			System.out.println();
			int i = 1;
			for (int literal : clause) {
				switch (literal) {
				case 1:
					System.out.print("P" + i + " ");
					break;
				case -1:
					System.out.print("~P" + i + " ");
					break;
				case 0:
					break;
				default:
					break;
				}
				i++;
			}
		}
	}

	private boolean literalStateNegatesFulfillment(int i, boolean b) {
		if ((i == 1 && b) || (i == -1 && !b) || i == 0)
			return false;
		return true;
	}

}
