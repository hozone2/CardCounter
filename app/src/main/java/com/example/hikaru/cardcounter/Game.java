package com.example.hikaru.cardcounter;

public class Game extends MainActivity {
    public int totalCount;

    Game() {
        totalCount = 0;
        shuffleDeck();
        for (int i = 0; i < 52; i++) {
            drawACard(totalCount);
            //time iteration;
        }
    }

}
