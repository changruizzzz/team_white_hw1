import Piece from "./piece.js";
export default class Flag extends Piece {
    constructor(player) {
        super(player, (player === 1 ? "http://vector.gissen.nl/stratego-flag.svg" : null))
        this.rank = 0
    }
}