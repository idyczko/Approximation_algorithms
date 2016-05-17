public class LPProblemStandard {
	private boolean maximization;
	private boolean constraintInequalitiesLessThan;
	private boolean variableInequalitiesMoreThan;
	private int[] b;
	private int[] c;
	private int[][] A;

	public LPProblemStandard(boolean maximization, boolean constraintInequalitiesLessThan,
			boolean variableInequalitiesMoreThan, int[] b, int[] c, int[][] a) {
		this.maximization = maximization;
		this.constraintInequalitiesLessThan = constraintInequalitiesLessThan;
		this.variableInequalitiesMoreThan = variableInequalitiesMoreThan;
		this.b = b;
		this.c = c;
		this.A = a;
	}

	public static LPProblemStandard standarize(LPProblem lpProblem) {
		int[] oppositeC = calculateInitialC(lpProblem);
		int count = calculateARows(lpProblem);
		int[][] rowsA = new int[lpProblem.getA().length + count][lpProblem.getA()[0].length];
		int[] b = new int[lpProblem.getB().length + count];
		coverInequalitiesTypes(lpProblem, rowsA, b);
		coverUpperIntervalBounds(lpProblem, rowsA, b);
		count = countFreeVariables(lpProblem);
		int[] c = new int[oppositeC.length + count];
		int[][] A = new int[rowsA.length][rowsA[0].length + count];
		System.out.print(finalMatrices(lpProblem, rowsA, oppositeC, b, c, A));
		return new LPProblemStandard(true, true, true, b, c, A);
	}

	private static void coverUpperIntervalBounds(LPProblem lpProblem, int[][] rowsA, int[] b) {
		int index = 0;
		for (int i = 0; i < lpProblem.getVariables().length; i++) {
			if (lpProblem.getVariables()[i].isLowerBound() && lpProblem.getVariables()[i].isUpperBound()) {
				rowsA[b.length - 1 - index][i] = 1;
				b[b.length - 1 - index] = lpProblem.getVariables()[i].getUpperBoundValue();
				index++;
			}
		}
	}

	private static int countFreeVariables(LPProblem lpProblem) {
		int count;
		count = 0;
		for (Variables v : lpProblem.getVariables()) {
			if (!(v.isLowerBound() || v.isUpperBound())) {
				count++;
			}
		}
		return count;
	}

	private static int calculateARows(LPProblem lpProblem) {
		int count = 0;
		for (Inequalities i : lpProblem.getInequalities()) {
			if (i == Inequalities.EQUAL) {
				count++;
			}
		}
		for (Variables v : lpProblem.getVariables()) {
			if (v.isUpperBound() && v.isLowerBound()) {
				count++;
			}
		}
		return count;
	}

	private static int[] calculateInitialC(LPProblem lpProblem) {
		int[] oppositeC = new int[lpProblem.getC().length];
		if (lpProblem.isMaximization()) {
			oppositeC = lpProblem.getC();
		} else {
			for (int i = 0; i < lpProblem.getC().length; i++) {
				oppositeC[i] = -lpProblem.getC()[i];
			}
		}
		return oppositeC;
	}

	private static String finalMatrices(LPProblem lpProblem, int[][] constraintsA, int[] oppositeC, int[] b, int[] c, int[][] A) {
		String equalities = "";
		int bias = 0;
		for (int i = 0; i < lpProblem.getA()[0].length; i++) {
			equalities+="x"+(i+1)+" = ";
			if (lpProblem.getVariables()[i].isLowerBound()) {
				for (int j = 0; j <constraintsA.length; j++) {
					A[j][i + bias] = constraintsA[j][i];
					b[j] -= A[j][i + bias] * lpProblem.getVariables()[i].getLowerBoundValue();
				}
				c[i + bias] = oppositeC[i];
				equalities+="x"+(i+bias+1)+(lpProblem.getVariables()[i].getLowerBoundValue()<0?"-":"+")+Math.abs(lpProblem.getVariables()[i].getLowerBoundValue())+"\n";
			}
			else if (lpProblem.getVariables()[i].isUpperBound() && !lpProblem.getVariables()[i].isLowerBound()) {
				for (int j = 0; j < constraintsA.length; j++) {
					A[j][i + bias] = -constraintsA[j][i];
					b[j] += A[j][i + bias] * lpProblem.getVariables()[i].getUpperBoundValue();
				}
				c[i + bias] = -oppositeC[i];
				equalities+="-x"+(i+bias+1)+(lpProblem.getVariables()[i].getUpperBoundValue()<0?"-":"+")+Math.abs(lpProblem.getVariables()[i].getUpperBoundValue())+"\n";
			}
			else if (!lpProblem.getVariables()[i].isUpperBound() && !lpProblem.getVariables()[i].isLowerBound()) {
				for (int j = 0; j < constraintsA.length; j++) {
					A[j][i + bias] = constraintsA[j][i];
					A[j][i + bias + 1] = -constraintsA[j][i];
				}
				c[i + bias] = oppositeC[i];
				c[i + bias + 1] = -oppositeC[i];
				equalities+="x"+(i+bias+1)+" - x"+(i+bias+2)+"\n";
				bias++;
			}
			else{
				equalities+="x"+i+"\n";
			}

		}
		return equalities;
	}

	private static int coverInequalitiesTypes(LPProblem lpProblem, int[][] constraintsA, int[] b) {
		int bias = 0;
		for (int i = 0; i < lpProblem.getB().length; i++) {
			switch (lpProblem.getInequalities()[i]) {
			case LOWER_EQUAL:
				for (int j = 0; j < lpProblem.getA()[0].length; j++) {
					constraintsA[i + bias][j] = lpProblem.getA()[i][j];
				}
				b[i + bias] = lpProblem.getB()[i];
				break;
			case GREATER_EQUAL:
				for (int j = 0; j < lpProblem.getA()[0].length; j++) {
					constraintsA[i + bias][j] = -lpProblem.getA()[i][j];
				}
				b[i + bias] = -lpProblem.getB()[i];
				break;
			case EQUAL:
				for (int j = 0; j < lpProblem.getA()[0].length; j++) {
					constraintsA[i + bias][j] = lpProblem.getA()[i][j];
				}
				b[i + bias] = lpProblem.getB()[i];
				for (int j = 0; j < lpProblem.getA()[0].length; j++) {
					constraintsA[i + bias + 1][j] = -lpProblem.getA()[i][j];
				}
				b[i + bias + 1] = -lpProblem.getB()[i];
				bias++;
				break;
			default:
				break;
			}

		}
		return bias;
	}

	public LPProblemStandard generateDual() {
		return new LPProblemStandard(!maximization, !constraintInequalitiesLessThan, variableInequalitiesMoreThan, c, b,
				MatrixLib.transpose(A));
	}

	public void display() {
		System.out.println();
		String objectiveFunction = c[0] + "*x1";
		for (int i = 1; i < this.c.length; i++) {
			objectiveFunction += (c[i]>=0?" + ":" - ") + Math.abs(c[i]) + "*x" + (i + 1);
		}
		objectiveFunction += " ----> ";
		objectiveFunction += maximization ? "max" : "min";
		System.out.println(objectiveFunction);

		for (int i = 0; i < this.A.length; i++) {
			String constraint = A[i][0] + "*x1";
			for (int j = 1; j < this.A[i].length; j++) {
				constraint += (A[i][j]>=0?" + ":" - ") + Math.abs(A[i][j]) + "*x" + (j + 1);
			}
			constraint += constraintInequalitiesLessThan ? " <= " : " >= ";
			constraint += b[i];
			System.out.println(constraint);
		}
		System.out.println("x" + (variableInequalitiesMoreThan ? " >= 0" : " <= 0"));
	}

}
