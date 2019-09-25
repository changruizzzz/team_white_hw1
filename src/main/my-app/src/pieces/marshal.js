import Piece from "./piece.js";
export default class Marshal extends Piece {
    constructor(player) {
        super(player, (player === 1 ? "http://vector.gissen.nl/stratego-marshal.svg" : null))
        this.rank = 10
    }
}