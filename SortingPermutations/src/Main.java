import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Main {

	public static void main(String[] args) throws IOException {
		for (int i = 1; i <= 4; i++) {

			HashMap<Integer, Long> map = PermutationsGeneratingClass.generate(i);
			for (Integer key : map.keySet()) {
				System.out.println("Key: " + key + ", value: " + map.get(key));
			}

//			File file = new File("C:\\Users\\Igor\\Desktop\\output_file_" + i + ".txt");
//
//			if (!file.exists()) {
//				file.createNewFile();
//			}
//
//			FileWriter fw = new FileWriter(file.getAbsoluteFile());
//			BufferedWriter bw = new BufferedWriter(fw);
//			for (Integer key : map.keySet()) {
//				bw.write(key + "\t" + map.get(key));
//				bw.newLine();
//			}
//			bw.close();
//
//			System.out.println(i + " Permutations done.");
		}

	}

	public static void printTab(int[] tab) {
		for (int i : tab)
			System.out.print(i + " ");
		System.out.println();
	}

}
