import Piece from "./piece.js";
export default class Colonel extends Piece {
    constructor(player) {
        super(player, (player === 1 ? "http://vector.gissen.nl/stratego-colonel.svg" : null))
        this.rank = 8
    }
}