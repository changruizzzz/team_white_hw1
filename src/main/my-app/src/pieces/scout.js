import Piece from "./piece.js";
export default class Scout extends Piece {
    constructor(player) {
        super(player, (player === 1 ? "http://vector.gissen.nl/stratego-scout.svg" : null))
        this.rank = 2
    }
}