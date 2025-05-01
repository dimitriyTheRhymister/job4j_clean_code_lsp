package ru.job4j.test.temp.my.tic_tac_toe;

import java.util.Scanner;

public class TicTacToe {

    private final char[][] board = new char[3][3];
    private boolean isComputerFirst;

    public TicTacToe() {
        initBoard();
    }

    private void initBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Кто делает первый ход? (1 - человек, 2 - компьютер)");
        int choice = scanner.nextInt();
        scanner.nextLine(); // добавление этой строки для очистки буфера
        isComputerFirst = choice == 2;
        playGame(scanner);
        scanner.close();
    }

    private void playGame(Scanner scanner) {
        if (isComputerFirst) {
            makeComputerMove();
        }
        while (true) {
            printBoard();

            System.out.println("Ваш ход. Введите номер строки и столбца (1-3):");
            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            while (parts.length != 2) {
                System.out.println("Недопустимый ввод. Введите два числа разделенных пробелом:");
                input = scanner.nextLine();
                parts = input.split(" ");
            }
            try {
                int row = Integer.parseInt(parts[0]) - 1;
                int col = Integer.parseInt(parts[1]) - 1;

                if (isValidMove(row, col)) {
                    board[row][col] = 'x';
                    if (isWinningMove('x')) {
                        printBoard();
                        System.out.println("Вы выиграли!");
                        break;
                    }
                    makeComputerMove();
                    if (isWinningMove('o')) {
                        printBoard();
                        System.out.println("Компьютер выиграл!");
                        break;
                    }
                } else {
                    System.out.println("Недопустимый ход. Попробуйте снова.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Недопустимый ввод. Введите два числа разделенных пробелом:");
                continue;
            }
            if (isBoardFull()) {
                printBoard();
                System.out.println("Ничья!");
                break;
            }
        }
    }

    private void makeComputerMove() {
        ComputerPlayer computerPlayer = new ComputerPlayer(board);
        computerPlayer.makeMove();
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == '-';
    }

    private boolean isWinningMove(char player) {
        // проверка строк
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
        }
        // проверка столбцов
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }
        // проверка диагоналей
        return (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
                (board[0][2] == player && board[1][1] == player && board[2][0] == player);
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    private void printBoard() {
        System.out.println("--|---|---");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]);
                if (j < 2) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if (i < 2) {
                System.out.println("--|---|---");
            }
        }
        System.out.println("--|---|---");
    }

    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.startGame();
    }
}
