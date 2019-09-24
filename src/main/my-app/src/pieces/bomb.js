import Piece from "./piece.js";
export default class Bomb extends Piece {
    constructor(player) {
        super(player, (player === 1 ? "http://vector.gissen.nl/stratego-bomb.svg" :null))
        this.rank = 11;
    }
}