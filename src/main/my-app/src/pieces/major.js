import Piece from "./piece.js";
export default class Major extends Piece {
    constructor(player) {
        super(player, (player === 1 ? "http://vector.gissen.nl/stratego-major.svg" : null))
        this.rank = 7
    }
}