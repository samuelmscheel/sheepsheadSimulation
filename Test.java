import java.util.ArrayList;
import java.util.Random;
/**
 * Testing class to run simulation with various parameters.
 * @author Samuel Scheel
 */
public class Test 
{
	public static void main(String[] args) 
	{
		// PARAMATER OPTIMIZATION \\
//		Random random = new Random();
//		int numberOfSeeds = 10;
//		int noTrials = 100000;
//		int initialSeed = random.nextInt(1000);
//		int noTrumpParam = 0;
//		int[] wins = new int[11];
//		for (int count = 0; count < numberOfSeeds; count++)
//		{
//			for (int i = 0; i <= 10; i++)
//			{
//				wins[i] += Simulation.simulate(0, i, initialSeed + count, noTrials);
//			}
//		}
//		for (int i = 0; i <= 10; i++)
//		{
//			System.out.println("Parameter = " + i + " - Player One won " + wins[i] + " out of " + noTrials + " trials.");
//		}
		
		// SIMULATION \\
		Random random = new Random();
		int simulationsPerSeed = 10000;
		int numberOfSeeds = 10;
		int initialSeed = random.nextInt(1000);
		int noTrials = simulationsPerSeed*numberOfSeeds;
		int trumpStratWins = 0;
		int randomStratWins = 0;
		for (int i = 1; i <= 10; i++)
		{
			trumpStratWins += Simulation.simulate(0, 10, initialSeed + i, simulationsPerSeed);
			randomStratWins += Simulation.simulate(1, 10, initialSeed + i, simulationsPerSeed);
		}
		System.out.println("Using the Trump strategy, Player One won " + trumpStratWins + " out of " + noTrials);
		System.out.println("Using the Random strategy, Player One won " + randomStratWins + " out of " + noTrials);
	}
}