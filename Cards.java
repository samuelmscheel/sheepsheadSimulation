import java.util.ArrayList;
import java.util.Random;
/**
 * Class for managing Collection of Cards
 * Mainly an extension of ArrayList API
 * Several important methods unique to this simulation
 * @author Samuel Scheel
 */
public class Cards 
{
	private ArrayList<Card> cards;
	private int noCards;
	public Cards(ArrayList<Card> cards) 
	{
		this.cards = cards;
		this.noCards = cards.size();
	}
	public Cards() 
	{
		this.cards = new ArrayList<Card>();
		this.noCards = 0;
	}
	public int getNoCards()
	{
		return this.noCards;
	}
	public String toString()
	{
		String print = "";
		for (int i = 0; i < cards.size(); i ++)
		{
			if (i == cards.size() - 1)
				print = print + cards.get(i).toString();
			else
				print = print + cards.get(i).toString() + "\n";
		}
		return print;
	}
	public Card getCard(int index) 
	{
		return this.cards.get(index);
	}
	public void addCard(Card card)
	{
		this.cards.add(card);
		this.noCards++;
	}
	public void addCards(Cards cards)
	{
		int num = cards.noCards;
		for (int i = 0; i < num; i++)
			this.addCard(cards.removeCard(0));
	}
	public Card removeCard(int index)
	{
		this.noCards--;
		return this.cards.remove(index);
	}
	public boolean removeCard(Card card)
	{
		this.noCards--;
		return this.cards.remove(card);
	}
	public boolean hasSuit(char suit)
	{
		for(int i = 0; i < this.cards.size(); i++)
		{
			if (this.cards.get(i).getSuit() == suit)
				return true;
		}
		return false;
	}
	public boolean hasTrump()
	{
		for(int i = 0; i < this.cards.size(); i++)
		{
			if(this.cards.get(i).isTrump())
				return true;
		}
		return false;
	}
	public int numberOfTrump()
	{
		int number = 0;
		for(int i = 0; i < cards.size(); i++)
		{
			if(cards.get(i).isTrump())
				number++;
		}
		return number;
	}
	public int numberOfPoints()
	{
		int number = 0;
		for(int i = 0; i < cards.size(); i++)
			number += cards.get(i).getPoints();
		return number;
	}
	public void takePick(Cards pick)
	{
		this.cards.add(pick.removeCard(0));
		this.cards.add(pick.removeCard(0));
		this.noCards += 2;
	}
	public Cards buryRandom(Random random)
	{
		Cards bury = new Cards();
		Card bury1 = null;
		Card bury2 = null;
		int index1 = random.nextInt(12);
		int index2 = random.nextInt(12);
		while (index1 == index2)
			index2 = random.nextInt(12);
		bury1 = this.getCard(index1);
		bury2 = this.getCard(index2);
		bury.addCard(bury1);
		bury.addCard(bury2);
		this.cards.remove(bury1);
		this.cards.remove(bury2);
		return bury;
	}
	/**
	 * Burys cards based on number of points, starting with fail
	 * @return Cards to bury.
	 */
	public Cards buryPoints()
	{
		Cards bury = new Cards();
		Card bury1 = null;
		Card bury2 = null;
		int max1 = -1;
		int max2 = -1;
		int max1index = 0;
		int max2index = 0;
		for (int i = 0; i < this.noCards; i++)
		{
			if ((this.getCard(i).getPoints() > max1) && (!this.getCard(i).isTrump()))
			{
				max1 = this.getCard(i).getPoints();
				max1index = i;
			}
		}
		for (int i = 0; i < this.noCards; i++)
		{
			if ((this.getCard(i).getPoints() > max2) && (!this.getCard(i).isTrump()) && (i != max1index))
			{
				max2 = this.getCard(i).getPoints();
				max2index = i;
			}
		}
		//If hand is all trump and we must bury trump\\
		if (max1 < 0)
		{
			for (int i = 0; i < this.noCards; i++)
			{
				if (this.getCard(i).getPoints() > max1)
				{
					max1 = this.getCard(i).getPoints();
					max1index = i;
				}
			}
		}
		if (max2 < 0)
		{
			for (int i = 0; i < this.noCards; i++)
			{
				if ((this.getCard(i).getPoints() > max2) && (i != max1index))
				{
					max2 = this.getCard(i).getPoints();
					max2index = i;
				}
			}
		}
		bury1 = this.getCard(max1index);
		bury2 = this.getCard(max2index);
		bury.addCard(bury1);
		bury.addCard(bury2);
		this.cards.remove(bury1);
		this.cards.remove(bury2);
		return bury;
	}
	
}