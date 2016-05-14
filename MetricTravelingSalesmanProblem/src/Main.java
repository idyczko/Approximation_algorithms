import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {

		try {
			performanceTests();
			//approximationComparison();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void approximationComparison() throws IOException {
		int maxVertices = 40;
		int minVertices = 4;
		int testsNumber = 50;
		int edgeCostBound = 500;
		DecimalFormat decimalFormat = new DecimalFormat();
		decimalFormat.setMaximumFractionDigits(2);
		File times_file = new File("C:\\Users\\Igor\\Desktop\\time_file_2.txt");
		File solution_file = new File("C:\\Users\\Igor\\Desktop\\solution_file_2.txt");
		if (!times_file.exists()) {
			times_file.createNewFile();
		}
		if (!solution_file.exists()) {
			solution_file.createNewFile();
		}
		FileWriter fw_times = new FileWriter(times_file.getAbsoluteFile());
		BufferedWriter bw_times = new BufferedWriter(fw_times);
		FileWriter fw_solution = new FileWriter(solution_file.getAbsoluteFile());
		BufferedWriter bw_solution = new BufferedWriter(fw_solution);
		for (int vertices = minVertices; vertices <= maxVertices; vertices++) {
			for (int test = 0; test < testsNumber; test++) {
				Graph testGraph = Graph.generateMetricCompleteGraph(vertices, edgeCostBound);
				long startTime = System.nanoTime();
				ArrayList<Integer> twoApproxTrace = new ArrayList<Integer>(testGraph.getTwoApproximationPath(0));
				long twoApproxTime = System.nanoTime() - startTime;
				int twoApproxCost = testGraph.getTraceCost(twoApproxTrace);
				startTime = System.nanoTime();
				ArrayList<Integer> threeOverTwoApproxTrace = new ArrayList<Integer>(
						testGraph.getThreeOverTwoApproximationPath(0));
				long threeOverTwoApproxTime = System.nanoTime() - startTime;
				int threeOverTwoApproxCost = testGraph.getTraceCost(threeOverTwoApproxTrace);

				//bw_times.write(twoApproxTime + "\t" + threeOverTwoApproxTime + "\t");
				//bw_solution.write(twoApproxCost + "\t" + threeOverTwoApproxCost + "\t");
				System.out.println("Test no. " + (test + 1) + " for " + vertices + " vertices:");
				System.out.println("Solution cost for 3/2-approx: " + threeOverTwoApproxCost + " found in "
						+ threeOverTwoApproxTime + " nanoseconds");
				System.out.println(
						"Solution cost for 2-approx: " + twoApproxCost + " found in " + twoApproxTime + " nanoseconds");
			}
		}
		bw_solution.close();
		bw_times.close();
	}

	public static void performanceTests() throws IOException {
		int maxVertices = 10;
		int minVertices = 4;
		int testsNumber = 50;
		int edgeCostBound = 500;
		DecimalFormat decimalFormat = new DecimalFormat();
		decimalFormat.setMaximumFractionDigits(2);
		File times_file = new File("C:\\Users\\Igor\\Desktop\\time_file_1.txt");
		File solution_file = new File("C:\\Users\\Igor\\Desktop\\solution_file_1.txt");
		if (!times_file.exists()) {
			times_file.createNewFile();
		}
		if (!solution_file.exists()) {
			solution_file.createNewFile();
		}
		FileWriter fw_times = new FileWriter(times_file.getAbsoluteFile());
		BufferedWriter bw_times = new BufferedWriter(fw_times);
		FileWriter fw_solution = new FileWriter(solution_file.getAbsoluteFile());
		BufferedWriter bw_solution = new BufferedWriter(fw_solution);
		for (int vertices = minVertices; vertices <= maxVertices; vertices++) {
			for (int test = 0; test < testsNumber; test++) {
				Graph testGraph = Graph.generateMetricCompleteGraph(vertices, edgeCostBound);
				long startTime = System.nanoTime();
				ArrayList<Integer> twoApproxTrace = new ArrayList<Integer>(testGraph.getTwoApproximationPath(0));
				long twoApproxTime = System.nanoTime() - startTime;
				int twoApproxCost = testGraph.getTraceCost(twoApproxTrace);
				startTime = System.nanoTime();
				ArrayList<Integer> threeOverTwoApproxTrace = new ArrayList<Integer>(
						testGraph.getThreeOverTwoApproximationPath(0));
				long threeOverTwoApproxTime = System.nanoTime() - startTime;
				int threeOverTwoApproxCost = testGraph.getTraceCost(threeOverTwoApproxTrace);
				startTime = System.nanoTime();
				ArrayList<Integer> bruteforceTrace = new ArrayList<Integer>(testGraph.getBruteForceSolution(0));
				long bruteforceTime = System.nanoTime() - startTime;
				int bruteforceCost = testGraph.getTraceCost(bruteforceTrace);

				//bw_times.write(bruteforceTime + "\t" + twoApproxTime + "\t" + threeOverTwoApproxTime + "\t");
				//bw_solution.write(bruteforceCost + "\t" + twoApproxCost + "\t" + threeOverTwoApproxCost + "\t");
				System.out.println("Test no. " + (test + 1) + " for " + vertices + " vertices:");
				System.out.println("Solution cost for bruteforce: " + bruteforceCost + " found in " + bruteforceTime
						+ " nanoseconds");
				System.out.println("Solution cost for 3/2-approx: " + threeOverTwoApproxCost + " found in "
						+ threeOverTwoApproxTime + " nanoseconds");
				System.out.println(
						"Solution cost for 2-approx: " + twoApproxCost + " found in " + twoApproxTime + " nanoseconds");
				System.out.println("Approximation coefficients are: "
						+ decimalFormat.format((double) threeOverTwoApproxCost / bruteforceCost)
						+ " for 3/2-approximation and " + decimalFormat.format((double) twoApproxCost / bruteforceCost)
						+ " for 2-approximation.");
			}
		}
		bw_solution.close();
		bw_times.close();
	}

}
