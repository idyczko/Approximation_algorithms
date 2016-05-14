
public class Main {

	public static void main(String[] args) {
		int[] b = { -3, 5, 2 };
		int[] c = { 3, -1, 0, 0 };
		int[][] A = { { -1, 6, -1, 1 }, { 0, 7, 0, 1 }, { 0, 0, 1, 1 } };
		Inequalities[] inequalities = { Inequalities.GREATER_EQUAL, Inequalities.EQUAL, Inequalities.LOWER_EQUAL };
		Variables variable1 = new Variables(false, false, 0, 0);
		Variables variable2 = new Variables(true, false, -1, 0);
		Variables variable3 = new Variables(false, true, 0, 5);
		Variables variable4 = new Variables(true, true, -2, 2);
		Variables[] variables = { variable1, variable2, variable3, variable4 };
		LPProblem testProblem = new LPProblem(false, inequalities, variables, c, b, A);
		testProblem.display();
		LPProblemStandard standarizedTestProblem = LPProblemStandard.standarize(testProblem);
		LPProblemStandard dualStandarizedTestProblem = standarizedTestProblem.generateDual();
		standarizedTestProblem.display();
		dualStandarizedTestProblem.display();
	}

}
