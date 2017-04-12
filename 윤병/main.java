/**
 * Created by L5vD5 on 2017-04-08.
 */
import java.util.Scanner;

public class main {

        public static void main(String[] args) {

            char[][] board = new char[6][7]; //칸 설정

            //우리가 먼저둘지 상대방이 먼저둘지 정함
            System.out.println("■■■ 우리먼저(0), 니네먼저(1) ■■■");
            Scanner input = new Scanner(System.in);
            int firstPlayer = input.nextInt();

            displayTheBoard(board); //현재 상황을 화면에 표시

            while (true) {

                //우리(U)먼저, 니네(N)그다음
                if(firstPlayer == 0){
                    aiPlay('U', board);
                    userPlay('N', board);
                }

                //니네(N)먼저, 우리(U)그다음
                else if(firstPlayer == 1){
                    userPlay('N', board);
                    aiPlay('U', board);
                }


            }
        }


        //사용자의 턴(상대편꺼 보고 놓음)
        public static void aiPlay(char who, char board[][]){
            dropTheDisc(who, board); //'who'플레이어 착수
            displayTheBoard(board); //현재 상황을 화면에 표시

            //이겼는지 비겼는지
            if (isWon(board)) {
                System.out.println(who + " 플레이어가 이김!");
                System.exit(1);
            } else if (isDraw(board)) {
                System.out.println("No winner.");
                System.exit(2);
            }
        }


        //인공지능이 플레이
        public static void userPlay(char who, char board[][]){
            //AI의 행동패턴을 만들면 됨

            //=======================================================(AI부분으로 대체)
            alphabeta.dropTheDiscAI(who, board); //'who'플레이어 착수
            displayTheBoard(board); //현재 상황을 화면에 표시

            //이겼는지 비겼는지
            if (isWon(board)) {
                System.out.println(who + " 플레이어가 이김!");
                System.exit(1);
            } else if (isDraw(board)) {
                System.out.println("No winner.");
                System.exit(2);
            }
            //=======================================================


        }



        //disc를 두는 함수. 해당 열이 차있는지도 확인.
        public static void dropTheDisc(char player, char[][] board) {
            Scanner input = new Scanner(System.in);
            boolean done = false;

            do {
                System.out.print("Drop a " + (player == 'U' ? "U" : "N") + " disk at column (0-6): ");
                int column = input.nextInt();
                if (placeTheDisk(board, column, player))
                    done = true;
                else
                    System.out.println("This column is full. Try a different column");
            } while (!done);
        }


        // 해당 board위치가 null값이면 플레이어의 이름을 넣는다(이름으로 누구의 disc인지 확인)
        //아니면 dropTheDisc에서 if문을 else부분이 실행되게 한다.
        static boolean placeTheDisk(char[][] board, int column, char player) {
            for (int i = 0; i < board.length; i++) {
                if (board[i][column] == '\u0000') {
                    board[i][column] = player;
                    return true;
                }
            }

            return false;
        }

        //화면에 현재 board를 띄워주는 함수
        static void displayTheBoard(char[][] board) {
            for (int i = board.length - 1; i >= 0; i--) {
                System.out.print("|");
                for (int j = 0; j < board[i].length; j++)
                    System.out.print(board[i][j] != '\u0000' ? board[i][j] + "|"
                            : " |");
                System.out.println();
            }
            System.out.println("----------------------");
        }


        public static boolean isWon(char[][] board) {
            return isConsecutiveFour(board);
        }


        //모든 board를 확인하여 모두 null값이 아니면 (U, N이 차있으면) true 반환
        public static boolean isDraw(char[][] board) {
            for (int i = 0; i < board.length; i++)
                for (int j = 0; j < board[i].length; j++)
                    if (board[i][j] == '\u0000')
                        return false;

            return true;
        }

        public static boolean isConsecutiveFour(char[][] values) {
            int numberOfRows = values.length;
            int numberOfColumns = values[0].length;

            //borad의 해당 행에서 4개가 연속된 것이 있는지 확인
            for (int i = 0; i < numberOfRows; i++) {
                if (isConsecutiveFour(values[i]))
                    return true;
            }

            //board의 해당 열에서 4개가 연속된 것이 있는지 확인
            for (int j = 0; j < numberOfColumns; j++) {
                char[] column = new char[numberOfRows];
                for (int i = 0; i < numberOfRows; i++)
                    column[i] = values[i][j];
                if (isConsecutiveFour(column))
                    return true;
            }

            //board에서 해당 state(values[x][y])에서 (1열)(1행~3행)-에 있는 state) 오른쪽위로 끝까지 조사했을때 4개 연속되는 disc가 있는지
            for (int i = 0; i < numberOfRows - 3; i++) {
                int numberOfElementsInDiagonal = Math.min(numberOfRows - i, numberOfColumns);
                char[] diagonal = new char[numberOfElementsInDiagonal];
                for (int k = 0; k < numberOfElementsInDiagonal; k++)
                    diagonal[k] = values[k + i][k];
                if (isConsecutiveFour(diagonal))
                    return true;
            }

            //board에서 해당 state(values[x][y])에서 (2열~4열)(1행)-에 있는 state) 오른쪽위로 끝까지 조사했을때 4개 연속되는 disc가 있는지
            for (int j = 1; j < numberOfColumns - 3; j++) {
                int numberOfElementsInDiagonal = Math.min(numberOfColumns - j, numberOfRows);
                char[] diagonal = new char[numberOfElementsInDiagonal];
                for (int k = 0; k < numberOfElementsInDiagonal; k++)
                    diagonal[k] = values[k][k + j];
                if (isConsecutiveFour(diagonal))
                    return true;
            }

            //board에서 해당 state(values[x][y])에서 (4열~6열)(1행)-에 있는 state) 왼쪽위로 끝까지 조사했을때 4개 연속되는 disc가 있는지
            for (int j = 3; j < numberOfColumns; j++) {
                int numberOfElementsInDiagonal = Math.min(j + 1, numberOfRows);
                char[] diagonal = new char[numberOfElementsInDiagonal];
                for (int k = 0; k < numberOfElementsInDiagonal; k++)
                    diagonal[k] = values[k][j - k];
                if (isConsecutiveFour(diagonal))
                    return true;
            }

            //board에서 해당 state(values[x][y])에서 (2열~3열)(7행)-에 있는 state) 왼쪽위로 끝까지 조사했을때 4개 연속되는 disc가 있는지
            for (int i = 1; i < numberOfRows - 3; i++) {
                int numberOfElementsInDiagonal = Math.min(numberOfRows - i, numberOfColumns);
                char[] diagonal = new char[numberOfElementsInDiagonal];
                for (int k = 0; k < numberOfElementsInDiagonal; k++)
                    diagonal[k] = values[k + i][numberOfColumns - k - 1];
                if (isConsecutiveFour(diagonal))
                    return true;
            }
            return false;
        }

        //values 배열을 받아와서 배열이 연속해서 4개가 같은 문자인지 확인함. 같지않으면 false
        public static boolean isConsecutiveFour(char[] values) {
            for (int i = 0; i < values.length - 3; i++) {
                boolean isEqual = true;
                for (int j = i; j < i + 3; j++) {
                    if (values[j] == '\u0000' || values[j] != values[j + 1]) {
                        isEqual = false;
                        break;
                    }
                }
                if (isEqual)
                    return true;
            }
            return false;
        }
}
