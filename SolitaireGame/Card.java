
/**
 * A Card object is a standard playing card from a 52 card deck
 * with 1-10 jack, king, queen, ace. 
 *
 * @author Aditi Khanna
 * @version November 17, 2019
 */
public class Card
{
    private int rank;
    private String suit;
    private boolean isFaceUp;

    /**
     * Constructs a card object
     * 
     * @param r   the rank of the card
     * @param s   the suit of the card
     */
    public Card(int r, String s)
    {
        rank = r;
        suit = s;
        isFaceUp = false;
    }

    /**
     * Returns the rank of the card.
     * 
     * @return  the rank of the card
     */
    public int getRank()
    {
        return rank;
    }

    /**
     * Returns the suit of the card
     * 
     * @return  the suit of the card
     */
    public String getSuit()
    {
        return suit;
    }

    /**
     * Determines if the card is red.
     * 
     * @return  true if the card is red; otherwise,
     *          false
     */
    public boolean isRed()
    {
        return getSuit().equals("h") || getSuit().equals("d");
    }

    /**
     * Determines if the card is face up.
     * 
     * @return  true if the card is face up; otherwise,
     *          false
     */
    public boolean isFaceUp()
    {
        return isFaceUp;
    }

    /**
     * Turns the card face up.
     */
    public void turnUp()
    {
        if (!isFaceUp())
            isFaceUp = !isFaceUp;
    }

    /**
     * Turns the card face down.
     */
    public void turnDown()
    {
        if (isFaceUp())
            isFaceUp = !isFaceUp;
    }

    /**
     * Gets the file name of the card to access via the class folder.
     * 
     * @return  the file name of the card
     */
    public String getFileName()
    {
        if (!isFaceUp())
        {
            return "cards/back.gif";
        }
        else
        {
            String num = ((Integer)getRank()).toString(); 
            if (rank == 13)
                num = "k";
            if (rank == 1) 
                num = "a";
            if (rank == 12)
                num = "q";
            if (rank == 10)
            {
                num = "t";
            }
            if (rank == 11)
                num = "j";
            return "cards/" + num + suit + ".gif";
        }
    }
}

