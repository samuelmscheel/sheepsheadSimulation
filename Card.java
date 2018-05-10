/**
 * Class for managing Card Objects
 * @author Samuel Scheel
 */
public class Card 
{
	private char suit;
	private char face;
	private String symbol;
	private int suitStrength;
	private int points;
	public Card(char suit, char face, String symbol, int suitStrength, int points) 
	{
		this.suit = suit;
		this.face = face;
		this.symbol = symbol;
		this.suitStrength = suitStrength;
		this.points = points;
	}
	public boolean isTrump() 
	{
		return suit == 't';
	}
	public boolean isSuit(char suit) 
	{
		return this.suit == suit;
	}
	public char getSuit() 
	{
		return suit;
	}
	public char getFace() 
	{
		return face;
	}
	public String getSymbol()
	{
		return symbol;
	}
	public int getSuitStrength() 
	{
		return suitStrength;
	}
	public int getPoints() 
	{
		return points;
	}
	public String toString() 
	{
		return "" + face + " of " + symbol;
	}
}