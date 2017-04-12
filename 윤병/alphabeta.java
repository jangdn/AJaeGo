import java.util.Scanner;
import java.util.Random;
import java.math.MathContext;
/**
 * Created by L5vD5 on 2017-04-08.
 */
public class alphabeta {

    public static void alphabeta()
    {
        char[][] temp_board = new char[6][7]; //칸 설정
    }

    //disc를 두는 함수. 해당 열이 차있는지도 확인.
    public static void dropTheDiscAI(char player, char[][] board) {
        Scanner input = new Scanner(System.in);
        boolean done = false;
        Random random = new Random();

        do {
            //System.out.print("Drop a " + (player == 'U' ? "U" : "N") + " disk at column (0-6): ");
            int column = random.nextInt(7); //컴퓨터가 랜덤으로 입력할 경우

            //int column = pruning(4, temp_board);
            if (main.placeTheDisk(board, column, player))
                done = true;
            else
                System.out.println("This column is full. Try a different column");
            //main.displayTheBoard(board);
            char opp;

            if(player == 'N')
            {
                opp = 'U';
            }
            else
            {
                opp = 'N';
            }

            node A = new node(board, null, opp);
            pruning(A, 1  );
        } while (!done);
    }
    public static int pruning(node A, int depth)
    {
        int e;
        int best_choice;//pruning 결과로 반환할 최선의 값
        node B[] = new node[7];
        for(int i= 0 ; i < 7 ; i++) {
            B[i] = A.generate(i);//generate한 후 node B에 대입
        }

        if(depth != 0)
            for(int i = 0 ; i < 7 ; i++) {
                //if (B[i].board[5][0] == '\0' && B[i].board[5][1] == '\0' && B[i].board[5][2] == '\0' && B[i].board[5][3] == '\0' && B[i].board[5][4] == '\0' && B[i].board[5][5] == '\0' && B[i].board[5][6] == '\0')
                    pruning(B[i], depth--);//재귀적으로 함수를 호출함
            }

        return 0;

    }
}