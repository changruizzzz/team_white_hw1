import Piece from "./piece.js";
export default class Genreal extends Piece {
    constructor(player) {
        super(player, (player === 1 ? "http://vector.gissen.nl/stratego-general.svg" : null))
        this.rank = 9
    }
}