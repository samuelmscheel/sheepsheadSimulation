import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
/**
 * Simulates 1000 trials of Sheepshead
 * All players play randomly, but follow the rules of play
 * Player One is what we simulate on
 * 		Player One picks either randomly, or based on number of Trump in hand
 * @author Samuel Scheel
 */
public class Simulation 
{
	public static int simulate(int simulationType, int noTrumpParam, int seed, int noTrials) 
	{
		/**
		 * Debug Codes:
		 * 0 = None
		 * 1 = Game
		 * 2 = Round
		 * 3 = General info
		 * 4 = Round info
		 */
		int printDebug = 0;
		/**
		 * Simulation Codes:
		 * 0 = Null: Player One picks depending on number of trump
		 * 1 = Random: Player One picks randomly, same as Player Two and Three
		 */
		//Initializations\\
		int playerOneWins = 0;
		int playerTwoWins = 0;
		int playerThreeWins = 0;
		int trial = 0;
		int dealer = -1;
		Random random = new Random(seed);
		ArrayList <Card> deck = loadDeck();
		//Start Simulation Loop\\
		while (trial < noTrials)
		{
			//Initilizations\\
			trial++;
			dealer = (dealer + 1) % 3;
			if (printDebug >= 1)
				System.out.println("Game " + trial);
			if (printDebug >= 3)
				System.out.println("Player " + (dealer + 1) + " deals");
			int picker = dealer;
			Cards pickDiscard = new Cards();
			Cards teamDiscard = new Cards();
			Cards table = new Cards();
			Cards pick = new Cards();
			pick.addCard(deck.get(30));
			pick.addCard(deck.get(31));
			Collections.shuffle(deck, random);
			//Deal\\
			Player[] players = deal(deck);
			//Pick\\
			for (int i = 1; i <= 3; i ++)
			{
				if (i == 3)
					break;
				else if ((((dealer + i) % 3) == 0) && (simulationType == 0))
				{
					if ((players[0].getHand().numberOfTrump() >= noTrumpParam))
					{
						//Number of Trump picking strategy
						picker = 0;
						break;
					}
				}
				else if (((dealer + i) % 3) == random.nextInt(3))
				{
					picker = (dealer + i) % 3;
					break;
				}
			}
			//Set up Pick\\
			if (printDebug >= 3)
				System.out.println("Player " + (picker + 1) + " picks.");
			players[picker].takePick(pick);
			players[picker].setPicker(true);
			pickDiscard = players[picker].bury(random); //Picker burys cards with most points, starting with fail suits
			//Rounds\\
			int strIndex = 0; //Index of player with strongest hand, need to know so they can start next round
			for (int round = 0; round < 10; round++)
			{
				if (printDebug >= 2)
					System.out.println("Round " + (round + 1) + ":");
				char calledSuit;
				int index = strIndex;
				Card leadCard = players[strIndex].removeCard(random.nextInt(10 - round));
				Card strongest = leadCard;
				table.addCard(leadCard);
				calledSuit = leadCard.getSuit();
				if (printDebug >= 4)
					System.out.println("Player " + (strIndex + 1) + " lead " + leadCard);
				for (int i = 1; i <= 2; i++)
				{
					boolean done = false;
					index = (index + 1) % 3;
					Card turn = null;
					if (players[index].hasSuit(calledSuit))
					//If the player has the suit, play a random card of that suit
					{
						while (!done)
						{
							turn = players[index].getCard(random.nextInt(10 - round));
							if (turn.isSuit(calledSuit))
								done = true;
						}
					}
					else if (players[index].hasTrump())
					//If the player does not have the called suit, play a random trump if possible
					{
						while (!done)
						{
							turn = players[index].getCard(random.nextInt(10 - round));
							if (turn.isTrump())
								done = true;
						}
					}
					else 
					//If the player has neither the called suit nor a trump, play a random card
						turn = players[index].getCard(random.nextInt(10 - round));
					//Check for Strongest\\
					if ((turn.isTrump()) && (strongest.isTrump()) && (turn.getSuitStrength() > strongest.getSuitStrength()))
					//Check if played trump is stronger than previous strongest trump
					{
						strongest = turn;
						strIndex = index;
					}
					else if ((turn.isTrump()) && (!strongest.isTrump()))
					//Check if played card is trump and strongest is not a trump
					{
						strongest = turn;
						strIndex = index;
					}
					else if ((!turn.isTrump()) && (!strongest.isTrump()) && (turn.getSuit() == calledSuit) && (turn.getSuitStrength() > strongest.getSuitStrength()))
					//Check if played is stronger than previous stronger
					{
						strongest = turn;
						strIndex = index;
					}
					table.addCard(turn);
					players[index].removeCard(turn);
					if (printDebug >= 4)
						System.out.println("Player " + (index + 1) + " played " + turn);
				}
				if (printDebug >= 3)
					System.out.println("Player " + (strIndex + 1) + " takes the hand with " + strongest);
				if (players[strIndex].isPicker())
					pickDiscard.addCards(table);
				else
					teamDiscard.addCards(table);
			}
			if (pickDiscard.numberOfPoints() > teamDiscard.numberOfPoints())
			{
				if (players[0].isPicker())
					playerOneWins++;
				else if (players[1].isPicker())
					playerTwoWins++;
				else if (players[2].isPicker())
					playerThreeWins++;
				if (printDebug >= 2)
				{
					System.out.println("Player " + (picker + 1) + " has won with " + pickDiscard.numberOfPoints() + " points!");
					System.out.println("Player " + (((picker + 1) % 3) + 1) + " and Player " + (((picker + 2) % 3) + 1) + " have lost with " + teamDiscard.numberOfPoints() + " points!");
				}
			}
			else 
			{
				if (players[0].isPicker())
				{
					playerTwoWins++;
					playerThreeWins++;
				}	
				else if (players[1].isPicker())
				{
					playerOneWins++;
					playerThreeWins++;
				}
				else if (players[2].isPicker())
				{
					playerOneWins++;
					playerTwoWins++;
				}
				if (printDebug >= 2)
				{
					System.out.println("Player " + (picker + 1) + " has lost with " + pickDiscard.numberOfPoints() + " points!");
					System.out.println("Player " + (((picker + 1) % 3) + 1) + " and Player " + (((picker + 2) % 3) + 1) + " have won with " + teamDiscard.numberOfPoints() + " points!");
				}
			}
		}
		return playerOneWins;
	}
	/**
	 * Loads an unshuffled sheepshead deck
	 * @return
	 */
	public static ArrayList <Card> loadDeck()
	{
		ArrayList <Card> deck = new ArrayList<Card>();
		deck.add(new Card('t', '7', "diamonds", 0, 0));
		deck.add(new Card('t', '8', "diamonds", 1, 0));
		deck.add(new Card('t', '9', "diamonds", 2, 0));
		deck.add(new Card('t', 'T', "diamonds", 4, 10));
		deck.add(new Card('t', 'A', "diamonds", 5, 11));
		deck.add(new Card('t', 'J', "diamonds", 6, 2));
		deck.add(new Card('t', 'Q', "diamonds", 10, 3));
		deck.add(new Card('t', 'K', "diamonds", 3, 4));
		deck.add(new Card('h', '7', "hearts", 0, 0));
		deck.add(new Card('h', '8', "hearts", 1, 0));
		deck.add(new Card('h', '9', "hearts", 2, 0));
		deck.add(new Card('h', 'T', "hearts", 4, 10));
		deck.add(new Card('h', 'A', "hearts", 5, 11));
		deck.add(new Card('t', 'J', "hearts", 7, 2));
		deck.add(new Card('t', 'Q', "hearts", 11, 3));
		deck.add(new Card('h', 'K', "hearts", 3, 4));
		deck.add(new Card('s', '7', "spades", 0, 0));
		deck.add(new Card('s', '8', "spades", 1, 0));
		deck.add(new Card('s', '9', "spades", 2, 0));
		deck.add(new Card('s', 'T', "spades", 4, 10));
		deck.add(new Card('s', 'A', "spades", 5, 11));
		deck.add(new Card('t', 'J', "spades", 8, 2));
		deck.add(new Card('t', 'Q', "spades", 12, 3));
		deck.add(new Card('s', 'K', "spades", 3, 4));
		deck.add(new Card('c', '7', "clubs", 0, 0));
		deck.add(new Card('c', '8', "clubs", 1, 0));
		deck.add(new Card('c', '9', "clubs", 2, 0));
		deck.add(new Card('c', 'T', "clubs", 4, 10));
		deck.add(new Card('c', 'A', "clubs", 5, 11));
		deck.add(new Card('t', 'J', "clubs", 9, 2));
		deck.add(new Card('t', 'Q', "clubs", 13, 3));
		deck.add(new Card('c', 'K', "clubs", 3, 4));
		return deck;
	}
	/**
	 * Deals out Cards, not taking into account pick
	 * @param deck
	 * @return
	 */
	public static Player[] deal(ArrayList <Card> deck)
	{
		Player[] players = new Player[3];
		ArrayList <Card> hand1 = new ArrayList<Card>();
		ArrayList <Card> hand2 = new ArrayList<Card>();
		ArrayList <Card> hand3 = new ArrayList<Card>();
		for(int i = 0; i < 30; i+= 3)
		{
			hand1.add(deck.get(i));
			hand2.add(deck.get(i + 1));
			hand3.add(deck.get(i + 2));
		}
		players[0] = new Player(false, "random", new Cards(hand1));
		players[1] = new Player(false, "random", new Cards(hand2));
		players[2] = new Player(false, "random", new Cards(hand3));
		return players;
	}
}