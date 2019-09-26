import Piece from "./piece.js";
export default class Captain extends Piece {
    constructor(player) {
        super(player, (player === 1 ? "http://vector.gissen.nl/stratego-captain.svg" : null))
        this.rank = 6
    }
}