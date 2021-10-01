import java.util.*;

/**
 * Code for the game of Solitaire!
 * 
 * @author Aditi Khanna
 * @version November 17, 2019
 */
public class Solitaire
{
    

    private Stack<Card> stock;
    private Stack<Card> waste;
    private Stack<Card>[] foundations;
    private Stack<Card>[] piles;
    private SolitaireDisplay display;
    private ArrayList<Card> deck;

    /**
     * Constructs a solitaire game.
     * Contains piles, foundations, stock and waste
     */
    public Solitaire()
    {
        //initialize all of the instance vars
        foundations = new Stack[4];
        for (int i = 0; i < foundations.length; i++)
            foundations[i] = new Stack<Card>();
        piles = new Stack[7];
        for (int i = 0; i < piles.length; i++)
            piles[i] = new Stack<Card>();
        stock = new Stack<Card>();
        waste = new Stack<Card>();
        createStock();
        deal();
        display = new SolitaireDisplay(this);
    }

    /**
     * Returns the card at the top of the stock.
     * 
     * @return  the top stock card or null if empty
     */
    public Card getStockCard()
    {
        if (!stock.isEmpty())
            return stock.peek();
        else
            return null;
    }

    /**
     * Returns top card of waste pile.
     * 
     * @return the top waste card or null if empty
     */
    public Card getWasteCard()
    {
        if (!waste.isEmpty())
            return waste.peek();
        else
            return null;
    }
    /**
     * Returns the card at the top of the foundation pile of index.
     * @precondition    0 <= index < 4
     * @param   index   the index of the foundation
     * @return  returns the card on top of the given foundation,
     *          or null if the foundation is empty
     */ 
    public Card getFoundationCard(int index)
    {
        if (!foundations[index].isEmpty())
            return foundations[index].peek();
        else
            return null;
    }

    /**
     * Returns the pile at the given index.
     * @precondition    0 <= index < 7
     * @param   index   the index of the pile
     * @return  returns the stack of the given pile
     */
    public Stack<Card> getPile(int index)
    {
        return piles[index];
    }

    /**
     * Creates a new deck and shuffles it to create a stock
     */
    public void createStock()
    {
        deck = new ArrayList<Card>();
        for (int i = 1; i <= 13; i++)
        {
            deck.add(new Card(i, "h"));
            deck.add(new Card(i, "d"));
            deck.add(new Card(i, "c"));
            deck.add(new Card(i, "s"));
        }
        while (deck.size() > 0)
        {
            int rand = (int)(Math.random() * deck.size());
            stock.push(deck.remove(rand));
        }
    }

    /**
     * Deals seven piles. 
     */
    public void deal()
    {
        for (int i = 0; i < piles.length; i++)
        {
            for (int j = 0; j < i + 1; j++)
            {
                piles[i].push(stock.pop());
            }
            piles[i].peek().turnUp();
        }
    }

    /**
     * Deals three cards from the stock to waste pile.
     */
    public void dealThreeCards()
    {
        int i = 0;
        while (!stock.isEmpty() && i < 3)
        {
            waste.push(stock.pop()).turnUp();
            i++;
        }
    }
    
    /**
     * Deals one card from the stock to waste pile.
     */
    public void dealOneCard()
    {
        if (!stock.isEmpty())
            waste.push(stock.pop()).turnUp();
        }

    /**
     * Resets the stock if the waste is empty.
     */
    public void resetStock()
    {
        while (!waste.isEmpty())
        {
            stock.push(waste.pop()).turnDown();
        }
    }

    /**
     * when stock is clicked, either deal three cards or reset.
     */
    public void stockClicked()
    {
         if(!display.isWasteSelected() || !display.isPileSelected())
	    {
          if(!stock.isEmpty())
          {
              dealThreeCards();
           }
          else
            {  resetStock();
                display.unselect();
            }
         System.out.println("stock clicked");
       }
    }

    /**
     * when waste is called iether reset or deal.
     */
    public void wasteClicked()
    {
        if (!waste.isEmpty() && !display.isWasteSelected()
            && !display.isPileSelected())
        {
            display.selectWaste();
        }
        else if (display.isWasteSelected())
        {
            display.unselect();
        }
    }

    /**
     * moves cards to foundations
     * 
     * @precondition    0 <= index < 4
     * @param   index   the index of the foundation
     */
    public void foundationClicked(int index)
    {
        if (display.isWasteSelected())
        {
            if (canAddToFoundation(waste.peek(), index))
            {
                foundations[index].push(waste.pop());
                display.unselect();
            }    
        }
        else if (display.isPileSelected())
        {
            Stack <Card> p = getPile(display.selectedPile());
            if (canAddToFoundation(p.peek(), index))
            {
                foundations[index].push(p.pop());
                display.unselect();
            }
        }
        System.out.println("foundation #" + index + " clicked");
    }

