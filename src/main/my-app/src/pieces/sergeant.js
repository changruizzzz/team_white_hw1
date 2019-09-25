import Piece from "./piece.js";
export default class Sergeant extends Piece {
    constructor(player) {
        super(player, (player === 1 ? "http://vector.gissen.nl/stratego-sergeant.svg" : null))
        this.rank = 4
    }
}