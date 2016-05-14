
public class LPProblem {
	private boolean maximization;
	private Inequalities[] inequalities;
	private Variables[] variables;
	private int[] c;
	private int[] b;
	private int[][] A;
	
	public LPProblem(boolean maximization, Inequalities[] inequalities, Variables[] variables, int[] c, int[] b,
			int[][] a) {
		super();
		this.maximization = maximization;
		this.inequalities = inequalities;
		this.variables = variables;
		this.c = c;
		this.b = b;
		this.A = a;
	}
	
	public void display() {
		System.out.println();
		String objectiveFunction = c[0] + "*x1";
		for (int i = 1; i < this.c.length; i++) {
			objectiveFunction += " + " + c[i] + "*x" + (i + 1);
		}
		objectiveFunction += "---->";
		objectiveFunction += maximization ? "max" : "min";
		System.out.println(objectiveFunction);

		for (int i = 0; i < this.A.length; i++) {
			String constraint = A[i][0] + "*x1";
			for (int j = 1; j < this.A[i].length; j++) {
				constraint += " + " + A[i][j] + "*x" + (j + 1);
			}
			switch (inequalities[i]) {
			case LOWER_EQUAL:
				constraint+=" <= "; 
				break;
			case GREATER_EQUAL:
				constraint+=" >= "; 
				break;
			case EQUAL:
				constraint+=" = "; 
				break;	
			default:
				break;
			}
			
			constraint += b[i];
			System.out.println(constraint);
		}
		for(int i=0; i<this.variables.length;i++){
			String variables = "";
			if(this.variables[i].isLowerBound()){
				variables+=this.variables[i].getLowerBoundValue()+" <= ";
			}
			variables+="x"+(i+1);
			if(this.variables[i].isUpperBound()){
				variables+=" <= "+this.variables[i].getUpperBoundValue();
			}
			if(!(this.variables[i].isLowerBound()||this.variables[i].isUpperBound())){
				variables+=" is free";
			}
			System.out.println(variables);
		}
	}

	public boolean isMaximization() {
		return maximization;
	}

	public Inequalities[] getInequalities() {
		return inequalities;
	}

	public Variables[] getVariables() {
		return variables;
	}

	public int[] getC() {
		return c;
	}

	public int[] getB() {
		return b;
	}

	public int[][] getA() {
		return A;
	}
	
	
}
