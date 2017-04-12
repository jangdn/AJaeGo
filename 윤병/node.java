import javax.xml.soap.Node;

/**
 * Created by L5vD5 on 2017-04-10.
 */
public class node {
    private node child[] = new node[7];
    private node parent;
    public char board[][] = new char[6][7];
    private char turn;
    public node(char[][] board, node parent, char turn)
    {
        this.board = board;
        this.parent = parent;
        this.turn = turn;
    }

    /*************************
     *   generate child node!
     *
     * @param ith ith child node를 결정해서 generate
     * @return ith child node
     *************************/
    public node generate(int ith)
    {
        char temp_board[][] = new char[6][7];//임시로 사용할 Board

        char opp;

        if(turn == 'N')
        {
            opp = 'U';
        }
        else
        {
            opp = 'N';
        }

        //2^depth개의 임시 board를 만들어서 노드를 탐색할 준비를 한다.
        for(int j = 0; j < 6 ; j ++)
        {
                for(int k = 0; k < 7 ; k++)
                {
                    temp_board[j][k] = board[j][k];
                }
        }
        main.placeTheDisk(temp_board, ith, turn);
        this.child[ith] = new node(temp_board, this, opp);
        main.displayTheBoard(temp_board);

        return this.child[ith];
    }


}
