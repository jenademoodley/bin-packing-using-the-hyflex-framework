package binpacking;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import BinPacking.BinPacking;

public class Packing {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		long seed = System.currentTimeMillis();
		int gen = 1000;
		String solution = "";

		System.out.println("Bin-Packing using the HyFlex Framework");
		System.out.println("========================================");
		System.out.println(
				"Do you wish to enter a Seed? (Number Only) (Default is System Current Time in Milliseconds)\n1 - Yes\n2 - No");
		int choice = sc.nextInt();

		while (choice < 1 || choice > 2) {
			System.out.println(
					"\nUnavailable Option\nDo you wish to enter a Seed? (Number Only) (Default is System Current Time in Milliseconds)\n1 - Yes\n2 - No");
			choice = sc.nextInt();
		}

		if (choice == 1) {
			System.out.println("\nEnter Seed");
			seed = sc.nextLong();
		}

		System.out.println("\nChoose Problem Instance (Number Only)");
		System.out.println("1 - bpp1");
		System.out.println("2 - bpp2");
		System.out.println("3 - bpp3");
		System.out.println("4 - bpp4");
		System.out.println("5 - bpp5");

		int instance = sc.nextInt();

		while (instance < 1 || instance > 5) {
			System.out.println("\nUnavailable Option\nChoose Problem Instance (Number Only)");
			System.out.println("1 - bpp1");
			System.out.println("2 - bpp2");
			System.out.println("3 - bpp3");
			System.out.println("4 - bpp4");
			System.out.println("5 - bpp5");
			instance = sc.nextInt();
		}

		System.out.println("\nChoose Operator (Number Only)");
		System.out.println("1 - Mutational Heuristic");
		System.out.println("2 - Local Search Heuristic");
		System.out.println("3 - Ruin-Recreate Heuristic");
		System.out.println("4 - Crossover");

		int operator = sc.nextInt();

		while (operator < 1 || operator > 4) {
			System.out.println("\nUnavailable Option\nChoose Operator (Number Only)");
			System.out.println("1 - Mutational Heuristic");
			System.out.println("2 - Local Search Heuristic");
			System.out.println("3 - Ruin-Recreate Heuristic");
			System.out.println("4 - Crossover");
			operator = sc.nextInt();
		}

		BufferedWriter bw = null;
		FileWriter fw = null;
		String FILENAME = "outputs//output";
		int t = 1;
		try {

			File f = new File(FILENAME + t + ".txt");

			// if file doesn't exists, then create it

			while (f.exists()) {
				t++;
				f = new File(FILENAME + t + ".txt");
			}
			f.createNewFile();

			// true = append file
			fw = new FileWriter(f.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
			System.out.println("Problem Instance:\t\tbpp" + instance);
			bw.write("Problem Instance:\t\tbpp" + instance);
			bw.newLine();
			instance--;

			BinPacking BPP = new BinPacking(seed);
			BPP.loadInstance(instance);
			BPP.initialiseSolution(0);

			System.out.println("Seed:\t\t\t\t" + seed);
			bw.write("Seed:\t\t\t\t" + seed);
			bw.newLine();
			double fitness = BPP.getFunctionValue(0);
			solution = BPP.solutionToString(0);
			System.out.println("\nInitial Solution fitness:\t" + fitness);
			bw.newLine();
			bw.write("Initial Solution fitness:\t" + fitness);
			bw.newLine();
			long start = System.nanoTime();
			if (operator == 1) {

				double mutIntensity = 0.6;
				BPP.setIntensityOfMutation(mutIntensity);
				int n = 0;
				double newFitness = fitness;

				while (n < 10 && gen > 0) {
					BPP.applyHeuristic(3, 0, 0);
					fitness = BPP.getFunctionValue(0);

					if (fitness == newFitness)
						n++;

					else {
						n = 0;
						if (fitness < newFitness) {
							newFitness = fitness;
							solution = BPP.solutionToString(0);
						}
					}

					gen--;
				}
				System.out.println("Mutational Solution fitness:\t" + newFitness);
				bw.write("Mutational Solution fitness:\t" + newFitness);
				bw.newLine();
			}

			if (operator == 2) {
				int n = 0;
				double newFitness = fitness;

				while (n < 10 && gen > 0) {
					BPP.applyHeuristic(4, 0, 0);
					fitness = BPP.getFunctionValue(0);

					if (fitness == newFitness)
						n++;

					else {
						n = 0;
						if (fitness < newFitness) {
							newFitness = fitness;
							solution = BPP.solutionToString(0);
						}
					}

					gen--;
				}
				System.out.println("Local Search Solution fitness:\t" + newFitness);
				bw.write("Local Search Solution fitness:\t" + newFitness);
				bw.newLine();
			}

			if (operator == 3) {
				int n = 0;
				double newFitness = fitness;

				while (n < 10 && gen > 0) {
					BPP.applyHeuristic(1, 0, 0);
					fitness = BPP.getFunctionValue(0);
					if (fitness == newFitness)
						n++;

					else {
						n = 0;
						if (fitness < newFitness) {
							newFitness = fitness;
							solution = BPP.solutionToString(0);
						}
					}

					gen--;
				}
				System.out.println("Ruin-Recreate Solution fitness:\t" + newFitness);
				bw.write("Ruin-Recreate Solution fitness:\t" + newFitness);
				bw.newLine();
			}

			if (operator == 4) {
				int n = 0;
				BPP.initialiseSolution(1);
				double newFitness = fitness;

				while (n < 10 && gen > 0) {
					BPP.applyHeuristic(7, 0, 1, 0);
					fitness = BPP.getFunctionValue(0);

					if (fitness == newFitness)
						n++;

					else {
						n = 0;
						if (fitness < newFitness) {
							newFitness = fitness;
							solution = BPP.solutionToString(0);
						}
					}

					gen--;
				}
				System.out.println("Crossover Solution fitness:\t" + newFitness);
				bw.write("Crossover Solution fitness:\t" + newFitness);
				bw.newLine();
			}

			long end = System.nanoTime();
			long time = end - start;

			bw.newLine();
			System.out.println("\nIterations:\t\t\t" + (1000 - gen));
			bw.write("Iterations:\t\t" + (1000 - gen));
			bw.newLine();

			System.out.println("Time taken in nanoseconds:\t" + time);
			bw.write("Time taken in nanoseconds:\t" + time);
			bw.newLine();
			bw.newLine();
			System.out.println();
			System.out.println(solution);
			bw.write(solution);
			bw.newLine();
		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}

		System.out.println("\nPush Enter to Continue");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sc.close();
	}

}
