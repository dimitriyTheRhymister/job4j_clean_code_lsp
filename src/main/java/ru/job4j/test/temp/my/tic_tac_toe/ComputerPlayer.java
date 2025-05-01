package ru.job4j.test.temp.my.tic_tac_toe;

import java.util.Random;

public class ComputerPlayer {
    private final char[][] board;

    public ComputerPlayer(char[][] board) {
        this.board = board;
    }

    public void makeMove() {
        // Проверяем, является ли это первым ходом компьютера
        if (isFirstMove()) {
            makeFirstMove();
        } else {
            // Проверяем, есть ли возможность выиграть или заблокировать победу пользователя
            if (!makeWinningOrBlockingMove()) {
                // Ищем линию, где уже есть "о" компьютера
                if (!makeMoveInExistingLine()) {
                    // Если нет линий с "о", то делаем случайный ход
                    makeRandomMove();
                }
            }
        }
    }

    private boolean isFirstMove() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 'o') {
                    return false;
                }
            }
        }
        return true;
    }

    private void makeFirstMove() {
        // Ставим "о" в центр или в один из углов
        int[][] possibleMoves = {{1, 1}, {0, 0}, {0, 2}, {2, 0}, {2, 2}};
        Random random = new Random();
        int index = random.nextInt(possibleMoves.length);
        int row = possibleMoves[index][0];
        int col = possibleMoves[index][1];
        if (board[row][col] == '-') {
            board[row][col] = 'o';
        } else {
            makeRandomMove();
        }
    }

    private boolean makeWinningOrBlockingMove() {
        // Проверяем все линии
        for (int i = 0; i < 3; i++) {
            // Проверяем строки
            if (canMakeWinningOrBlockingMove(i, 0, i, 1, i, 2)) {
                return true;
            }
            // Проверяем столбцы
            if (canMakeWinningOrBlockingMove(0, i, 1, i, 2, i)) {
                return true;
            }
        }
        // Проверяем диагонали
        if (canMakeWinningOrBlockingMove(0, 0, 1, 1, 2, 2)) {
            return true;
        }
        return canMakeWinningOrBlockingMove(0, 2, 1, 1, 2, 0);
    }

    private boolean canMakeWinningOrBlockingMove(int row1, int col1, int row2, int col2, int row3, int col3) {
        char player1 = board[row1][col1];
        char player2 = board[row2][col2];
        char player3 = board[row3][col3];
        if ((player1 == 'x' && player2 == 'x' && player3 == '-') ||
                (player1 == 'x' && player3 == 'x' && player2 == '-') ||
                (player2 == 'x' && player3 == 'x' && player1 == '-')) {
            if (player1 == '-') {
                board[row1][col1] = 'o';
            } else if (player2 == '-') {
                board[row2][col2] = 'o';
            } else {
                board[row3][col3] = 'o';
            }
            return true;
        }
        return false;
    }

    private boolean makeMoveInExistingLine() {
        // Проверяем все линии
        for (int i = 0; i < 3; i++) {
            // Проверяем строки
            if (makeMoveInLine(i, 0, i, 1, i, 2)) {
                return true;
            }
            // Проверяем столбцы
            if (makeMoveInLine(0, i, 1, i, 2, i)) {
                return true;
            }
        }
        // Проверяем диагонали
        if (makeMoveInLine(0, 0, 1, 1, 2, 2)) {
            return true;
        }
        return makeMoveInLine(0, 2, 1, 1, 2, 0);
    }

    private boolean makeMoveInLine(int row1, int col1, int row2, int col2, int row3, int col3) {
        char player1 = board[row1][col1];
        char player2 = board[row2][col2];
        char player3 = board[row3][col3];
        if ((player1 == 'o' || player2 == 'o' || player3 == 'o') &&
                (player1 == '-' || player2 == '-' || player3 == '-')) {
            if (player1 == '-') {
                board[row1][col1] = 'o';
            } else if (player2 == '-') {
                board[row2][col2] = 'o';
            } else {
                board[row3][col3] = 'o';
            }
            return true;
        }
        return false;
    }

    private void makeRandomMove() {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (board[row][col] != '-');
        board[row][col] = 'o';
    }
}
