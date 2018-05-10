import java.util.Random;

/**
 * Class for managing Player Objects
 * @author Samuel Scheel
 */
public class Player 
{
	private boolean picker;
	private String buryStrategy;
	private Cards hand;
	public Player(boolean picker, String buryStrategy, Cards hand) 
	{
		this.picker = false;
		this.buryStrategy = buryStrategy;
		this.hand = hand;
	}
	public boolean isPicker() 
	{
		return picker;
	}
	public void setPicker(boolean picker) 
	{
		this.picker = picker;
	}
	public Cards getHand() 
	{
		return hand;
	}
	public boolean hasTrump()
	{
		return hand.hasTrump();
	}
	public boolean hasSuit(char suit)
	{
		return hand.hasSuit(suit);
	}
	public Card getCard(int index)
	{
		return hand.getCard(index);
	}
	public boolean removeCard(Card card)
	{
		return hand.removeCard(card);
	}
	public Card removeCard(int index) 
	{
		return hand.removeCard(index);
	}
	public void takePick(Cards pick)
	{
		hand.takePick(pick);
	}
	public Cards bury(Random random)
	{
		if (this.buryStrategy.equals("points"))
			return hand.buryPoints();
		else
			return hand.buryRandom(random);
	}
	
}