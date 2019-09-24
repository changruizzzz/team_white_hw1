package com.white.stratego.stratego.game;

public class boardSetups {
    int[] setup = {1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 8, 8, 9, 10, 11, 13, 13, 13, 13, 13, 13};

    // shuffling the array using Knuth's shuffle method
    private void randomSet() {
        int n = 40;
        for (int i = 0; i < n; i++) {
            // choose index uniformly in [i, n-1]
            int r = i + (int) (Math.random() * (n - i));
            int tmp = setup[r];
            setup[r] = setup[i];
            setup[i] = tmp;
        }
    }

    //The first 6 setups are from Vincent Deboer (World Champion Stratego).
    /*
    This is one of his most "famous" setups, which is a dubious quality
    for a Stratego setup. He didn't used any other setup as often on tournaments
    and several other players have successfully used this setup as well.
    It is a bit predictable, but all pieces are at hand when you need them and
    it works well for both attack and defense.
     */
    private void set1() {
        setup = new int[]{6,2,2,5,2,6,3,10,2,6,
                          5,4,13,1,9,2,7,7,8,2,
                          4,13,4,7,8,5,13,5,6,4,
                          2,3,13,2,3,13,11,13,3,3};
    };
    /*
    This is another setup he has used often on tournaments. It's outspoken aggressive
    and not very suitable for careful slow games, but it's less predictable and
    works better against stronger players.
     */
    private void set2() {
        setup = new int[]{10,6,5,3,2,6,2,2,2,6,
                          4,2,8,8,9,2,4,13,13,5,
                          7,2,7,1,6,5,13,4,5,2,
                          7,3,3,3,4,13,11,13,13,3};
    }
    /*
    Another setup he used often. Harder It's harder to defend the flag in the corner,
    but otherwise works very well.
     */
    private void set3() {
        setup = new int[]{6,2,4,9,6,2,2,10,2,6,
                          5,2,7,5,13,2,7,7,8,3,
                          4,8,1,3,13,2,6,5,5,13,
                          3,13,4,13,4,2,3,3,13,11};
    }
    /*
    It's a good all-round setup.
     */
    private void set4() {
        setup = new int[]{2,8,5,2,6,2,9,3,2,6,
                          10,2,7,8,2,6,13,5,13,5,
                          6,4,7,1,7,5,13,4,13,4,
                          3,2,3,3,4,13,11,13,3,2};
    }
    /*
    The flag on this position is something he often used against stronger opponents,
    but the rest of the pieces he usually change every time in such games.
     */
    private void set5() {
        setup = new int[]{9,6,2,4,2,2,2,3,6,2,
                          3,2,8,7,13,5,10,7,5,8,
                          13,6,1,7,5,2,6,5,13,4,
                          4,2,3,13,4,3,3,13,11,13};
    }
    /*
    This is an old setup that he invented on his first world championships.
    It's rather weak defensively though, it worked well when setups of this style were
    still unexpected but later it got too weak.
     */
    private void set6() {
        setup = new int[]{2,6,5,9,2,6,2,10,6,2,
                          7,2,13,1,8,4,2,7,8,4,
                          5,4,13,2,7,3,5,6,5,13,
                          3,13,4,2,3,13,3,3,13,11};
    }
    /*
    This setup comes from Philip Atzemoglou
     */
    private void set7() {
        setup = new int[]{10,7,3,4,13,13,4,3,7,9,
                          7,2,8,2,6,5,2,8,2,1,
                          13,6,2,5,4,13,6,2,3,2,
                          11,13,5,3,13,4,3,6,2,5};
    }
    /*
    This setup from Anthony. This is a variation of the Shoreline Bluff.
    Scouts are placed at the front to test the opponent's defense and then in the back
    as reserves where they can become very useful in the endgame. The Flag is very well protected by the Marshal
    on one side and by a Colonel on the other.
    The Bombs in the center are used to force play to the sides.
    The right side is rather weak, however, that is countered by the placement
    of the Bombs preventing any aggressive horizontal movement.
    I also have several Scouts on that side to test the strengths of my opponent's pieces, making sure my
    Colonel and Major are not taken by surprise. Most of my opponents bypass my Flag and are surprised
    to learn that it was in the first row after I defeat them.
     */
    private void set8() {
        setup = new int[]{12,2,13,11,13,13,4,2,2,2,
                          7,9,8,13,10,7,13,3,8,7,
                          5,4,6,6,3,3,6,2,6,5,
                          2,4,3,5,2,1,4,13,5,3};
    }
    /*
    This setup comes from Brandon Clark
    I send my front line out to try to get an idea of where his pieces are,
    then I try my best not to let him penetrate my 2nd line.
    I hold a lot of Miners and Scouts for later in the game.
    I have my Spy in a key spot so he can't get to my Flag.
     */
    private void set9() {
        setup = new int[]{4,3,5,7,4,2,5,7,4,3,
                          13,13,10,8,13,13,8,9,13,13,
                          5,1,6,4,6,3,2,6,2,2,
                          11,7,6,2,2,2,5,2,3,3};
    }
    //The following is from Mike Rowles.
    /*
    Bomb Barrier.
    The first one is an older setup when I had a one-dimensional
    style of play. The strategy was to hit hard on the left and
    hopefully his counterattack would run into the Bombs.
     */
    private void set10() {
        setup = new int[]{13,9,10,2,4,5,4,5,5,13,
                          8,1,13,6,4,2,2,13,5,6,
                          13,7,3,3,8,7,2,7,4,2,
                          11,13,3,3,3,6,2,2,6,2};
    }
    /*
    B29.
     */
    private void set11() {
        setup = new int[]{6,2,9,13,4,4,2,2,4,4,
                          8,10,2,8,13,5,2,5,2,3,
                          13,3,1,7,6,13,3,6,5,2,
                          11,13,7,3,7,6,13,3,2,5};
    }
    /*
    This setup comes from Johnny O'Donnell.
    This setup aims to swallow up all your opponent's
    Miners before the Flag can be reached.
    It is an aggressive players nightmare.
     */
    private void set12() {
        setup = new int[]{13,9,1,8,2,5,3,8,2,5,
                          4,13,3,6,7,10,2,2,7,2,
                          13,4,13,2,2,5,4,6,2,6,
                          11,13,4,13,6,3,3,3,5,7};
    }
    /*
    This setup comes from Bill East.
    Here is a real aggressive setup where you hope to eliminate his Miners
    and flood him from one side. Most of the players I play against tend
    to keep their Miners in the back ranks. This causes a great deal of problem
    for them because they have to move a great deal to reposition them to take out my Bombs.
    Also, notice the back line of Scouts. As you march up on the left side
    you can use your Scouts to flood this side and to probe what is coming
    to greet you. I feel this is a great Strategy.
    To be successful with this setup you need to advance hard from the left!
     */
    private void set13() {
        setup = new int[]{2,2,3,7,13,13,4,4,13,13,
                          9,8,1,3,6,8,6,6,7,4,
                          13,10,5,3,3,7,6,5,5,5,
                          11,13,2,2,2,2,2,2,3,4};
    }
    // returns setup array either by number (1-13),
    // or randomly in range [1,13], or set the board randomly if n is not in range [0,13]
    public int[] getSetup(int n) {
        if (n == 0) {
            n = (int) (Math.random() * 10 + 1);
        }

        switch(n) {
            case 1:
                set1();
                break;
            case 2:
                set2();
                break;
            case 3:
                set3();
                break;
            case 4:
                set4();
                break;
            case 5:
                set5();
                break;
            case 6:
                set6();
                break;
            case 7:
                set7();
                break;
            case 8:
                set8();
                break;
            case 9:
                set9();
                break;
            case 10:
                set10();
                break;
            case 11:
                set11();
                break;
            case 12:
                set12();
                break;
            case 13:
                set13();
                break;
            default:
                randomSet();

        }
        return setup;
    }
}
