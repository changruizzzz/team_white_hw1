import Miner from "../pieces/miner";
import Bomb from "../pieces/bomb";
import General from "../pieces/general";
import Lieutenant from "../pieces/lieutenant";
import Major from "../pieces/major";
import Marshal from "../pieces/marshal";
import Scout from "../pieces/scout";
import Sergeant from "../pieces/sergeant";
import Spy from "../pieces/spy";
import Flag from "../pieces/flag";
import Colonel from "../pieces/colonel";
import Captain from "../pieces/captain";
export default class boardInitialization {

    constructor() {
        this.squares = Array(100).fill(null)
        this.player1Pieces = Array(12).fill(0)
        this.player2Pieces = Array(12).fill(0)
    }
    boardInit() {
        /*
         * [  0 ][  1 ][  2 ][  3 ][  4 ][  5 ][  6 ][  7 ][  8 ][  9 ] ---------->
         * [ 10 ][ 11 ][ 12 ][ 13 ][ 14 ][ 15 ][ 16 ][ 17 ][ 18 ][ 19 ] Player 2
         * [ 20 ][ 21 ][ 22 ][ 23 ][ 24 ][ 25 ][ 26 ][ 27 ][ 28 ][ 29 ]
         * [ 30 ][ 31 ][ 32 ][ 33 ][ 34 ][ 35 ][ 36 ][ 37 ][ 38 ][ 39 ] ---------->
         * [ 40 ][ 41 ][ 42 ][ 43 ][ 44 ][ 45 ][ 46 ][ 47 ][ 48 ][ 49 ]
         * [ 50 ][ 51 ][ 52 ][ 53 ][ 54 ][ 55 ][ 56 ][ 57 ][ 58 ][ 59 ]
         * [ 60 ][ 61 ][ 62 ][ 63 ][ 64 ][ 65 ][ 66 ][ 67 ][ 68 ][ 69 ] ---------->
         * [ 70 ][ 71 ][ 72 ][ 73 ][ 74 ][ 75 ][ 76 ][ 77 ][ 78 ][ 79 ] Player 1
         * [ 80 ][ 81 ][ 82 ][ 83 ][ 84 ][ 85 ][ 86 ][ 87 ][ 88 ][ 89 ]
         * [ 90 ][ 91 ][ 92 ][ 93 ][ 94 ][ 95 ][ 96 ][ 97 ][ 98 ][ 99 ] ---------->
         *
         *
         */
        const squares = Array(100).fill(null)

        squares[75] = new Flag(2)
        this.player2Pieces[0] += 1
        squares[77] = new Scout(2)
        this.player2Pieces[2] += 1

        squares[93] = new Flag(1)
        this.player1Pieces[0] += 1
        squares[96] = new Marshal(1)
        this.player1Pieces[10] += 1
        // squares[83] = new Bomb(1)
        // squares[94] = new Bomb(1)
        // squares[91] = new Lieutenant(1)
        // squares[90] = new Major(1)
        // squares[89] = new Marshal(1)
        // squares[88] = new Scout(1)
        // squares[87] = new Sergeant(1)
        // squares[86] = new Spy(1)
        // squares[95] = new Colonel(1)
        // squares[82] = new General(1)
        // squares[15] = new Miner(2)
        // this.setup(1, squares)
        // let ranksArray = [1, 1, 8, 5, 4, 4, 4, 3, 2, 1, 1, 6]

        this.squares = squares
    }

    /*
     * Setup pieces randomly of specified player
     */
    setup(player, squares) {
        this.squares = Array(100).fill(null)
        player === 1?
            this.player1Pieces = Array(12).fill(0):
            this.player2Pieces = Array(12).fill(0)
        let ranksArray = [1, 1, 8, 5, 4, 4, 4, 3, 2, 1, 1, 6]//length: 12
        // let ranksMap = [[0,1], [1,1], [2,8], [3,5], [4,4], [5,4], [6,4], [7,3], [8,2], [9,1], [10,1], [11,6]]
        let flagInd, bomb1Ind, bomb2Ind, bomb3Ind
        if (player === 1) {
            //Initialize flag and three bombs pieces
            flagInd = Math.floor(Math.random() * (99 - 91) + 91)
            bomb1Ind = flagInd - 1
            bomb2Ind = flagInd + 1
            bomb3Ind = flagInd - 10
            squares[flagInd] = this.getObject(player, 0, ranksArray)
            squares[bomb1Ind] = this.getObject(player, 11, ranksArray)
            squares[bomb2Ind] = this.getObject(player, 11, ranksArray)
            squares[bomb3Ind] = this.getObject(player, 11, ranksArray)
            for (let i = 60; i < 100; i++) {
                if (squares[i] == null) {
                    let randomRank = Math.floor(Math.random() * (ranksArray.length))
                    //Remove 0 amount from array
                    while (ranksArray[randomRank] === 0) {
                        randomRank += 1
                        randomRank = randomRank >= ranksArray.length? 0 : randomRank
                    }
                    squares[i] = this.getObject(player, randomRank, ranksArray)
                }
            }
        } else {
            flagInd = Math.floor(Math.random() * (9 - 1) + 1)
            bomb1Ind = flagInd - 1
            bomb2Ind = flagInd + 1
            bomb3Ind = flagInd + 10
            squares[flagInd] = this.getObject(player, 0, ranksArray)
            squares[bomb1Ind] = this.getObject(player, 11, ranksArray)
            squares[bomb2Ind] = this.getObject(player, 11, ranksArray)
            squares[bomb3Ind] = this.getObject(player, 11, ranksArray)
            for (let i = 0; i < 40; i++) {
                if (squares[i] !== null) {
                    let randomRank = Math.floor(Math.random() * (ranksArray.length))
                    //Remove 0 amount from array
                    while (ranksArray[randomRank] === 0) {
                        ranksArray.splice(randomRank, 1)
                        randomRank = Math.floor(Math.random() * (ranksArray.length))
                    }
                    squares[i] = this.getObject(player, randomRank, ranksArray)
                }
            }
        }

        return squares
    }

    /*
     * @Return relative piece object of specified player
     */
    getObject(player, rank, ranksArray) {
        if (rank < 0 && rank > 11) {
            return null
        }

        let obj
        switch (rank) {
            case 0:
                obj = new Flag(player); break

            case 1:
                obj = new Spy(player); break
            case 2:
                obj = new Scout(player); break
            case 3:
                obj = new Miner(player); break
            case 4:
                obj = new Sergeant(player); break
            case 5:
                obj = new Lieutenant(player); break
            case 6:
                obj = new Captain(player); break
            case 7:
                obj = new Major(player); break
            case 8:
                obj = new Colonel(player); break
            case 9:
                obj = new General(player); break
            case 10:
                obj = new Marshal(player); break
            case 11:
                obj = new Bomb(player)
        }
        ranksArray[rank] -= 1
        player === 1 ? this.player1Pieces[rank]++ : this.player2Pieces[rank]++
        return obj
    }
}