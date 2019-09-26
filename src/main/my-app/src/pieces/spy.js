import Piece from "./piece.js";
export default class Spy extends Piece {
    constructor(player) {
        super(player, (player === 1 ? "http://vector.gissen.nl/stratego-spy.svg" : null))
        this.rank = 1
    }
}