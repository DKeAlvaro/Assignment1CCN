import java.util.Random;
import java.util.ArrayList;

/**
 * Some very basic stuff to get you started. It shows basically how each
 * chromosome is built.
 * 
 * @author Jo Stevens
 * @version 1.0, 14 Nov 2008
 * 
 * @author Alard Roebroeck
 * @version 1.1, 12 Dec 2012
 * 
 */

public class Practical2 implements Cloneable {
	public static int popsize = 100;
	static final String TARGET = "HELLO WORLD";
	static final char[] charTarget = { 'H', 'E', 'L', 'L', 'O', ' ', 'W', 'O', 'R', 'L', 'D' };
	static char[] alphabet = new char[27];
	 static int generatioNumber = 9;

	

	public static void main(String[] args) {

		Individual[] currentGeneration = randomGeneration(popsize);
		HeapSort prueba = new HeapSort();
		for (int i = 0; i < currentGeneration.length; i++) {
			currentGeneration[i].setFitness(myFitness(currentGeneration[i]));
		}
		prueba.sort(currentGeneration);
		int counter = 0;
		boolean solutionFound = false;
		while (counter<=generatioNumber) {
		

			for (int i = 0; i < currentGeneration.length; i++) {
				currentGeneration[i].setFitness(myFitness(currentGeneration[i]));
			}
			prueba.sort(currentGeneration);
			Individual[] parents = new Individual[50];

			for (int i = 0; i < parents.length; i++) {
				parents[i] = new Individual(new char[TARGET.length()]);
				for (int j = 0; j < parents[0].chromosome.length; j++) {
					// parents[i].chromosome[j] = currentGeneration[i].getChromosome()[j];
					parents[i].changeLetter(j, currentGeneration[i].getChromosome()[j]);
				}	
			}

			for (int i = 0; i < parents.length; i++) {
				parents[i].setFitness(myFitness(currentGeneration[i]));
			}
			printGeneration(parents);
			System.out.println("Generation number " + counter);
			currentGeneration = crossover(parents);
			counter++;

			for (int i = 0; i < parents.length; i++) {
				if(parents[i].getFitness() == 110){
					System.out.println(parents[i].genoToPhenotype());
					System.out.println("Solution Found!!!");
					solutionFound = true;
					break;
				}
			}
			
			

		}

	}

	public static double myFitness(Individual individual) {
		double fitness = 0;
		final double increment = 10; // the individual's fitness will increment by 1/11 if a letter matches with the
										// target (11 letters in hello world)

		char[] targetArray = new char[TARGET.length()]; // creating an empty char array with the same length as the
														// target

		for (int i = 0; i < TARGET.length(); i++) {
			targetArray[i] = TARGET.charAt(i); // converting the target string to an array of characters
		}

		for (int i = 0; i < TARGET.length(); i++) { // iterating through every chromosome of the individual
			if (individual.getChromosome()[i] == targetArray[i]) { // if a particular individual's chromosome is correct
				fitness += increment;
			}
		}
		return fitness;

	}

	public static Individual chooseFirstParent(Individual[] population1) {
		boolean parentFound = false;
		int index = 0;
		int generationNumber = 1;
		while (!parentFound) {
			population1 = randomGeneration(popsize);
			for (int i = 0; i < population1.length; i++) {
				if (targetLetters(population1[i]) >= 8) {
					parentFound = true;
					index = i;
					break;
				}
			}
			System.out.println("generation " + generationNumber);
			generationNumber++;

		}
		System.out.println("Parent found!!!");
		System.out.println(population1[index].genoToPhenotype());
		return population1[index];
	}

	public static boolean contains(ArrayList<Character> foundChars, char letter) {
		boolean found = false;
		for (int x = 0; x < foundChars.size(); x++) {
			if (foundChars.get(x) == letter) {
				found = true;
				break;
			}
		}
		return found;
	}

	public static int targetLetters(Individual individual) {
		ArrayList<Character> foundChars = new ArrayList<Character>();
		int fitness = 0;
		for (int i = 0; i < individual.getChromosome().length; i++) {
			if ((individual.chromosome[i] == 'H' || individual.chromosome[i] == 'E' || individual.chromosome[i] == ' '
					|| individual.chromosome[i] == 'L' || individual.chromosome[i] == 'O'
					|| individual.chromosome[i] == 'W' || individual.chromosome[i] == 'R'
					|| individual.chromosome[i] == 'D') && !contains(foundChars, individual.chromosome[i])) {
				fitness++;
				foundChars.add(individual.chromosome[i]);
			}
		}
		return fitness;
	}

	public static Individual[] randomGeneration(int popSize) {
		// int popSize = 100;
		for (char c = 'A'; c <= 'Z'; c++) {
			alphabet[c - 'A'] = c;
		}
		alphabet[26] = ' '; // 26th element of the alphabet is the space
		Random generator = new Random(System.currentTimeMillis());
		Individual[] population = new Individual[popSize];
		// we initialize the population with random characters
		for (int i = 0; i < popSize; i++) {
			char[] tempChromosome = new char[TARGET.length()]; // Creates a char array with its length being equal to
																// the target's length

			for (int j = 0; j < TARGET.length(); j++) { // iterate throughout the Target.length (HelloWorld) assign a
														// random letter in the alphabet to each index in the new array
				tempChromosome[j] = alphabet[generator.nextInt(alphabet.length)];

			}
			population[i] = new Individual(tempChromosome); // add this random individual to the population

		}
		return population;
	}

	public static void printGeneration(Individual[] population) {
		int individualnumber = 1;
		for (int i = 0; i < population.length; i++) {
			System.out.println(population[i].genoToPhenotype() + " individual number " + individualnumber
					+ " individual fitness " + population[i].getFitness()); // gets the individual's chromosome
																			// and prints it
			individualnumber++;
		}
	}

	public static Individual[] crossover(Individual[] parents) { // creates a new generation based on the parents
		// List<Individual> newGeneration= new ArrayList<Individual>(popsize);
		Individual[] newGeneration = new Individual[popsize];
		// int random = 0;
		for (int i = 0; i < popsize; i++) {
			newGeneration[i] = new Individual(new char[TARGET.length()]);
			for (int j = 0; j < TARGET.length(); j++) {
				// random = (int) (Math.random() * parents.length+1);
				newGeneration[i].changeLetter(j, parents[(int) (Math.random() * parents.length)].getChromosome()[j]);
			}
		}
		return newGeneration;
	}

}