    /**
     * Determines if a card can be added to the given foundation.
     * 
     * @precondition    0 <= index < 4
     * @param   card    the card to be added to the foundation
     * @param   index   the index of the foundation
     * @return  true if the card can be added successfully; otherwise,
     *          false
     */
    public boolean canAddToFoundation(Card card, int index)
    {
        if (foundations[index].isEmpty())
        {
            return (card.getRank() == 1);
        }
        else
        {
            if (card.getRank() - foundations[index].peek().getRank() == 1
                && foundations[index].peek().getSuit().equals(card.getSuit()))
            {
                return true;
            }
        }
        return false;
    }
    /**
     * Removes and returns the face up cards on a given pile.
     * 
     * @precondition 0 <= index < 7
     * @param   index   the index of the pile
     * @return  the stack of face up cards on the pile
     */
    private Stack<Card> removeFaceUpCards(int index)
    {
        Stack<Card> upCards = new Stack<Card>();
        while (!piles[index].isEmpty() && piles[index].peek().isFaceUp())
        {
            upCards.push(piles[index].pop());
        }
        return upCards;
    }
    /**
     * Determines if a card can be added to the given pile
     * 
     * @precondition    0 <= index < 7
     * @param card the card to be added to the pile
     * @param index the index of the pile
     */
    private boolean canAddToPile(Card card, int index)
    {
        if (!piles[index].isEmpty() && card.getRank() != 13)
        {
            Card curr = piles[index].peek();
            if (curr.isFaceUp())
            {
                if ((curr.isRed() && !card.isRed())
                    && (curr.getRank() == card.getRank() + 1))
                {
                    return true;
                }
                else if ((!curr.isRed() && card.isRed())
                    && (curr.getRank() == card.getRank() + 1))
                {
                    return true;
                }
            }
        }
        else if (piles[index].isEmpty() && card.getRank() == 13)
        {
            return true;
        }
        return false;
    }
    
    /**
     * Called when a pile is clicked to either add cards from the waste,
     * another pile, or select and unselect a pile.
     * 
     * @param   index   the index of the pile that is clicked
     */
    public void pileClicked(int index)
    {
        if (display.isWasteSelected())
        {
            if ((!piles[index].isEmpty() && canAddToPile(waste.peek(), index))
                || (piles[index].isEmpty() && waste.peek().getRank() == 13))
            {
                piles[index].push(waste.pop());
                display.unselect();
            }
        }
        
        if (!display.isWasteSelected() && !display.isPileSelected() &&
            !piles[index].isEmpty() && piles[index].peek().isFaceUp())
        {
            display.selectPile(index);
        }   
        
        else if (display.isPileSelected() && display.selectedPile() == index)
        {
            display.unselect();
        }
        
        else if (display.isPileSelected() && !piles[index].isEmpty() &&
            display.selectedPile() != index)
        {
            Stack<Card> temps = removeFaceUpCards(display.selectedPile());
            if (canAddToPile(temps.peek(), index))
            {
                addToPile(temps, index);
                display.unselect();
            }
            else
            {
                addToPile(temps, display.selectedPile());
            }
        }
        
        else if (display.isPileSelected() && piles[index].isEmpty() &&
            display.selectedPile() != index)
        {
            Stack<Card> currs = removeFaceUpCards(display.selectedPile());
            if (canAddToPile(currs.peek(), index))
            {
                addToPile(currs, index);
                display.unselect();
            }
            else
            {
                addToPile(currs, display.selectedPile());
            }
        }
        
        else if (!display.isPileSelected() && !display.isWasteSelected()
            && !piles[index].isEmpty() && !piles[index].peek().isFaceUp())
        {
            piles[index].peek().turnUp();
        }
        else if (!foundations[display.selectedFoundation()].isEmpty()
            && display.isFoundationSelected())
        {
            display.selectFoundation(display.selectedFoundation());
        }
        else if (!foundations[display.selectedFoundation()].isEmpty() && 
            canAddToPile(foundations[display.selectedFoundation()].peek(), 
                index))
        {
            piles[index].push(foundations[display.selectedFoundation()].pop());
        }
    }
    /**
     * Adds the given cards on to the given pile.
     * 
     * @param   cards   the cards to add to the pile
     * @param   index   the index of the pile
     */
    private void addToPile(Stack<Card> cards, int index)
    {
        while (!cards.isEmpty())
        {
            piles[index].push(cards.pop());
        }
    }

    /**
     * Plays a Solitaire game!
     * 
     * @param args arguments from the command line
     */
    public static void main(String[] args)
    {
        new Solitaire();
    }
}