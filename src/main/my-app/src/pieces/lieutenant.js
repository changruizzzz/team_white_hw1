import Piece from "./piece.js";
export default class Lieutenant extends Piece {
    constructor(player) {
        super(player, (player === 1 ? "http://vector.gissen.nl/stratego-lieutenant.svg" : null))
        this.rank = 5
    }
}